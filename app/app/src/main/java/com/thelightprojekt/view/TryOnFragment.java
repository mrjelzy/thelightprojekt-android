package com.thelightprojekt.view;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Size;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.mediapipe.components.CameraHelper;
import com.google.mediapipe.components.CameraXPreviewHelper;
import com.google.mediapipe.components.ExternalTextureConverter;
import com.google.mediapipe.components.FrameProcessor;
import com.google.mediapipe.components.PermissionHelper;
import com.google.mediapipe.framework.AndroidAssetUtil;
import com.google.mediapipe.framework.Packet;
import com.google.mediapipe.framework.PacketGetter;
import com.google.mediapipe.glutil.EglManager;
import com.google.mediapipe.modules.facegeometry.FaceGeometryProto.FaceGeometry;
import com.google.mediapipe.formats.proto.MatrixDataProto.MatrixData;
import com.thelightprojekt.R;
import com.thelightprojekt.view.product.LensFragment;
import com.thelightprojekt.view.product.ProductFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TryOnFragment extends Fragment {
    private static final String TAG = "TryOnFragment";
    // Flips the camera-preview frames vertically by default, before sending them into FrameProcessor
    // to be processed in a MediaPipe graph, and flips the processed frames back when they are
    // displayed. This maybe needed because OpenGL represents images assuming the image origin is at
    // the bottom-left corner, whereas MediaPipe in general assumes the image origin is at the
    // top-left corner.
    // NOTE: use "flipFramesVertically" in manifest metadata to override this behavior.
    private static final boolean FLIP_FRAMES_VERTICALLY = true;

    // Number of output frames allocated in ExternalTextureConverter.
    // NOTE: use "converterNumBuffers" in manifest metadata to override number of buffers. For
    // example, when there is a FlowLimiterCalculator in the graph, number of buffers should be at
    // least `max_in_flight + max_in_queue + 1` (where max_in_flight and max_in_queue are used in
    // FlowLimiterCalculator options). That's because we need buffers for all the frames that are in
    // flight/queue plus one for the next frame from the camera.
    private static final int NUM_BUFFERS = 2;

    static {
        // Load all native libraries needed by the app.
        System.loadLibrary("mediapipe_jni");
        try {
            System.loadLibrary("opencv_java3");
        } catch (java.lang.UnsatisfiedLinkError e) {
            // Some example apps (e.g. template matching) require OpenCV 4.
            System.loadLibrary("opencv_java4");
        }
    }

    protected FrameProcessor processor;
    // Handles camera access via the {@link CameraX} Jetpack support library.
    protected CameraXPreviewHelper cameraHelper;

    // {@link SurfaceTexture} where the camera-preview frames can be accessed.
    private SurfaceTexture previewFrameTexture;
    // {@link SurfaceView} that displays the camera-preview frames processed by a MediaPipe graph.
    private SurfaceView previewDisplayView;

    // Creates and manages an {@link EGLContext}.
    private EglManager eglManager;
    // Converts the GL_TEXTURE_EXTERNAL_OES texture from Android camera into a regular texture to be
    // consumed by {@link FrameProcessor} and the underlying MediaPipe graph.
    private ExternalTextureConverter converter;

    // ApplicationInfo for retrieving metadata defined in the manifest.
    private ApplicationInfo applicationInfo;

    MaterialButton addButton;
    Integer product_id;

    // Face - Effect

    private static final String USE_FACE_DETECTION_INPUT_SOURCE_INPUT_SIDE_PACKET_NAME =
            "use_face_detection_input_source";
    private static final String SELECTED_EFFECT_ID_INPUT_STREAM_NAME = "selected_effect_id";
    private static final String OUTPUT_FACE_GEOMETRY_STREAM_NAME = "multi_face_geometry";

    private static final String EFFECT_SWITCHING_HINT_TEXT = "Tap to switch between effects!";

    private static final boolean USE_FACE_DETECTION_INPUT_SOURCE = false;
    private static final int MATRIX_TRANSLATION_Z_INDEX = 14;

    private static final int SELECTED_EFFECT_ID_GLASSES = 0;

    private final Object effectSelectionLock = new Object();
    private int selectedEffectId;

    private View effectSwitchingHintView;
    private GestureDetector tapGestureDetector;

    public static TryOnFragment newInstance(String id) {
        TryOnFragment fragment = new TryOnFragment();
        Bundle args = new Bundle();
        args.putString("product", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            product_id = Integer.valueOf((String) getArguments().get("product"));
    }


    @Override
    public void onResume() {
        super.onResume();
        converter =
                new ExternalTextureConverter(
                        eglManager.getContext(),
                        applicationInfo.metaData.getInt("converterNumBuffers", NUM_BUFFERS));
        converter.setFlipY(
                applicationInfo.metaData.getBoolean("flipFramesVertically", FLIP_FRAMES_VERTICALLY));
        converter.setConsumer(processor);
        if (PermissionHelper.cameraPermissionsGranted(this.getActivity())) {
            startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        converter.close();

        // Hide preview display until we re-open the camera again.
        previewDisplayView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        converter.close();
        previewDisplayView.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_try_on, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = view.findViewById(R.id.button_add_to_cart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_main_activity, LensFragment.newInstance(String.valueOf(product_id)))
                        .setReorderingAllowed(true)
                        .addToBackStack("LensChoosingFragment")
                        .commit();
            }
        });

        PackageManager packageManager = requireActivity().getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(requireActivity().getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Cannot find application info: " + e);
        }

        previewDisplayView = new SurfaceView(this.getContext());
        setupPreviewDisplayView(view);

        // Initialize asset manager so that MediaPipe native libraries can access the app assets, e.g.,
        // binary graphs.
        AndroidAssetUtil.initializeNativeAssetManager(this.getContext());
        eglManager = new EglManager(null);
        processor =
                new FrameProcessor(
                        this.getContext(),
                        eglManager.getNativeContext(),
                        applicationInfo.metaData.getString("binaryGraphName"),
                        applicationInfo.metaData.getString("inputVideoStreamName"),
                        applicationInfo.metaData.getString("outputVideoStreamName"));
        processor
                .getVideoSurfaceOutput()
                .setFlipY(applicationInfo.metaData.getBoolean("flipFramesVertically", FLIP_FRAMES_VERTICALLY));

        PermissionHelper.checkAndRequestCameraPermissions(this.getActivity());

        // Suite de mediapipe


        // Add an effect switching hint view to the preview layout.

        ViewGroup viewGroup = view.findViewById(R.id.preview_display_layout);

        // By default, render the axis effect for the face detection input source and the glasses effect
        // for the face landmark input source.

        selectedEffectId = SELECTED_EFFECT_ID_GLASSES;

        // Pass the USE_FACE_DETECTION_INPUT_SOURCE flag value as an input side packet into the graph.
        Map<String, Packet> inputSidePackets = new HashMap<>();
        inputSidePackets.put(USE_FACE_DETECTION_INPUT_SOURCE_INPUT_SIDE_PACKET_NAME,
                            processor.getPacketCreator().createBool(USE_FACE_DETECTION_INPUT_SOURCE));
        processor.setInputSidePackets(inputSidePackets);

        // This callback demonstrates how the output face geometry packet can be obtained and used
        // in an Android app. As an example, the Z-translation component of the face pose transform
        // matrix is logged for each face being equal to the approximate distance away from the camera
        // in centimeters.
       processor.addPacketCallback(
                OUTPUT_FACE_GEOMETRY_STREAM_NAME,
                (packet) -> {
                    Log.d(TAG, "Received a multi face geometry packet.");
                    List<FaceGeometry> multiFaceGeometry =
                            PacketGetter.getProtoVector(packet, FaceGeometry.parser());

                    StringBuilder approxDistanceAwayFromCameraLogMessage = new StringBuilder();
                    for (FaceGeometry faceGeometry : multiFaceGeometry) {
                        if (approxDistanceAwayFromCameraLogMessage.length() > 0) {
                            approxDistanceAwayFromCameraLogMessage.append(' ');
                        }
                        MatrixData poseTransformMatrix = faceGeometry.getPoseTransformMatrix();
                        approxDistanceAwayFromCameraLogMessage.append(
                                -poseTransformMatrix.getPackedData(MATRIX_TRANSLATION_Z_INDEX));
                    }

                    Log.d(
                            TAG,
                            "[TS:"
                                    + packet.getTimestamp()
                                    + "] size = "
                                    + multiFaceGeometry.size()
                                    + "; approx. distance away from camera in cm for faces = ["
                                    + approxDistanceAwayFromCameraLogMessage
                                    + "]");
                });

        // Alongside the input camera frame, we also send the `selected_effect_id` int32 packet to
        // indicate which effect should be rendered on this frame.
        processor.setOnWillAddFrameListener(
                (timestamp) -> {
                    Packet selectedEffectIdPacket = processor.getPacketCreator().createInt32(selectedEffectId);
                        processor.getGraph()
                                .addPacketToInputStream(SELECTED_EFFECT_ID_INPUT_STREAM_NAME, selectedEffectIdPacket, timestamp);
                        if (selectedEffectIdPacket != null) {
                            selectedEffectIdPacket.release();
                        }
                });
    }

    private void setupPreviewDisplayView(View view) {
        previewDisplayView.setVisibility(View.GONE);
        ViewGroup viewGroup = view.findViewById(R.id.preview_display_layout);
        viewGroup.addView(previewDisplayView);

        previewDisplayView
                .getHolder()
                .addCallback(
                        new SurfaceHolder.Callback() {
                            @Override
                            public void surfaceCreated(SurfaceHolder holder) {
                                processor.getVideoSurfaceOutput().setSurface(holder.getSurface());
                            }

                            @Override
                            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                                onPreviewDisplaySurfaceChanged(holder, format, width, height);
                            }

                            @Override
                            public void surfaceDestroyed(SurfaceHolder holder) {
                                processor.getVideoSurfaceOutput().setSurface(null);
                            }
                        });
    }

    public void startCamera() {
        cameraHelper = new CameraXPreviewHelper();
        previewFrameTexture = converter.getSurfaceTexture();
        cameraHelper.setOnCameraStartedListener(
                surfaceTexture -> {
                    onCameraStarted(surfaceTexture);
                });
        CameraHelper.CameraFacing cameraFacing =
                applicationInfo.metaData.getBoolean("cameraFacingFront", false)
                        ? CameraHelper.CameraFacing.FRONT
                        : CameraHelper.CameraFacing.BACK;
        cameraHelper.startCamera(
                this.getActivity(), cameraFacing, previewFrameTexture, cameraTargetResolution());
    }


    protected void onCameraStarted(SurfaceTexture surfaceTexture) {
        previewFrameTexture = surfaceTexture;
        // Make the display view visible to start showing the preview. This triggers the
        // SurfaceHolder.Callback added to (the holder of) previewDisplayView.
        previewDisplayView.setVisibility(View.VISIBLE);
    }

    protected Size cameraTargetResolution() {
        return null; // No preference and let the camera (helper) decide.
    }

    protected void onPreviewDisplaySurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // (Re-)Compute the ideal size of the camera-preview display (the area that the
        // camera-preview frames get rendered onto, potentially with scaling and rotation)
        // based on the size of the SurfaceView that contains the display.
        Size viewSize = computeViewSize(width, height);
        Size displaySize = cameraHelper.computeDisplaySizeFromViewSize(viewSize);
        boolean isCameraRotated = cameraHelper.isCameraRotated();

        // Configure the output width and height as the computed display size.
        converter.setDestinationSize(
                isCameraRotated ? displaySize.getHeight() : displaySize.getWidth(),
                isCameraRotated ? displaySize.getWidth() : displaySize.getHeight());
    }

    protected Size computeViewSize(int width, int height) {
        return new Size(width, height);
    }


}
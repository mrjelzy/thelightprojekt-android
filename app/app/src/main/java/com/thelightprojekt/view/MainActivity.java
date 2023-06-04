package com.thelightprojekt.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.mediapipe.components.CameraXPreviewHelper;
import com.google.mediapipe.components.ExternalTextureConverter;
import com.google.mediapipe.components.FrameProcessor;
import com.google.mediapipe.glutil.EglManager;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.view.account.ProfileFragment;
import com.thelightprojekt.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity {

    ProductViewModel productViewModel;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.host_fragment_main_activity, HomeFragment.class, null)
                    .commit();
        }

        NavigationBarView navBarr=findViewById(R.id.bottomNavBar);
        LoginFragment loginFragment=new LoginFragment();
        HomeFragment homeFragment=new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        navBarr.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.account:
                        if(UserState.getInstance().getUser() != null){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.host_fragment_main_activity,profileFragment)
                                    .setReorderingAllowed(true)
                                    .addToBackStack("ProfileFragment")
                                    .commit();
                        }else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.host_fragment_main_activity,loginFragment)
                                    .setReorderingAllowed(true)
                                    .addToBackStack("LoginFragment")
                                    .commit();
                        }
                        return true;

                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.host_fragment_main_activity,homeFragment)
                                .setReorderingAllowed(true)
                                .addToBackStack("HomeFragment")
                                .commit();
                        return true;
                }
                return false;
            }
        });
    }
}
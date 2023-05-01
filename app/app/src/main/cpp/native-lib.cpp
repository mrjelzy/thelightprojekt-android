#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_thelightprojekt_model_HttpClientInstance_getEncryptedKey(JNIEnv *env, jclass clazz) {
 std::string encrypted_key = "R040S1lJMjlOSUlFUkZONEI0VU1NTFlTVU5CU1RCSVY=";
 return env->NewStringUTF(encrypted_key.c_str());
}

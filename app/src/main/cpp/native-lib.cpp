#include <jni.h>
#include <string>

extern "C" JNIEXPORT jdouble JNICALL
Java_id_ac_ui_cs_mobileprogramming_natasyameidianaakhda_savingco_ui_calculator_CalculatorViewModel_nativeAdd(JNIEnv *env, jobject obj, jdouble numberOne, jdouble numberTwo) {
    return numberOne + numberTwo;
}

extern "C" JNIEXPORT jdouble JNICALL
Java_id_ac_ui_cs_mobileprogramming_natasyameidianaakhda_savingco_ui_calculator_CalculatorViewModel_nativeMinus(JNIEnv *env, jobject obj, jdouble numberOne, jdouble numberTwo) {
    return numberOne - numberTwo;
}

extern "C" JNIEXPORT jdouble JNICALL
Java_id_ac_ui_cs_mobileprogramming_natasyameidianaakhda_savingco_ui_calculator_CalculatorViewModel_nativeMultiply(JNIEnv *env, jobject obj, jdouble numberOne, jdouble numberTwo) {
    return numberOne * numberTwo;
}

extern "C" JNIEXPORT jdouble JNICALL
Java_id_ac_ui_cs_mobileprogramming_natasyameidianaakhda_savingco_ui_calculator_CalculatorViewModel_nativeDivide(JNIEnv *env, jobject obj, jdouble numberOne, jdouble numberTwo) {
    return numberOne / numberTwo;
}
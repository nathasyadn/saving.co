package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.calculator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class CalculatorViewModel extends AndroidViewModel {
    static {
        System.loadLibrary("native-lib");
    }

    public CalculatorViewModel(@NonNull Application application){
        super(application);
    }

    public native double nativeAdd(double numberOne, double numberTwo);
    public native double nativeMinus(double numberOne, double numberTwo);
    public native double nativeMultiply(double numberOne, double numberTwo);
    public native double nativeDivide(double numberOne, double numberTwo);

    public double add(double numberOne, double numberTwo){
        return nativeAdd(numberOne, numberTwo);
    }

    public double minus(double numberOne, double numberTwo){
        return nativeMinus(numberOne, numberTwo);
    }

    public double multiply(double numberOne, double numberTwo){
        return nativeMultiply(numberOne, numberTwo);
    }

    public double divide(double numberOne, double numberTwo){
        return nativeDivide(numberOne, numberTwo);
    }
}

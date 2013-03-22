package me.test.server;

import me.test.mymath.AddFault;
import me.test.mymath.AddFault_Exception;
import me.test.mymath.DivideFault_Exception;
import me.test.mymath.MinusFault;
import me.test.mymath.MinusResponseType;
import me.test.mymath.MinusType;
import me.test.mymath.MultiplyFault;
import me.test.mymath.MyFaultType;
import me.test.mymath.MyMath;

public class MyMathImpl2 implements MyMath {

    public int add(int x, int y) throws AddFault_Exception {
        if (y == -1) {
            String errorCode = "err-add";
            String errorMessage = "y cannot be minus.";
            AddFault addFault = new AddFault();
            addFault.setErrorCode(errorCode);
            addFault.setErrorMessage(errorMessage);
            throw new AddFault_Exception(errorMessage, addFault);
        }
        return x + y;
    }

    public MinusResponseType minus(MinusType parameters) throws MinusFault {
        if (parameters.getY() == -1) {
            String errorCode = "err-multiply";
            String errorMessage = "y cannot be minus.";
            MyFaultType myFaultType = new MyFaultType();
            myFaultType.setErrorCode(errorCode);
            myFaultType.setErrorMessage(errorMessage);
            throw new MinusFault(errorMessage, myFaultType);
        }
        MinusResponseType minusResponseType = new MinusResponseType();
        minusResponseType.setOut(parameters.getX() - parameters.getY());
        return minusResponseType;
    }

    public int multiply(int x, int y) throws MultiplyFault {
        if (y == -1) {
            String errorCode = "err-multiply";
            String errorMessage = "y cannot be minus.";
            MyFaultType myFaultType = new MyFaultType();
            myFaultType.setErrorCode(errorCode);
            myFaultType.setErrorMessage(errorMessage);
            throw new MultiplyFault(errorMessage, myFaultType);
        }
        return x * y;
    }

    public int divide(int x, int y) throws DivideFault_Exception {
        return 0;
    }

}

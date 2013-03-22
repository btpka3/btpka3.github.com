package me.test.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import me.test.mymath.AddFault;
import me.test.mymath.AddFault_Exception;
import me.test.mymath.DivideFault_Exception;
import me.test.mymath.MinusFault;
import me.test.mymath.MinusResponseType;
import me.test.mymath.MinusType;
import me.test.mymath.MultiplyFault;
import me.test.mymath.MyFaultType;
import me.test.mymath.MyMath;

public class MyMathImpl implements MyMath {

    @WebResult(name = "out", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://www.test.me/MyMath/", className = "me.test.mymath.Add")
    @WebMethod(action = "http://www.test.me/MyMath/add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://www.test.me/MyMath/", className = "me.test.mymath.AddResponse")
    public int add(@WebParam(name = "x", targetNamespace = "") int x,
            @WebParam(name = "y", targetNamespace = "") int y)
            throws AddFault_Exception {
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

    @SOAPBinding(parameterStyle = ParameterStyle.BARE)
    @WebResult(name = "parameters", targetNamespace = "", partName = "parameters")
    @WebMethod
    public MinusResponseType minus(
            @WebParam(partName = "parameters", name = "parameters", targetNamespace = "") MinusType parameters)
            throws MinusFault {
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

    @WebResult(name = "out", targetNamespace = "")
    @RequestWrapper(localName = "multiply", targetNamespace = "http://www.test.me/MyMath/", className = "me.test.mymath.MultiplyType")
    @WebMethod
    @ResponseWrapper(localName = "multiplyResponse", targetNamespace = "http://www.test.me/MyMath/", className = "me.test.mymath.MultiplyResponseType")
    public int multiply(@WebParam(name = "x", targetNamespace = "") int x,
            @WebParam(name = "y", targetNamespace = "") int y)
            throws MultiplyFault {
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

    @WebResult(name = "out", targetNamespace = "")
    @RequestWrapper(localName = "divide", targetNamespace = "http://www.test.me/MyMath/", className = "me.test.mymath.DivideType")
    @WebMethod
    @ResponseWrapper(localName = "divideResponse", targetNamespace = "http://www.test.me/MyMath/", className = "me.test.mymath.DivideResponseType")
    public int divide(@WebParam(name = "x", targetNamespace = "") int x,
            @WebParam(name = "y", targetNamespace = "") int y)
            throws DivideFault_Exception {
        // TODO Auto-generated method stub
        return 0;
    }
}

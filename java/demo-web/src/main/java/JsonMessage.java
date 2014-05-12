
import java.io.Serializable;

public class JsonMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer STATUS_SUCCESS = 0;
    public static final Integer STATUS_FAIL = 1;

    // 0 - 成功， 其他值 - 失败
    private Integer status = STATUS_SUCCESS;
    private String msg;
    // 结果集
    private Object result;

    public JsonMessage() {

    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    public Object getResult() {

        return result;
    }

    public void setResult(Object result) {

        this.result = result;
    }

}

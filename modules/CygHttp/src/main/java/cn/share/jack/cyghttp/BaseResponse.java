package cn.share.jack.cyghttp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 服务器返回的json基类
 */
public class BaseResponse<T> implements Serializable {

    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * API是否请求成功
     *
     * @return 成功返回true, 失败返回false
     */
    public boolean isRequestSuccess() {
        return status == ConstantCode.REQUEST_SUCCESS;
    }
}

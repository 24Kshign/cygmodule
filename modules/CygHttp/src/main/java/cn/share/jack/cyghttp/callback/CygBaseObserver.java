package cn.share.jack.cyghttp.callback;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.share.jack.cyghttp.ApiException;
import cn.share.jack.cyghttp.app.CygApplication;

/**
 * 对请求失败的分类操作
 */
public abstract class CygBaseObserver<T> extends BaseObserver<T> {

    private static final String TAG = "CygBaseObserver";

    private boolean isNeedProgress;
    private String titleMsg;

    public CygBaseObserver() {
        this(null, null);
    }

    public CygBaseObserver(BaseImpl base) {
        this(base, null);
    }

    public CygBaseObserver(BaseImpl base, String titleMsg) {
        super(base);
        this.titleMsg = titleMsg;
        if (TextUtils.isEmpty(titleMsg)) {
            this.isNeedProgress = false;
        } else {
            this.isNeedProgress = true;
        }
    }

    @Override
    protected boolean isNeedProgressDialog() {
        return isNeedProgress;
    }

    @Override
    protected String getTitleMsg() {
        return titleMsg;
    }

    @Override
    protected void onBaseError(Throwable t) {
        StringBuffer sb = new StringBuffer();
        sb.append("请求失败：");
        if (t instanceof NetworkErrorException || t instanceof UnknownHostException) {
            sb.append("网络异常");
        } else if (t instanceof SocketTimeoutException || t instanceof ConnectException
                || t instanceof IOException) {
            sb.append("请求超时");
        } else if (t instanceof JsonSyntaxException) {
            sb.append("服务端数据解析失败");
        } else if (t instanceof ApiException) {
            ApiException exception = (ApiException) t;
            if (exception.isTokenExpried()) {
                sb.append("token异常");
            } else {
                sb.append(String.valueOf(t.getMessage()));
            }
        }
        Toast.makeText(CygApplication.getInstance(), sb.toString(), Toast.LENGTH_SHORT).show();
    }
}
package cn.share.jack.cyghttp;


import android.accounts.NetworkErrorException;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.share.jack.cyghttp.app.HttpServletAddress;
import cn.share.jack.cyghttp.convert.CustomGsonConverterFactory;
import cn.share.jack.cyghttp.interceptor.BasicParamsInterceptor;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * BaseRetrofit
 */
public abstract class BaseRetrofit {

    protected Retrofit mRetrofit;
    private static final int DEFAULT_TIME = 10;    //默认超时时间

    public BaseRetrofit() {
        //创建okHttpClient
        if (null == mRetrofit) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(DEFAULT_TIME, TimeUnit.SECONDS);
            builder.connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS);

            //设置拦截器
            builder.addInterceptor(new BasicParamsInterceptor.Builder().addParamsMap(getCommonMap()).build());
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpServletAddress.getInstance().getServletAddress())
                    .client(okHttpClient)
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    //公共参数
    protected abstract Map<String, String> getCommonMap();

    protected <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())    // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在io线程
                .timeout(DEFAULT_TIME, TimeUnit.SECONDS)    //重连间隔时间
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    private int mRetryCount;

                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                if ((throwable instanceof NetworkErrorException
                                        || throwable instanceof ConnectException
                                        || throwable instanceof SocketTimeoutException
                                        || throwable instanceof TimeoutException) && mRetryCount < 3) {
                                    mRetryCount++;
                                    return Observable.timer(2000, TimeUnit.MILLISECONDS);
                                }
                                return Observable.error(throwable);
                            }
                        });
                    }
                })
                .subscribe(observer);   //订阅
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static <T> T getPresent(Class<T> cls) {
        T instance = null;
        try {
            instance = cls.newInstance();
            if (instance == null) {
                return null;
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
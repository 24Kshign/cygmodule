package cn.share.jack.cyghttp.callback;

import android.app.Activity;

import io.reactivex.disposables.Disposable;

/**
 *
 */

public interface BaseImpl {

    boolean addDisposable(Disposable disposable);

    Activity getActivity();

}

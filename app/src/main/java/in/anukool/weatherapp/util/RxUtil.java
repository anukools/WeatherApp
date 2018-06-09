package in.anukool.weatherapp.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public final class RxUtil {

    private RxUtil() {
        throw new AssertionError();
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.newThread());
    }
}

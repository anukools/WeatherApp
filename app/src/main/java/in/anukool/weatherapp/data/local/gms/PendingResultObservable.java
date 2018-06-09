package in.anukool.weatherapp.data.local.gms;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public class PendingResultObservable<T extends Result> implements Observable.OnSubscribe<T> {

    private final PendingResult<T> mResult;
    private boolean mIsComplete = false;

    public static <T extends Result> Observable<T> create(PendingResult<T> result) {
        return Observable.create(new PendingResultObservable<>(result));
    }

    private PendingResultObservable(PendingResult<T> result) {
        this.mResult = result;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        mResult.setResultCallback(t -> {
            subscriber.onNext(t);
            mIsComplete = true;
            subscriber.onCompleted();
        });
        subscriber.add(Subscriptions.create(() -> {
            if (!mIsComplete) {
                mResult.cancel();
            }
        }));
    }
}

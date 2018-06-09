package in.anukool.weatherapp.ui.base;

/** * Created by Anukool Srivastav on 15/04/18. */
public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    @Override
    public T getMvpView() {
        return mMvpView;
    }
}

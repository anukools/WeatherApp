package in.anukool.weatherapp.ui.base;

/** * Created by Anukool Srivastav on 15/04/18. */
public interface Presenter<T extends MvpView> {

    void attachView(T mvpView);

    void detachView();

    boolean isViewAttached();

    T getMvpView();
}

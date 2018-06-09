package in.anukool.weatherapp.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {

}

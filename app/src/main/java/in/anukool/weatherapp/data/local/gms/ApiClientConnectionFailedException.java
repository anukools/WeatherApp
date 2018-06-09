package in.anukool.weatherapp.data.local.gms;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public class ApiClientConnectionFailedException extends Exception {

    public ConnectionResult getConnectionResult() {
        return mConnectionResult;
    }

    private ConnectionResult mConnectionResult;

    public ApiClientConnectionFailedException(ConnectionResult connectionResult) {
        mConnectionResult = connectionResult;
    }
}

package IVIaximus12.ustinov_lab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class MyConnectivityManager {


    //static methods

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

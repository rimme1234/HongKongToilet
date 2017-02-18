package hk.edu.ouhk.ronnie.hongkongtoilet.Utils;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Ronnie on 18/2/2017.
 */

public class GPSUtils {
    private static Location location;
    public static void setLocation(Location l){
        location=l;
    }
    public static Location getLocation(){
        return location;
    }
    public static Location getLastKnownLocation(Context c){
            LocationManager locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);
            Location l = null;
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);}
            else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {l=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);}
            else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
            {l=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);}

            if(l!=null)
            {
                return l;
            }else
            {
                return null;
            }
    }
}

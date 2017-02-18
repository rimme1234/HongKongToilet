package layout;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import hk.edu.ouhk.ronnie.hongkongtoilet.MainActivity;
import hk.edu.ouhk.ronnie.hongkongtoilet.R;
import hk.edu.ouhk.ronnie.hongkongtoilet.Toilet;
import hk.edu.ouhk.ronnie.hongkongtoilet.ToiletApplication;
import hk.edu.ouhk.ronnie.hongkongtoilet.ToiletListAdapter;
import hk.edu.ouhk.ronnie.hongkongtoilet.Utils.GPSUtils;
import hk.edu.ouhk.ronnie.hongkongtoilet.Utils.ToiletUtils;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToiletListFragment extends Fragment {
    public String url="http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?lat=22&lng=114"; //Hardcode
    String urlGet="http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?"; //Hardcode
    ListView toiletListView;
    ToiletApplication application;
    String applanguage;
    int rowNo;
    MainActivity mainActivity;
    public ToiletListFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.findItem(R.id.action_update).setEnabled(true);
        menu.findItem(R.id.action_update).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_toilet_list, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.action_search));
        application=(ToiletApplication)getContext().getApplicationContext();
        applanguage=application.getAppLanguage(getContext()).getCountry();
        rowNo=application.getRowNo();
        toiletListView=(ListView) view.findViewById(R.id.toiletListView);
        //new LocationAsyncTask().execute();
        getLocation();

        return view;
    }

    public class HttpGetAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        // Run on UI thread
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<Toilet> list= ToiletUtils.LoadJson(result,getString(R.string.distance),getString(R.string.meter));
            ToiletListAdapter adapter = new ToiletListAdapter(ToiletListFragment.this.getActivity(),R.layout.item_list,list);
            toiletListView.setAdapter(adapter);
            toiletListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position,
                                        long id) {
                    // TODO Auto-generated method stub
                    Toilet toilet=(Toilet) parent.getItemAtPosition(position);
                    DetailFragment detailFragment=new DetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("toilet", toilet);
                    detailFragment.setArguments(bundle);
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.content_main,
                            detailFragment,
                            "detail").commit();

                }
            });
        }

        @Override
        // Run on UI thread
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        // Run in background thread
        protected String doInBackground(String... url) {
            return ToiletUtils.executeHttpGet(url[0]);
        }
    }
    ProgressDialog progress;
    public void getLocation(){
        Location tempLocation=null;
        tempLocation= GPSUtils.getLastKnownLocation(getContext());
        if (tempLocation==null)
        {
            progress =new ProgressDialog(getContext());
            progress.setMessage(getString(R.string.loading));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(false);
            progress.show();
            LocationManager locationManager=(LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //return location;
                    String tempurl=urlGet+"lat="+location.getLatitude()+"&lng="+location.getLongitude();
                    String[] r=getResources().getStringArray(R.array.row_no);
                    switch (applanguage)
                    {
                        case "HK":
                            tempurl+="&lang="+getString(R.string.hk);
                            break;
                        case "CN":
                            tempurl+="&lang="+getString(R.string.cn);
                            break;
                        case "TW":
                            tempurl+="&lang="+getString(R.string.tw);
                            break;
                        default:
                            break;
                    }
                    tempurl+="&display_row="+r[rowNo];

                    new HttpGetAsyncTask().execute(tempurl);
                    progress.dismiss();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {
                    //Toast.makeText(MainActivity.this,"GET \n "+s,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            };
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100,locationListener);}
            else{locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,100,locationListener);}
            //return GPSUtils.getLocation();
        }
        else
        {//return tempLocation;
            String tempurl=urlGet+"lat="+tempLocation.getLatitude()+"&lng="+tempLocation.getLongitude();
            String[] r=getResources().getStringArray(R.array.row_no);
            switch (applanguage)
            {
                case "HK":
                    tempurl+="&lang="+getString(R.string.hk);
                    break;
                case "CN":
                    tempurl+="&lang="+getString(R.string.cn);
                    break;
                case "TW":
                    tempurl+="&lang="+getString(R.string.tw);
                    break;
                default:
                    break;
            }
            tempurl+="&display_row="+r[rowNo];
            new HttpGetAsyncTask().execute(tempurl);
            }
    }
}

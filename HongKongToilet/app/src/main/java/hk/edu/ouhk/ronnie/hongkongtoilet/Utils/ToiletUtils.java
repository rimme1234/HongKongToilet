package hk.edu.ouhk.ronnie.hongkongtoilet.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import hk.edu.ouhk.ronnie.hongkongtoilet.Toilet;

/**
 * Created by Ronnie on 18/2/2017.
 */

public class ToiletUtils {
    public static String executeHttpGet(String url)
    {
        String result = "";
        URL urlObject;
        HttpURLConnection urlConnection=null;
        try {
            urlObject = new URL(url);
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("action", "NETWORK_GET");
            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(1500);
            urlConnection.setConnectTimeout(1500);

            urlConnection.connect();
            if( urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK ) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String tempStr;
                StringBuffer stringBuffer = new StringBuffer();

                while ((tempStr = bufferedReader.readLine()) != null) {
                    stringBuffer.append(tempStr);
                }
                bufferedReader.close();
                inputStream.close();
                String responseString = stringBuffer.toString();
                result=responseString;
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return result;
    }
    public static ArrayList<Toilet> LoadJson(String str, String distance, String meter)
    {
        ArrayList<Toilet> list = new ArrayList();
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(str);
            JSONArray toiletArray = jsonObj.getJSONArray("results");

            for (int i = 0; i < toiletArray.length(); i++) {
                JSONObject obj = toiletArray.getJSONObject(i);

                Toilet toiletObject = new Toilet();
                toiletObject.id = obj.getInt("id"); //TODO object value
                toiletObject.type = obj.getString("type");
                toiletObject.name = obj.getString("name");
                toiletObject.address = obj.getString("address");
                toiletObject.lat=obj.getDouble("lat");
                toiletObject.lng=obj.getDouble("lng");
                DecimalFormat df = new DecimalFormat("####.00");
                toiletObject.distance=distance+":"+Double.parseDouble(df.format(obj.getDouble("distance")))+meter;
                list.add(toiletObject);
            }
        } catch (JSONException e) {
        }
        return list;
    }
}

package hk.edu.ouhk.ronnie.hongkongtoilet;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Ronnie on 18/2/2017.
 */

public class ToiletApplication extends Application
{
    private static ToiletApplication singleton;
    public static ToiletApplication getInstance(){
        return singleton;
    }
    SharedPreferences prefs,data;
    @Override
    public void onCreate()
    {
        singleton = this;
        update(this);
        super.onCreate();
    }

    public void update(Context ctx)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        data =getSharedPreferences ("PREF_DEMO", 0);
        String lang = prefs.getString("locale_override", "");
        int row_no=data.getInt("row_no",0);
        updateRowNo(row_no);
        updateLanguage(ctx, lang);
    }

    public void updateRowNo(int row_no) {
        data.edit().putInt("row_no",row_no).apply();
    }

    public void updateLanguage(Context ctx, String lang)
    {
        Configuration cfg = new Configuration();
        switch (lang)
        {
            case "ENG":
                cfg.locale=Locale.ENGLISH;
                break;
            case "ZH":
                cfg.locale= Locale.TRADITIONAL_CHINESE;
                break;
            case "CH":
                cfg.locale=Locale.SIMPLIFIED_CHINESE;
                break;
            case "":
                cfg.locale=Locale.getDefault();
                break;
            default:
                cfg.locale=Locale.getDefault();
                break;
        }
        prefs.edit().putString("locale_override",lang).apply();
        ctx.getResources().updateConfiguration(cfg, null);
    }

    public Locale getAppLanguage(Context ctx){
        return ctx.getResources().getConfiguration().locale;
    }
    public int getRowNo(){
        int r=data.getInt("row_no",0);
        return r;
    }
}

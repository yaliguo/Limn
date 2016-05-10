package utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import base.Config;

/**
 * Created by pe on 2016/2/24.
 */
public class DataUtils {

    public static boolean CheckNull(Object object) {
        return object == null;
    }

    public static Integer ValueOf(String s) {
        int i = -1;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());//
        String str = formatter.format(curDate);

        return str;
    }

    public static String createWeatherKey(String cityid) {
        String publicKey = Config.BASE_URL + "/data/?areaid=" + cityid + "&type=index_v&date=" +
                getDate() + "&appid=" + Config.WEATHER_APPID;
        Log.i("xxx",publicKey);
            return keys.standardURLEncoder(publicKey, Config.WEATHER_PRIVATE_KEY);

    }
}

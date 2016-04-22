package utils;

import android.util.Log;

/**
 * Created by pe on 2016/2/24.
 */
public class DataUtils {

    public static boolean CheckNull(Object object){
       return  object==null;
    }

    public static Integer ValueOf(String s){
        int i = -1;
        try {
             i = Integer.parseInt(s);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return  i;
    }
}

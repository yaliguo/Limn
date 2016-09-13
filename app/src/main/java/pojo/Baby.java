package pojo;

import android.util.Log;

/**
 * Created by pe on 2016/6/29.
 */
public abstract class Baby {
    public static String Tag = "Baby";
    public static  final Integer   NO_CACHE_INDEX = -1;
    public static  final Integer   NET_ERROR = -2;
    /**
     * 设置的缓存索引，重复会覆盖
     * @return integer
     */
    public abstract Integer getCache_index();
    public Integer getCache_Index() {
        if(null== getCache_index()){
            Log.e(Tag,"数据缓存索引Cache_Index未找到");
            return NO_CACHE_INDEX;
        }
        return getCache_index();
    }

}

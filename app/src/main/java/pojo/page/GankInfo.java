package pojo.page;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pojo.Baby;
import pojo.Gank;

/**
 * Created by pe on 2016/6/27.
 */
public class GankInfo extends Baby {
    List<String> category;
    Boolean error;
    public Results results;
    @Override
    public Integer getCache_index() {
        return 1;
    }

   public class Results{
        @SerializedName("Android") public List<Gank> androidList;
        @SerializedName("休息视频") public List<Gank> 休息视频List;
        @SerializedName("iOS") public List<Gank> iOSList;
        @SerializedName("福利") public List<Gank> 妹纸List;
        @SerializedName("拓展资源") public List<Gank> 拓展资源List;
        @SerializedName("瞎推荐") public List<Gank> 瞎推荐List;
        @SerializedName("App") public List<Gank> appList;
    }

}

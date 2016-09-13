package pojo.page;

import pojo.Baby;

/**
 * Created by pe on 2016/2/24.
 */
public class PmInfo extends Baby {
    public String des;
    public long level;
    public String quality;
    public String pm10;
    public String curPm;
    public String pm25_;

    @Override
    public Integer getCache_index() {
        return null;
    }
}

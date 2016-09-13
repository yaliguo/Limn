package pojo.page;

import java.util.List;

import pojo.Baby;
import pojo.Gank;

/**
 * Created by pe on 2016/8/30.
 */
public class FuliInfo extends Baby {
    @Override
    public Integer getCache_index() {
        return 2;
    }
    Boolean error;
    public List<Gank> results;
}

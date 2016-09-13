package pojo;

import java.util.Date;

/**
 * Created by pe on 2016/6/27.
 */
public class Gank extends Baby {
    public String url;
     public String type;
    public String desc;
    public String who;
  public boolean used;
   public Date createdAt;
     public Date updatedAt;
     public Date publishedAt;

    @Override
    public Integer getCache_index() {
        return null;
    }
}

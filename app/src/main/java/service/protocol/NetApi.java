package service.protocol;

import base.Config;
import okhttp3.ResponseBody;
import pojo.page.FuliInfo;
import pojo.page.GankInfo;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pe on 2015/11/30.
 */

/**
 * http://www.weather.com.cn/data/sk/101010100.html
 */
public interface NetApi {
    @POST("/onebox/weather/query")
   Observable<ResponseBody> getWeather (@Query("cityname")String cityname,
                                         @Query("dtype")String dtype,
                                         @Query("format")int format,
                                         @Query("key")String key);

    @GET("/data/")
    Observable<ResponseBody> getWeather(@Query("areaid")String areaid,
                                        @Query("type")String type,
                                        @Query("date")String date,
                                        @Query("appid")String appid,
                                        @Query("key")String key);

    @GET("day/{year}/{month}/{day}") Observable<GankInfo> getGankDaily(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);
    @GET("data/福利/"+ Config.MEIZHI_LIMIT+"/{page}")
    Observable<FuliInfo> getGankMeiZhi(@Path("page") int page);

}

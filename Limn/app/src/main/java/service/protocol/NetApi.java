package service.protocol;

import java.util.Map;

import okhttp3.ResponseBody;
import pojo.WeatherInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
}

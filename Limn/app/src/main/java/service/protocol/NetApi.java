package service.protocol;

import okhttp3.ResponseBody;
import pojo.WeatherInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;
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

}

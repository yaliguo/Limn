package data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import base.BaseDataLayer;
import okhttp3.ResponseBody;
import pojo.PmInfo;
import pojo.WeatherInfo;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import service.NetManager;
import service.NetResponse;
import store.WeatherStore;

/**
 * Created by phoenix on 2016/4/8.
 */
public class DataLayer extends BaseDataLayer {

    public DataLayer(NetManager manager, WeatherStore store) {
        super(manager,store);
    }

    @NonNull
    public Observable<WeatherInfo> getWehther(){
    mNetManager.getWeather()
            .filter(new Func1<NetResponse<ResponseBody>, Boolean>() {
                @Override
                public Boolean call(NetResponse<ResponseBody> responseBodyNetResponse) {
                    return responseBodyNetResponse.isOnNext();
                }
            })
            .map(new Func1<NetResponse<ResponseBody>, WeatherInfo>() {
                @Override
                public WeatherInfo call(NetResponse<ResponseBody> responseBodyNetResponse) {
                    String s = "";
                    try {
                        s = responseBodyNetResponse.getValue().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return DataLayer.this.ConvertData(s);
                }
            })
            .filter(new Func1<WeatherInfo, Boolean>() {
                @Override
                public Boolean call(WeatherInfo info) {
                    if(info.result==null&&info.reason!=null){
                        Log.e("ERROR",info.reason);
                        return false;
                    }
                    return true;
                }
            })
            .subscribe(new Action1<WeatherInfo>() {
                @Override
                public void call(WeatherInfo item) {
                    store.put(item);
                }
            });
        return  store.query();
    }

  public interface GetWeather{
      @NonNull
      Observable<WeatherInfo> call();
  }
    /**
     * json 2 Object
     */
    private WeatherInfo ConvertData(String responseBody) {
        WeatherInfo weatherInfo =null;
        try {
            weatherInfo = new Gson().fromJson(responseBody, WeatherInfo.class);
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject data = result.getJSONObject("data");
            JSONObject pm25 = data.getJSONObject("pm25");
            JSONObject pm251 = pm25.getJSONObject("pm25");
            weatherInfo.result.data.pm25.pm = new PmInfo();
            weatherInfo.result.data.pm25.pm.pm25_=pm251.getString("pm25");
            weatherInfo.result.data.pm25.pm.curPm=pm251.getString("curPm");
            weatherInfo.result.data.pm25.pm.pm10=pm251.getString("pm10");
            weatherInfo.result.data.pm25.pm.level=pm251.getLong("level");
            weatherInfo.result.data.pm25.pm.quality=pm251.getString("quality");
            weatherInfo.result.data.pm25.pm.des=pm251.getString("des");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherInfo;
    }

}

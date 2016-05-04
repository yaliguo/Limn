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
import utils.DataUtils;

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
                            WeatherInfo weatherInfo = new Gson().fromJson("{\n" +
                                    "    \"reason\": \"查询成功\",\n" +
                                    "    \"result\": {\n" +
                                    "        \"data\": {\n" +
                                    "            \"realtime\": {\n" +
                                    "                \"city_code\": \"101210701\",\n" +
                                    "                \"city_name\": \"温州\",     /*城市*/\n" +
                                    "                \"date\": \"2014-10-15\",  /*日期*/\n" +
                                    "                \"time\": \"09:00:00\",     /*更新时间*/\n" +
                                    "                \"week\": 3,\n" +
                                    "                \"moon\": \"九月廿二\",\n" +
                                    "                \"dataUptime\": 1413337811,\n" +
                                    "                \"weather\": {    /*当前实况天气*/\n" +
                                    "                    \"temperature\": \"19\",     /*温度*/\n" +
                                    "                    \"humidity\": \"54\",     /*湿度*/\n" +
                                    "                    \"info\": \"雾\",\n" +
                                    "                    \"img\": \"18\" /*18是雾这种天气所对应的图片的ID，每种天气的图片需要您自己设计，或者请阅读\n" +
                                    " https://www.juhe.cn/docs/api/id/39/aid/117*/\n" +
                                    "                },\n" +
                                    "                \"wind\": {\n" +
                                    "                    \"direct\": \"北风\",\n" +
                                    "                    \"power\": \"1级\",\n" +
                                    "                    \"offset\": null,\n" +
                                    "                    \"windspeed\": null\n" +
                                    "                }\n" +
                                    "            },\n" +
                                    "            \"life\": {     /*生活指数*/\n" +
                                    "                \"date\": \"2014-10-15\",\n" +
                                    "                \"info\": {\n" +
                                    "                    \"chuanyi\": [     /*穿衣指数*/\n" +
                                    "                        \"较舒适\",\n" +
                                    "                        \"建议着薄外套或牛仔衫裤等服装。年老体弱者宜着夹克衫、薄毛衣等。昼夜温差较大，注意适当增减衣服。\"\n" +
                                    "                    ],\n" +
                                    "                    \"ganmao\": [    /*感冒指数*/\n" +
                                    "                        \"较易发\",\n" +
                                    "                        \"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。\"\n" +
                                    "                    ],\n" +
                                    "                    \"kongtiao\": [   /*空调指数*/\n" +
                                    "                        \"较少开启\",\n" +
                                    "                        \"您将感到很舒适，一般不需要开启空调。\"\n" +
                                    "                    ],\n" +
                                    "                    \"wuran\": [     /*污染指数*/\n" +
                                    "                        \"良\",\n" +
                                    "                        \"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。\"\n" +
                                    "                    ],\n" +
                                    "                    \"xiche\": [     /*洗车指数*/\n" +
                                    "                        \"较适宜\",\n" +
                                    "                        \"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"\n" +
                                    "                    ],\n" +
                                    "                    \"yundong\": [     /*运动指数*/\n" +
                                    "                        \"较适宜\",\n" +
                                    "                        \"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意防风。\"\n" +
                                    "                    ],\n" +
                                    "                    \"ziwaixian\": [   /*紫外线*/\n" +
                                    "                        \"中等\",\n" +
                                    "                        \"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"\n" +
                                    "                    ]\n" +
                                    "                }\n" +
                                    "            },\n" +
                                    "            \"weather\": [   /*未来几天天气预报*/\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-15\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"day\": [     /*白天天气*/\n" +
                                    "                            \"0\",     /*天气ID*/\n" +
                                    "                            \"晴\",     /*天气*/\n" +
                                    "                            \"24\",     /*高温*/\n" +
                                    "                            \"东北风\",     /*风向*/\n" +
                                    "                            \"3-4 级\"      /*风力*/\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [    /*夜间天气*/\n" +
                                    "                            \"0\",\n" +
                                    "                            \"晴\",\n" +
                                    "                            \"13\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"三\",\n" +
                                    "                    \"nongli\": \"九月廿二\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-16\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"dawn\": [\n" +
                                    "                            \"0\",\n" +
                                    "                            \"晴\",\n" +
                                    "                            \"13\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"day\": [\n" +
                                    "                            \"0\",\n" +
                                    "                            \"晴\",\n" +
                                    "                            \"25\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"15\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"四\",\n" +
                                    "                    \"nongli\": \"九月廿三\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-17\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"dawn\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"15\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"day\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"26\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"16\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"五\",\n" +
                                    "                    \"nongli\": \"九月廿四\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-18\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"dawn\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"16\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"day\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"26\",\n" +
                                    "                            \"东风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"18\",\n" +
                                    "                            \"东风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"六\",\n" +
                                    "                    \"nongli\": \"九月廿五\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-19\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"dawn\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"18\",\n" +
                                    "                            \"东风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"day\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"27\",\n" +
                                    "                            \"东风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"19\",\n" +
                                    "                            \"东南风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"日\",\n" +
                                    "                    \"nongli\": \"九月廿六\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-20\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"dawn\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"19\",\n" +
                                    "                            \"东南风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"day\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"27\",\n" +
                                    "                            \"东南风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [\n" +
                                    "                            \"2\",\n" +
                                    "                            \"阴\",\n" +
                                    "                            \"18\",\n" +
                                    "                            \"南风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"一\",\n" +
                                    "                    \"nongli\": \"九月廿七\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"date\": \"2014-10-21\",\n" +
                                    "                    \"info\": {\n" +
                                    "                        \"dawn\": [\n" +
                                    "                            \"2\",\n" +
                                    "                            \"阴\",\n" +
                                    "                            \"18\",\n" +
                                    "                            \"南风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"day\": [\n" +
                                    "                            \"1\",\n" +
                                    "                            \"多云\",\n" +
                                    "                            \"26\",\n" +
                                    "                            \"东北风\",\n" +
                                    "                            \"3-4 级\"\n" +
                                    "                        ],\n" +
                                    "                        \"night\": [\n" +
                                    "                            \"2\",\n" +
                                    "                            \"阴\",\n" +
                                    "                            \"17\",\n" +
                                    "                            \"\",\n" +
                                    "                            \"微风\"\n" +
                                    "                        ]\n" +
                                    "                    },\n" +
                                    "                    \"week\": \"二\",\n" +
                                    "                    \"nongli\": \"九月廿八\"\n" +
                                    "                }\n" +
                                    "            ],\n" +
                                    "            \"pm25\": {    /*PM2.5*/\n" +
                                    "                \"key\": \"Wenzhou\",\n" +
                                    "                \"show_desc\": 0,\n" +
                                    "                \"pm25\": {\n" +
                                    "                    \"curPm\": \"97\",\n" +
                                    "                    \"pm25\": \"72\",\n" +
                                    "                    \"pm10\": \"97\",\n" +
                                    "                    \"level\": 2,\n" +
                                    "                    \"quality\": \"良\",\n" +
                                    "                    \"des\": \"可以接受的，除极少数对某种污染物特别敏感的人以外，对公众健康没有危害。\"\n" +
                                    "                },\n" +
                                    "                \"dateTime\": \"2014年10月15日09时\",\n" +
                                    "                \"cityName\": \"温州\"\n" +
                                    "            },\n" +
                                    "            \"date\": null,\n" +
                                    "            \"isForeign\": 0\n" +
                                    "        }\n" +
                                    "    },\n" +
                                    "    \"error_code\": 0\n" +
                                    "}", WeatherInfo.class);

                                store.put(weatherInfo);
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

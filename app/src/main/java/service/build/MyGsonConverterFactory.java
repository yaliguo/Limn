package service.build;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import pojo.page.WeatherInfo;
import retrofit2.Converter;

/**
 * Created by pe on 2016/2/23.
 *
 */
public class MyGsonConverterFactory extends Converter.Factory {
    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static MyGsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static MyGsonConverterFactory create(Gson gson) {
        return new MyGsonConverterFactory(gson);
    }

    private final Gson gson;

    private MyGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new GsonResponseBodyConverter<>(gson, type);
    }

   public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new GsonRequestBodyConverter<>(gson, type);
    }

    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final Type type;

        GsonResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override public T convert(ResponseBody value) throws IOException {
            Reader reader = value.charStream();
            String json = value.string();
            WeatherInfo weatherInfo = gson.fromJson(json, WeatherInfo.class);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject data = result.getJSONObject("data");
                JSONObject pm25 = data.getJSONObject("pm25");
                JSONObject pm251 = pm25.getJSONObject("pm25");
                weatherInfo.result.data.pm25.pm.pm25_=pm251.getString("pm25");
                weatherInfo.result.data.pm25.pm.curPm=pm251.getString("curPm");
                weatherInfo.result.data.pm25.pm.pm10=pm251.getString("pm10");
                weatherInfo.result.data.pm25.pm.level=pm251.getLong("level");
                weatherInfo.result.data.pm25.pm.quality=pm251.getString("quality");
                weatherInfo.result.data.pm25.pm.des=pm251.getString("des");
            } catch (JSONException e) {
                    e.printStackTrace();
            }
            try {
                return gson.fromJson(reader,type);
            } finally {
                //Utils.closeQuietly(reader);
            }
        }
    }

    final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private  final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final Type type;

        GsonRequestBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            try {
                gson.toJson(value, type, writer);
                writer.flush();
            } catch (IOException e) {
                throw new AssertionError(e); // Writing to Buffer does no I/O.
            }
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }
}

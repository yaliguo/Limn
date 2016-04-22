package phoenix.pe.demod.adapter;

import android.content.Context;

import java.util.List;

import base.PDefautAdapter;
import base.PViewHolder;
import phoenix.pe.demod.R;
import pojo.WeatherItem;

/**
 * Created by pe on 2016/3/1.
 */
public class WeatherWeekAdapter extends PDefautAdapter <WeatherItem>{
    public WeatherWeekAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(PViewHolder holder, WeatherItem item) {
        holder.setImageLv(R.id.itm_home_gradImg,item.img)
                .setText(R.id.itm_home_gradDay,holder.position==0?"明日" : "周"+item.day);
    }
}

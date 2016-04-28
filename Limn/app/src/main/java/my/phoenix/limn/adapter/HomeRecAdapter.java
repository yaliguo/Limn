package my.phoenix.limn.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import base.PAdapterSupportType;
import base.PDefautAdapter;
import base.PViewHolder;
import my.phoenix.limn.R;
import pojo.WeatherInfo;

/**
 * Created by pe on 2015/12/18.
 */
public class HomeRecAdapter extends PDefautAdapter<WeatherInfo> implements PAdapterSupportType<WeatherInfo>{
    private boolean flag = false;
    public HomeRecAdapter(Context context, List<WeatherInfo> data, int layoutId) {
        super(context, data, layoutId,true);
    }

    @Override
    public void convert(PViewHolder holder, WeatherInfo item) {
        switch (holder.getmLayoutId()){
            case R.layout.item_main_rv:
                TextView mTv = holder.getView(R.id.mtv);
                mTv.setText(item.toString());
                break;

            case R.layout.item_main_rv2:
                TextView mTv2 = holder.getView(R.id.mtv);
                mTv2.setText(item.toString()+"fasdf");
                break;
        }

    }

    @Override
    public PAdapterSupportType setSupportType() {
        return this;
    }

    @Override
    public int getLayoutId(int position, WeatherInfo weatherInfo) {
        if(flag){
            flag=false;
            return R.layout.item_main_rv;
        }else{
            flag=true;
            return R.layout.item_main_rv2;
        }
    }

    @Override
    public int getMyViewTypeCount() {
        return 0;
    }

    @Override
    public int getMyItemViewType(int postion, WeatherInfo weatherInfo) {
        return 0;
    }


}

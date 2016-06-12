package base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by phoenix on 2015/12/22.
 *  通用的适配器，可以适应多种条目，利用holder实现链式编程
 *
 *      多种条目实现步骤：
 *
 *          1.自己的adpater 实现 PAdapterSupportType 接口 ，实现方法
 *
 *          2.自己的adapter 构造器传入 support(true) 参数确认开启多条展示
 *
 *          3.如果使用的support那么构造传入的layoutId自动忽略，而是通过接口方法判断返回的layoutid
 *
 *          4.在adapter的convert方法中依据不同的layout来做不同的初始化
 *
 */
public abstract class PDefautAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> mDatas;
    private int mLayoutId;
    private PAdapterSupportType mSupport;
    private boolean isSupportType;
    public PDefautAdapter(Context context, List<T> data, int layoutId) {
        this(context,data,layoutId,false);
    }
    public PDefautAdapter(Context context, List<T> data, int layoutId,boolean support) {
        this.context = context;
        this.mDatas = data;
        this.mLayoutId = layoutId;
        isSupportType=support;
        if(support){
            mSupport=setSupportType();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null!=mSupport&&isSupportType){
            int layoutId = mSupport.getLayoutId(position, getItem(position));
            PViewHolder holder = PViewHolder.getHolder(context, convertView, position, layoutId, parent);
            convert(holder, getItem(position));
            return  holder.getConvertView();
        }
        PViewHolder holder = PViewHolder.getHolder(context, convertView, position, mLayoutId, parent);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(PViewHolder holder, T item);
    public  PAdapterSupportType setSupportType(){
        return null;
    }
}

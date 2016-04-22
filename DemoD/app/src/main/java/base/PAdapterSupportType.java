package base;

/**
 * Created by phoenix  on 2015/12/22.
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
 */
public interface PAdapterSupportType<T> {
    int getLayoutId(int position, T t);

    int getMyViewTypeCount();

    int getMyItemViewType(int postion, T t);
}

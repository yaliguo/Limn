package my.phoenix.limn;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import base.App;
import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import my.phoenix.limn.adapter.HomeRecAdapter;
import pojo.page.WeatherInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 响应 --
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.rv )
    RecyclerView mRv;
    private WeatherInfo mData;
    @BindView(R.id.lv )
    ListView mLv;

    @BindView(R.id.img)
    ImageView img;
    private Observable<? extends WeatherInfo> weatherNet;

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }
    @Override
    public  void init() {
        ButterKnife.bind(this);
        /**
         * retrofit
         */
      //  initBind();
        /**
         * rx
         */
         initNew();
        /**
         * rx 加上 map操作符
         */
         initRxMap();
        /**
         * rx 另一些操作符
         */
        //initRxList();

        /**
         * rx android
         */
        initRxAndroid();

        /**
         * rx 模拟三级缓存的例子
         */
        //initCache();
    }


    public void setUpRecycleView(WeatherInfo j) {
        mData = j;
        ArrayList<WeatherInfo> weatherInfos = new ArrayList<>();
        weatherInfos.add(mData);
        weatherInfos.add(mData);
        weatherInfos.add(mData);
        weatherInfos.add(mData);
        weatherInfos.add(mData);
        //mRv.setLayoutManager(new LinearLayoutManager(this));
       // mRv.setAdapter(new MainAdapter());
        mLv.setAdapter(new HomeRecAdapter(MainActivity.this, weatherInfos, R.layout.item_main_rv));
    }

private Observable<WeatherInfo> getWeatherCache(){
    ArrayList<WeatherInfo> query = App.mOrmInstance.query(WeatherInfo.class);
   return Observable.just(query.get(0));

}
    /**
     * 从网络获取数据
     * @return
     */
//    public Observable<WeatherInfo> getWeatherNet() {
//       // Observable<WeatherInfo> weather = api.getWeather("","",1,"");
//        ArrayList<WeatherInfo> query = App.mOrmInstance.query(WeatherInfo.class);
//        if(null!=query.get(0).temp){
//            Integer integer = Integer.valueOf(query.get(0).temp);
//            query.get(0).temp=integer+1+"";
//            query.get(0).city="第"+ query.get(0).temp+"次获取";
//        }else{
//            query.get(0).temp="1";
//        }
//        App.mOrmInstance.save(query);
//        //long insert = App.mOrmInstance.insert(weather, ConflictAlgorithm.Replace);
//        return null;
//    }
//
//    private void initBind() {
//        Observable.concat(getWeatherCache(),getWeatherNet())
//                //.subscribeOn(Schedulers.io())
//                .first(new Func1<WeatherInfo, Boolean>() {
//                    @Override
//                    public Boolean call(WeatherInfo weatherInfo) {
//                        if (null != weatherInfo.temp) {
//                            Integer integer = Integer.valueOf(weatherInfo.temp);
//                            if (integer > 50) return true;
//                        }
//                        return false;
//                    }
//                })
//               // .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<WeatherInfo>() {
//                    @Override
//                    public void call(WeatherInfo weatherInfo) {
//                        showToastOnUI("请求成功");
//                        setUpRecycleView(weatherInfo);
//                    }
//                });
//
//    }

    private void initCache() {
        /**
         * 通过concat 连接Observable对象，不交错 （Merge会交错）
         */
        Observable<String> ob1 = Observable.create(new Observable.OnSubscribe<String>() {
         @Override
         public void call(Subscriber<? super String> subscriber) {

            if(2>13){
                subscriber.onNext("测试1");

            }else{
                subscriber.onCompleted();

            }
         }
     });

        Observable<String> ob2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                if (2 > 13) {
                    subscriber.onNext("测试2");

                } else {
                    subscriber.onCompleted();

                }
            }
        });
        Observable<String> ob3 = Observable.just("测试3");

        Observable.concat(ob1, ob2, ob3)
                .first()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("list", "数据" + s);
                    }
                });
    }

    private void initRxAndroid() {
        /**
         * 模拟使用调度器切换线程
         *
         */
        simFun().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<ArrayList<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(ArrayList<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //..
                    }
                });
    }

    private void initRxList() {
        //我想打出所有的集合数据这样操作
//        simFun().subscribe(new Action1<ArrayList<String>>() {
//            @Override
//            public void call(ArrayList<String> strings) {
//                for (String string : strings) {
//                    Log.i("list",string);
//                }
//
//            }
//        });

        /**
         * 使用 from接受一个集合作为输入，每次输出一个集合中的数据 ，避免了froeach 但是嵌套
         */
//       simFun().subscribe(new Action1<ArrayList<String>>() {
//           @Override
//           public void call(ArrayList<String> strings) {
//               Observable.from(strings)
//                       .subscribe(new Action1<String>() {
//                           @Override
//                           public void call(String s) {
//                               Log.i("list",s);
//                           }
//                       });
//           }
//       });

        /**
         * 使用flatmap 返回新的obserable
         */
//        simFun().flatMap(new Func1<List<String>, Observable<String>>() {
//                 @Override
//                   public Observable<String> call(List<String> urls) {
//                           return Observable.from(urls);
//                       }
//           })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.i("list",s);
//                    }eo
//                });8

        /**
         * 根据URl列表 返回 URl对应标题
         */
//        simFun().flatMap(new Func1<ArrayList<String>, Observable<String>>() {
//            @Override
//            public Observable<String> call(ArrayList<String> strings) {
//                //from字 每次返回一个集合中的数据 有flat字 包装成 Observable 返回
//                return Observable.from(strings);
//            }
//        })
//                .flatMap(new Func1<String, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(String s) {
//                        return getTitle(s);
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.i("LIST",s);
//                    }
//                });

        /**
         * 据URl列表 返回 URl对应标题 并且有过滤条件 filter字
         */
//        simFun().flatMap(new Func1<ArrayList<String>, Observable<String>>() {
//            @Override
//            public Observable<String> call(ArrayList<String> strings) {
//                return Observable.from(strings);
//            }
//        })
//                .flatMap(new Func1<String, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(String s) {
//                        return getTitle(s);
//                    }
//                })
//                .filter(new Func1<String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) {
//                        return !"title4".equals(s);
//                    }
//                })
//                //限制输出5个
//                .take(5)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.i("LIST",s);
//                    }
//                });
        /**
         * doOnNext()允许我们在每次输出一个元素之前做一些额外的事情，比如这里的保存标题。

         */

//        simFun().flatMap(new Func1<ArrayList<String>, Observable<String>>() {
//            @Override
//            public Observable<String> call(ArrayList<String> strings) {
//                return Observable.from(strings);
//            }
//        })
//                .flatMap(new Func1<String, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(String s) {
//                        return getTitle(s);
//                    }
//                })
//                .filter(new Func1<String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) {
//                        return !"title4".equals(s);
//                    }
//                })
//                        //限制输出5个
//                .take(5)
//                .doOnNext(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//
//                        // 模拟保存到本地之类的操作
//
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.i("LIST", s);
//                    }
//                });



    }
    /**
     * 模拟网络请求更具URL返回数据
     */
    private Observable<String> getTitle(String s){
        HashMap<String, String> map  = new HashMap<>();
        for(int i = 0;i< 10 ;i++){
            map.put("url" + i, "title" + i);
        }
        return Observable.just(map.get(s));
    }

    /**
     * 模拟网络请求返回Observable集合对象 （URL列表）
     * @return
     */
    private Observable<ArrayList<String>> simFun() {
        ArrayList<String> strings = new ArrayList<>();
        for(int i = 0;i< 10 ;i++){
            strings.add("url" + i);
        }

        return  Observable.just(strings);
    }

    private void initRxMap() {
        /**
         * 通过map转换observable
         * map 中的函数 泛型第一个传入，第二个传出
         */
        Observable.just("HELLO ")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "加了个标记";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        showToastOnUI(s);
                    }
                });

    }

    private void initNew() {
        /**
         * 1 创建一个可观察者
         */
//        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello  Word");
//                subscriber.onCompleted();
//            }
//        });
        /**
         * 1.1 创建一个订阅者
         */
//        Subscriber subscriber = new Subscriber() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                showToastOnUI(o.toString());
//            }
//        };
//        stringObservable.subscribe(subscriber);

        /**
         * 2 创建一个只发出一次消息的可观察者
         */
        Observable<String> stringObservable = Observable.just("Hello Word");


        /**
         * 2.1创建一个简化的订阅者
         *  不关心OnComplete和OnError，我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类
         */

        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                showToastOnUI(s);
            }
        };
        stringObservable.subscribe(action1);


        /**
         * 3 上面的代码简化
         */
//       Observable.just("Hello word")
//               .subscribe(new Action1<String>() {
//                   @Override
//                   public void call(String s) {
//                       showToastOnUI(s);
//                   }
//               });

    }


    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder>{


        @Override
        public MainHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            MainHolder holder = new MainHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_main_rv, viewGroup,
                    false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MainHolder mainHolder, int i) {
            mainHolder.mtv.setText(mData.toString());
        }

        @Override
        public int getItemCount() {
            return 12;
        }

        class MainHolder extends  RecyclerView.ViewHolder{
            TextView mtv;
            public MainHolder(View itemView) {
                super(itemView);
                mtv= (TextView) itemView.findViewById(R.id.mtv);
            }
        }
    }

}

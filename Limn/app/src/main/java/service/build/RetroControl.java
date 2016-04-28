package service.build;

import retrofit2.Retrofit;
import service.protocol.NetApi;

/**
 * Created by pe on 2015/12/16.
 */
public class RetroControl {

    private static NetApi netApi;
    public static NetApi getSimAPi(){
        if(null==netApi){
            Retrofit sim = new RetroBuild().getSim();
            netApi = sim.create(NetApi.class);
        }
        return netApi;
    }

}

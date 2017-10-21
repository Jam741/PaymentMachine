package com.bolink.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xulu on 2017/8/24.
 */

public interface HttpApi {
    //baseUrl: 'http://jarvisqh.vicp.io/api-web/centralpayment',
    //baseUrl: 'https://beta.bolink.club/unionapi/centralpayment',
    @GET("/login")
    Observable<String> rxLogin(@Query("machine_id") String machine_id,@Query("password") String password);

    @GET("update.xml")
    Observable<byte[]> rxCheckUpdate();

}

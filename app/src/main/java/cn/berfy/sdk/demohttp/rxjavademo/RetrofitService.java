package cn.berfy.sdk.demohttp.rxjavademo;

import cn.berfy.sdk.demohttp.model.Data;
import cn.berfy.sdk.demohttp.rxjavademo.model.Book;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by win764-1 on 2016/12/12.
 */
public interface RetrofitService {

    @GET("api/v1/update_prompt")
    Observable<Data> checkUpdate(@Query("version") String version);

    @POST("book/search")
    @FormUrlEncoded
    Observable<Book> getSearchBooksPost(@Field("q") String name,
                                        @Field("tag") String tag, @Field("start") int start,
                                        @Field("count") int count);
}

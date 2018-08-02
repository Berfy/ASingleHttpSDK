package cn.berfy.sdk.http.rxjava.service;

import cn.berfy.sdk.http.rxjava.service.entity.Book;
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
    @GET("book/search")
    Observable<Book> getSearchBooks(@Query("q") String name,
                                    @Query("tag") String tag, @Query("start") int start,
                                    @Query("count") int count);

    @POST("book/search")
    @FormUrlEncoded
    Observable<Book> getSearchBooksPost(@Field("q") String name);
}

package cn.berfy.sdk.demohttp.rxjavademo

import cn.berfy.sdk.demohttp.model.Data
import cn.berfy.sdk.demohttp.rxjavademo.model.Book
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

/**
 * Created by win764-1 on 2016/12/12.
 */
interface RetrofitService {

    @GET("api/v1/update_prompt")
    fun checkUpdate(@Query("version") version: String): Observable<Data>

    @POST("book/search")
    @FormUrlEncoded
    fun getSearchBooksPost(@Field("q") name: String,
                           @Field("tag") tag: String, @Field("start") start: Int,
                           @Field("count") count: Int): Observable<Book>
}

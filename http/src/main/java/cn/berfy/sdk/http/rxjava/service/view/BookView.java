package cn.berfy.sdk.http.rxjava.service.view;

import cn.berfy.sdk.http.rxjava.service.entity.Book;

/**
 * Created by win764-1 on 2016/12/12.
 */

public interface BookView extends View {
    void onSuccess(Book mBook);
    void onError(String result);
}

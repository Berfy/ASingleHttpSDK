package cn.berfy.sdk.demohttp.rxjavademo.presenter

import android.content.Context

import cn.berfy.sdk.demohttp.model.Data
import cn.berfy.sdk.demohttp.rxjavademo.DemoHttpApi
import cn.berfy.sdk.demohttp.rxjavademo.model.Book
import cn.berfy.sdk.demohttp.rxjavademo.view.IDemoHttpView
import cn.berfy.sdk.mvpbase.prensenter.BasePresenter
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class DemoHttpPresenter : BasePresenter<IDemoHttpView>() {

    private var mCompositeSubscription: CompositeSubscription? = null

    fun init() {

    }

    override fun attach(context: Context, view: IDemoHttpView) {
        super.attach(context, view)
        mCompositeSubscription = CompositeSubscription()
    }

    override fun detach() {
        super.detach()
        if (mCompositeSubscription!!.hasSubscriptions()) {
            mCompositeSubscription!!.unsubscribe()
        }
    }

    fun checkUpdate(version: String, observer: Observer<Data>) {
        mCompositeSubscription!!.add(DemoHttpApi.instance?.server?.checkUpdate(version)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(observer)
        )
    }

    fun getSearchBooks(name: String, tag: String, start: Int, count: Int, observer: Observer<Book>) {
        mCompositeSubscription!!.add(DemoHttpApi.instance?.server?.getSearchBooksPost(name, tag, start, count)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(observer)
        )
    }
}

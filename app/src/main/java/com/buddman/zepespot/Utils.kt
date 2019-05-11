package com.buddman.zepespot


import android.util.Log
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

private val DEFAULT_ERROR_HANDLE: (Throwable) -> Unit = {

    if(it.message!!.isNotBlank()) Log.e("DEFAULT_ERROR_HANDLE", it.message)
    if(!AppController.checkInternetConnection(AppController.context)) AppController.context.toast("인터넷 연결 상태를 확인해주세요.")

}


fun <T> Observable<T>.subscribeOnIO() : Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.onUI(onNext: (T) -> Unit): Disposable {
    return this.onUI(onNext, DEFAULT_ERROR_HANDLE)
}

fun <T> Observable<T>.onUI(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable {

    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError)

}

fun TextView.textString() : String = this.text.toString()

fun View.show() { visibility = View.VISIBLE }
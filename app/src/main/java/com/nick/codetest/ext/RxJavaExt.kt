package com.nick.codetest.ext

import android.util.Log
import com.nick.codetest.CodeTestApplication
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.net.HttpURLConnection

fun <T> Observable<T>.runInBackgroundLogoutIfExpired(): Observable<T> {
    return this.logoutIfExpired().runInBackground()
}

fun <T> Observable<T>.runInBackground(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onTerminateDetach()
}

fun Disposable?.disposeBy(disposeBag: CompositeDisposable){
    disposeBag.add(this)
}

fun Disposable.disposedBy(disposedBag: CompositeDisposable) {
    disposedBag.add(this)
}

fun <T> Observable<T>.onMainThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.logoutIfExpired(): Observable<T> {

    return this.doOnError { t ->
//        Log.e("logoutIfExpired","${t is HttpException}")
        if (t.isForbidden()) {
//            Log.e("logoutIfExpired","isForbidden")
            CodeTestApplication.LOGOUT_SUBJECT.onNext(true)
        }
        if (t.isUnauthorized()) {
//            Log.e("logoutIfExpired","isUnauthorized")
            CodeTestApplication.LOGOUT_SUBJECT.onNext(true)
        }
//        if (t.isTokenException()) {  //custom exception
//            Log.e("logoutIfExpired","isTokenException")
//            CodeTestApplication.LOGOUT_SUBJECT.onNext(true)
//        }


    }
}

fun <T> Observable<T>.retryWhenNotForbidden(times: Long = 3): Observable<T> {
    return this.retry(times) { t ->
        !t.isForbidden()
    }
}

private fun Throwable.isForbidden(): Boolean {
    return this is HttpException && this.code() == HttpURLConnection.HTTP_FORBIDDEN //HTTP_UNAUTHORIZED
}
private fun Throwable.isUnauthorized(): Boolean {
    return this is HttpException && this.code() == HttpURLConnection.HTTP_UNAUTHORIZED
}

//private fun Throwable.isTokenException(): Boolean {
//    return this is DjangoTokenException
//}
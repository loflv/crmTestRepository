package com.example.crm_test

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cookie

import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("1")
            it.onNext("a")
            it.onNext("2")
        })
            .flatMap {
                Observable.just(it.toInt())
            }
            .flatMap {
                Observable.just(it + 1)
            }.flatMap {
                Observable.just(it.toString())
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("liwu", it.toString())
            }, {
                Log.e("liwu error", it.message.toString())
            },{
                Logger.d("kk")
            })


    }
}

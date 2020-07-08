package com.example.crm_test

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        Observable.just(getInts())
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

    fun getInts():Int{
        Log.d("liwu2",Thread.currentThread().toString())
        return  1
    }
}

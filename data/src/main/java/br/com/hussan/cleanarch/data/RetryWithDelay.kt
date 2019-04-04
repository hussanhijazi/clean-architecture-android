package br.com.hussan.cleanarch.data

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class RetryWithDelay(private val maxRetries: List<Int>) :
    Function<Observable<out Throwable>, Observable<*>> {
    override fun apply(attempts: Observable<out Throwable>): Observable<*> {
        return attempts
            .zipWith(
                Observable.range(0, maxRetries.size.plus(1)),
                BiFunction { error: Throwable, count: Int -> Pair(error, count) })
            .flatMap { item ->
                val time = maxRetries[item.second].toLong()
                if (item.second < maxRetries.size) {
                    when (item.first) {
                        is SocketException,
                        is SocketTimeoutException,
                        is UnknownHostException -> {
                            Observable.timer(time, TimeUnit.SECONDS)
                        }
                        is HttpException -> {
                            if ((item.first as HttpException).code() in 500..598)
                                Observable.timer(time, TimeUnit.SECONDS)
                            else Observable.error(item.first)
                        }
                        else -> Observable.error(item.first)
                    }
                } else Observable.error(item.first)
            }
    }

}

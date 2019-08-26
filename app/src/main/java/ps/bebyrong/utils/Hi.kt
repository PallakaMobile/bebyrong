package ps.bebyrong.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

object Hi {

    /**
     * Broadcast any event
     */
    private val subject: Subject<Any> = PublishSubject.create()

    @Suppress("UNCHECKED_CAST")
    fun <X> listen(typeClass: Class<X>, onNext: Consumer<X>, onError: Consumer<Throwable>): Disposable =
        subject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { o -> o.javaClass == typeClass }
            .map { o -> o as X }
            .subscribe(onNext, onError)

    fun broadcast(event: Any) = subject.onNext(event)
}
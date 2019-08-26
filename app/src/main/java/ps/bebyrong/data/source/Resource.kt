package ps.bebyrong.data.source

/**
 **********************************************
 * Created by ukie on 4/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable) : Resource<T>()
}
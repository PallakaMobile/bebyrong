package ps.bebyrong.data.repo

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ps.bebyrong.data.model.response.ResponseDetailStock
import ps.bebyrong.data.model.response.ResponseStock
import ps.bebyrong.data.model.response.ResponseYear
import ps.bebyrong.data.source.Resource
import ps.bebyrong.data.source.rest.BebyrongAPI

/**
 **********************************************
 * Created by ukie on 4/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

class RepoStock(private val bebyrongAPI: BebyrongAPI) {

    fun getStock(jenis: String, search: String): Flowable<Resource<ResponseStock>> {
        return bebyrongAPI.getListPasar(jenis, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseStock>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getDetailStock(queryMap: LinkedHashMap<String, String>): Flowable<Resource<ResponseDetailStock>> {
        return bebyrongAPI.getDetailStock(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseDetailStock>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getYear(): Flowable<Resource<ResponseYear>> {
        return bebyrongAPI.getListYear()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseYear>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())

    }
}
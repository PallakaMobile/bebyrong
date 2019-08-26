package ps.bebyrong.data.repo

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ps.bebyrong.data.model.response.ResponseFoodList
import ps.bebyrong.data.model.response.ResponseFormersProduct
import ps.bebyrong.data.model.response.ResponseListFormers
import ps.bebyrong.data.model.response.ResponseProfileFormers
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

class RepoCommodity(private val bebyrongAPI: BebyrongAPI) {

    fun getListPangan(queryMap: LinkedHashMap<String, String>): Flowable<Resource<ResponseFoodList>> {
        return bebyrongAPI.getListPangan(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseFoodList>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getListKelompokTani(queryMap: LinkedHashMap<String, String>): Flowable<Resource<ResponseListFormers>> {
        return bebyrongAPI.getListKelompokTani(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseListFormers>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getProfileFormers(category: String, idPangan: String): Flowable<Resource<ResponseProfileFormers>> {
        return bebyrongAPI.getProfileFormers(category, idPangan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseProfileFormers>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getFormersProduct(idFormers: String): Flowable<Resource<ResponseFormersProduct>> {
        return bebyrongAPI.getFormersProduct(idFormers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseFormersProduct>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

}
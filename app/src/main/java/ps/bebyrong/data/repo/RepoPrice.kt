package ps.bebyrong.data.repo

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ps.bebyrong.data.model.response.ResponsePrice
import ps.bebyrong.data.model.response.ResponsePriceDetail
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

class RepoPrice(private val bebyrongAPI: BebyrongAPI) {

    fun getPrice(queryMap: LinkedHashMap<String, String>): Flowable<Resource<ResponsePrice>> {
        return bebyrongAPI.getHargaPangan(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponsePrice>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getPriceDetail(id: String): Flowable<Resource<ResponsePriceDetail>> {
        return bebyrongAPI.getHargaPanganDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponsePriceDetail>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }
}
package ps.bebyrong.data.repo

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ps.bebyrong.data.model.response.*
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

class RepoNews(private val bebyrongAPI: BebyrongAPI) {

    fun getNews(queryMap: LinkedHashMap<String, String>): Flowable<Resource<ResponseNews>> {
        return bebyrongAPI.getBerita(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseNews>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getNewsDetail(id: String): Flowable<Resource<ResponseNewsDetail>> {
        return bebyrongAPI.getDetailBerita(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseNewsDetail>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getNewsCategory(): Flowable<Resource<ResponseNewsCategory>> {
        return bebyrongAPI.getKategoriBerita()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseNewsCategory>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getNewsComment(id: String, page: String): Flowable<Resource<ResponseNewsComment>> {
        return bebyrongAPI.getNewsComment(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseNewsComment>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun postNewsComment(id: String, headerMap: LinkedHashMap<String, String>): Flowable<Resource<Diagnostic>> {
        return bebyrongAPI.postComment(id, headerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<Diagnostic>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }

    fun getShareLink(id: String): Flowable<Resource<ResponseShare>> {
        return bebyrongAPI.getShareLink(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Resource<ResponseShare>> {
                    Resource.Success(it.body())
                }.onErrorReturn { Resource.Failure(it) }
                .startWith(Resource.Loading())
    }
}
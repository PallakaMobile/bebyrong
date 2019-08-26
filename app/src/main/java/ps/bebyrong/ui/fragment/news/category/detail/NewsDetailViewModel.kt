package ps.bebyrong.ui.fragment.news.category.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.Diagnostic
import ps.bebyrong.data.model.response.ResponseNewsComment
import ps.bebyrong.data.model.response.ResponseNewsDetail
import ps.bebyrong.data.model.response.ResponseShare
import ps.bebyrong.data.repo.RepoNews
import ps.bebyrong.data.source.Resource

/**
 **********************************************
 * Created by ukie on 4/29/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class NewsDetailViewModel(private val repoNews: RepoNews) : BaseViewModel() {
    val detailNews = MutableLiveData<ResponseNewsDetail.Data>()

    val isLoading = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()


    fun getDetailNews(id: String) {
        composite {
            repoNews.getNewsDetail(id)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                detailNews.value = it.data?.data
                                isContent.value = true
                                isLoading.value = false
                            }
                            is Resource.Failure -> {
                                it.throwable.printStackTrace()
                                isLoading.value = false
                            }
                            is Resource.Loading -> {
                                e { "im loading" }
                                isLoading.value = true
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    fun getNewsComment(id: String, page: String, isFirst: Boolean): LiveData<ResponseNewsComment> {
        val newsComment = MutableLiveData<ResponseNewsComment>()
        composite {
            repoNews.getNewsComment(id, page)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                newsComment.value = it.data
                                isContent.value = true
                                isLoading.value = false
                            }
                            is Resource.Failure -> {
                                it.throwable.printStackTrace()
                                isLoading.value = false
                            }
                            is Resource.Loading -> {
                                e { "im loading" }
                                if (isFirst)
                                    isLoading.value = true
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
        }
        return newsComment
    }

    fun postComment(id: String, headerMap: LinkedHashMap<String, String>): LiveData<Diagnostic> {
        val newsComment = MutableLiveData<Diagnostic>()
        composite {
            repoNews.postNewsComment(id, headerMap)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                newsComment.value = it.data
                                isContent.value = true
                                isLoading.value = false
                            }
                            is Resource.Failure -> {
                                it.throwable.printStackTrace()
                                isLoading.value = false
                            }
                            is Resource.Loading -> {
                                e { "im loading" }
                                isLoading.value = true
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
        }
        return newsComment
    }

    fun getShareLink(id: String): LiveData<ResponseShare.Data> {
        val linkShare = MutableLiveData<ResponseShare.Data>()
        composite {
            repoNews.getShareLink(id)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                linkShare.value = it.data?.data
                                isContent.value = true
                                isLoading.value = false
                            }
                            is Resource.Failure -> {
                                it.throwable.printStackTrace()
                                isLoading.value = false
                            }
                            is Resource.Loading -> {
                                e { "im loading" }
                                isLoading.value = true
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
        }
        return linkShare
    }
}
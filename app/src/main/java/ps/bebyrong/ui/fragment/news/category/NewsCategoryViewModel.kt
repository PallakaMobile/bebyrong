package ps.bebyrong.ui.fragment.news.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponseNews
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

class NewsCategoryViewModel(private val repoNews: RepoNews) : BaseViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isNoData = MutableLiveData<Boolean>()
    val isDisconnect = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    fun getNews(queryMap: LinkedHashMap<String, String>, isFirst: Boolean): LiveData<ResponseNews> {
        val listNews = MutableLiveData<ResponseNews>()
        composite {
            repoNews.getNews(queryMap).subscribe({
                when (it) {
                    is Resource.Success -> {
                        listNews.value = it.data
                        it.data?.data?.apply {
                            if (this.isNotEmpty()) {
                                isNoData.value = false
                                isContent.value = true
                            } else
                                isNoData.value = true
                        }

                        isLoading.value = false
                    }
                    is Resource.Failure -> {
                        it.throwable.printStackTrace()
                        isDisconnect.value = true
                        isLoading.value = false
                    }
                    is Resource.Loading -> {
                        e { "im loading" }
                        if (isFirst)
                            isLoading.value = true
                    }
                }
            }, { it.printStackTrace() })
        }
        return listNews
    }
}
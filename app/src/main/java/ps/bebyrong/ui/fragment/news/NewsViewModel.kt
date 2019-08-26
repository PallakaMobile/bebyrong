package ps.bebyrong.ui.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponseNewsCategory
import ps.bebyrong.data.repo.RepoNews
import ps.bebyrong.data.source.Resource

/**
 **********************************************
 * Created by ukie on 4/30/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

class NewsViewModel(private val repoNews: RepoNews) : BaseViewModel() {
    fun getNewsCategory(): LiveData<ResponseNewsCategory> {
        val newsCategory = MutableLiveData<ResponseNewsCategory>()
        composite {
            repoNews.getNewsCategory().subscribe({
                when (it) {
                    is Resource.Success -> {
                        newsCategory.value = it.data
                    }
                    is Resource.Failure -> {
                        it.throwable.printStackTrace()
                    }
                    is Resource.Loading -> {
                        e { "im loading" }
                    }
                }
            }, { it.printStackTrace() })
        }
        return newsCategory
    }
}
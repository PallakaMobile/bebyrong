package ps.bebyrong.ui.fragment.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponsePrice
import ps.bebyrong.data.source.Resource
import ps.bebyrong.data.repo.RepoPrice

/**
 **********************************************
 * Created by ukie on 4/29/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class PriceViewModel(private val repoPrice: RepoPrice) : BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isNoData = MutableLiveData<Boolean>()
    val isDisconnect = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    fun getPrice(queryMap: LinkedHashMap<String, String>, isFirst: Boolean): LiveData<ResponsePrice> {
        val listNews = MutableLiveData<ResponsePrice>()
        composite {
            repoPrice.getPrice(queryMap).subscribe({
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

                        isLoading.postValue(false)
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
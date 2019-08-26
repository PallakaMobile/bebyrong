package ps.bebyrong.ui.fragment.price.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponsePriceDetail
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
class PriceDetailViewModel(private val repoPrice: RepoPrice) : BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    fun getPriceDetail(id: String): LiveData<ResponsePriceDetail> {
        val newsDetail = MutableLiveData<ResponsePriceDetail>()
        composite {
            repoPrice.getPriceDetail(id).subscribe({
                when (it) {
                    is Resource.Success -> {
                        newsDetail.value = it.data

                        isContent.value = true
                        isLoading.postValue(false)
                    }
                    is Resource.Failure -> {
                        it.throwable.printStackTrace()
                        isLoading.value = false
                    }
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                }
            }, { it.printStackTrace() })
        }
        return newsDetail
    }
}
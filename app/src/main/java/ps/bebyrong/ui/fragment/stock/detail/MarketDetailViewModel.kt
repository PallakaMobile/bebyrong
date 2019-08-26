package ps.bebyrong.ui.fragment.stock.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponseDetailStock
import ps.bebyrong.data.model.response.ResponseYear
import ps.bebyrong.data.repo.RepoStock
import ps.bebyrong.data.source.Resource

/**
 **********************************************
 * Created by ukie on 08/05/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class MarketDetailViewModel(private val repoStock: RepoStock) : BaseViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    fun getYear(): LiveData<List<ResponseYear.DataItem>> {
        val years = MutableLiveData<List<ResponseYear.DataItem>>()
        composite {
            repoStock.getYear()
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                years.value = it.data?.data
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
        return years
    }

    fun getDetailStock(queryMap: LinkedHashMap<String, String>): LiveData<ResponseDetailStock.Data> {
        val detailStock = MutableLiveData<ResponseDetailStock.Data>()
        composite {
            repoStock.getDetailStock(queryMap)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                detailStock.value = it.data?.data
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
        return detailStock
    }
}
package ps.bebyrong.ui.fragment.commodity.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponseListFormers
import ps.bebyrong.data.repo.RepoCommodity
import ps.bebyrong.data.source.Resource

/**
 **********************************************
 * Created by ukie on 4/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityInfoViewModel(private val repoCommodity: RepoCommodity) : BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isNoData = MutableLiveData<Boolean>()
    val isDisconnect = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    fun getListKelompokTani(queryMap: LinkedHashMap<String, String>, isFirst: Boolean): LiveData<ResponseListFormers> {
        val kelompokTani = MutableLiveData<ResponseListFormers>()
        composite {
            repoCommodity.getListKelompokTani(queryMap)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                kelompokTani.value = it.data
                            }
                            is Resource.Failure -> {
                                it.throwable.printStackTrace()
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
        return kelompokTani

    }
}
package ps.bebyrong.ui.fragment.commodity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.model.response.ResponseFoodList
import ps.bebyrong.data.source.Resource
import ps.bebyrong.data.repo.RepoCommodity

/**
 **********************************************
 * Created by ukie on 4/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityViewModel(private val repoCommodity: RepoCommodity) : BaseViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isNoData = MutableLiveData<Boolean>()
    val isDisconnect = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    fun getListPangan(queryMap: LinkedHashMap<String, String>, isFirst: Boolean): LiveData<ResponseFoodList> {
        val listPangan = MutableLiveData<ResponseFoodList>()
        composite {
            repoCommodity.getListPangan(queryMap).subscribe({
                when (it) {
                    is Resource.Success -> {

                        listPangan.value = it.data
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
        return listPangan
    }
}
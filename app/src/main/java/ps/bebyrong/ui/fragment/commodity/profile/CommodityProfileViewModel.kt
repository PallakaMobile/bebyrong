package ps.bebyrong.ui.fragment.commodity.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ps.bebyrong.base.BaseViewModel
import ps.bebyrong.data.DataBroadcast
import ps.bebyrong.data.model.response.ResponseFormersProduct
import ps.bebyrong.data.model.response.ResponseProfileFormers
import ps.bebyrong.data.repo.RepoCommodity
import ps.bebyrong.data.source.Resource
import ps.bebyrong.utils.Hi

/**
 **********************************************
 * Created by ukie on 4/30/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityProfileViewModel(private val repoCommodity: RepoCommodity) : BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()
    val profileFormers = MutableLiveData<ResponseProfileFormers>()

    fun getProfileFormers(category: String, idPangan: String) {
        composite {
            repoCommodity.getProfileFormers(category, idPangan)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                profileFormers.value = it.data
                                Hi.broadcast(DataBroadcast(it.data?.data?.nama.toString(),
                                        it.data?.data?.textPanen.toString(), it.data?.data?.gambar.toString()))
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

    fun getProductFormers(idFormers: String): LiveData<ResponseFormersProduct> {
        val formersProduct = MutableLiveData<ResponseFormersProduct>()
        composite {
            repoCommodity.getFormersProduct(idFormers)
                    .subscribe({
                        when (it) {
                            is Resource.Success -> {
                                formersProduct.value = it.data
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
        return formersProduct
    }
}
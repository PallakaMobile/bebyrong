package ps.bebyrong.ui.fragment.commodity.info

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.data.model.response.ResponseListFormers
import ps.bebyrong.databinding.FragmentCommodityInfoBinding
import ps.bebyrong.utils.RxEditTextBinding
import java.util.concurrent.TimeUnit

/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityInfoFragment : BaseFragment<FragmentCommodityInfoBinding>() {
    private val viewModel by viewModel<CommodityInfoViewModel>()
    private val rxBinding by inject<RxEditTextBinding>()

    private var listFormers = mutableListOf<ResponseListFormers.DataItem>()
    private var currentPage = 1
    private var query = ""
    private var lastPage = 0
    private var isLoading = false

    private var idPangan: String? = ""
    private var category: String? = ""

    private lateinit var adapter: CommodityInfoAdapter

    companion object {

        private const val PARAM_CATEGORY = "category"
        private const val PARAM_IDPANGAN = "idPangan"
        fun newInstance(category: String, idPangan: String): CommodityInfoFragment {
            val mainFragment = CommodityInfoFragment()
            Bundle().apply {
                putString(PARAM_CATEGORY, category)
                putString(PARAM_IDPANGAN, idPangan)
                mainFragment.arguments = this
            }
            return mainFragment
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_commodity_info

    override fun myCodeHere() {
        val args = arguments
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel
        args?.apply {

            category = getString(PARAM_CATEGORY)
            idPangan = getString(PARAM_IDPANGAN)

            adapter = CommodityInfoAdapter()

            dataBinding.rvCommodityInfo.setHasFixedSize(true)
            dataBinding.rvCommodityInfo.layoutManager = LinearLayoutManager(activity)
            dataBinding.rvCommodityInfo.adapter = adapter

            //get first page
            getCommodityInfo("", true)

            dataBinding.swipeLayout.setOnRefreshListener {
                currentPage = 1
                getCommodityInfo("", true)
            }

            dataBinding.swipeLayout.setColorSchemeColors(
                    ContextCompat.getColor(activity, R.color.colorPrimary),
                    ContextCompat.getColor(activity, R.color.color2),
                    ContextCompat.getColor(activity, R.color.color3),
                    ContextCompat.getColor(activity, R.color.color4)
            )

            rxBinding.getTextWatcherObservable(dataBinding.etSearch)
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.single())
                    .subscribe {
                        query = it
                        currentPage = 1
                        getCommodityInfo(query, true)
                    }

            //pagination
            dataBinding.rvCommodityInfo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition
                    if (!isLoading && isLastPosition && currentPage < lastPage) {
                        showLoading()
                        currentPage = currentPage.plus(1)
                        getCommodityInfo(query, false)
                    }
                }
            })
        }
    }

    private fun getCommodityInfo(search: String, isFirst: Boolean) {
        viewModel.getListKelompokTani(linkedMapOf(
                "s" to search,
                "idPangan" to idPangan.toString(),
                "produksi" to category.toString(),
                "page" to currentPage.toString()
        ), isFirst)
                .observe(this, Observer {
                    lastPage = it.meta?.last_page ?: 0
                    if (currentPage == 1)
                        listFormers.clear()
                    listFormers.addAll(it.data ?: throw Exception("Error"))
                    adapter.updateNewsCategoryAdapter(listFormers, idPangan.toString())
                    hideLoading()
                    dataBinding.swipeLayout.isRefreshing = false
                })
    }

    private fun showLoading() {
        isLoading = true
        dataBinding.llLoadMore.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        isLoading = false
        dataBinding.llLoadMore.visibility = View.GONE
    }


}
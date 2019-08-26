package ps.bebyrong.ui.fragment.stock

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
import ps.bebyrong.data.model.response.ResponseStock
import ps.bebyrong.databinding.FragmentStockTypeBinding
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
class StockTypeFragment : BaseFragment<FragmentStockTypeBinding>() {
    private val viewModel by viewModel<StockTypeViewModel>()
    private val rxBinding by inject<RxEditTextBinding>()

    private var listStock = mutableListOf<ResponseStock.DataItem>()
    private var currentPage = 1
    private var query = ""
    private var lastPage = 0
    private var isLoading = false

    private lateinit var adapter: StockTypeAdapter

    companion object {

        private const val PARAM_CATEGORY = "category"
        fun newInstance(category: String): StockTypeFragment {
            val mainFragment = StockTypeFragment()
            Bundle().apply {
                putString(PARAM_CATEGORY, category)
                mainFragment.arguments = this
            }
            return mainFragment
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_stock_type

    override fun myCodeHere() {
        val args = arguments
        args?.apply {

            val category = getString(PARAM_CATEGORY)
            dataBinding.lifecycleOwner = this@StockTypeFragment
            dataBinding.viewModel = viewModel

            adapter = StockTypeAdapter()
            dataBinding.rvStock.setHasFixedSize(true)
            dataBinding.rvStock.layoutManager = LinearLayoutManager(activity)
            dataBinding.rvStock.adapter = adapter

            //get first page
            getStock(category, "", true)

            dataBinding.swipeLayout.setOnRefreshListener {
                currentPage = 1
                getStock(category, "", true)
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
                        getStock(category, query, true)
                    }

            //pagination
            dataBinding.rvStock.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition
                    if (!isLoading && isLastPosition && currentPage < lastPage) {
                        showLoading()
                        currentPage = currentPage.plus(1)
                        getStock(category, query, false)
                    }
                }
            })
        }
    }

    private fun getStock(category: String, search: String, isFirst: Boolean) {
        viewModel.getStock(if (category == getString(R.string.market_traditional)) "1" else "2", search, isFirst)
                .observe(this, Observer {
                    lastPage = it.meta?.last_page ?: 0
                    if (currentPage == 1)
                        listStock.clear()
                    listStock.addAll(it.data ?: throw Exception("Error"))
                    adapter.updateStockTypeAdapter(listStock)
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

    override fun onResume() {
        super.onResume()
        dataBinding.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        dataBinding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }
}

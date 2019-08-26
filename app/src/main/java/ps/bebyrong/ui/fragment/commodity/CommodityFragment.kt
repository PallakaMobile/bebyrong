package ps.bebyrong.ui.fragment.commodity

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
import ps.bebyrong.data.model.response.ResponseFoodList
import ps.bebyrong.databinding.FragmentCommodityBinding
import ps.bebyrong.utils.RxEditTextBinding
import java.util.concurrent.TimeUnit

/**
 **********************************************
 * Created by ukie on 4/14/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityFragment : BaseFragment<FragmentCommodityBinding>() {
    private val viewModel by viewModel<CommodityViewModel>()
    private val rxBinding by inject<RxEditTextBinding>()

    private var listCommodity = mutableListOf<ResponseFoodList.DataItem>()
    private var currentPage = 1
    private var query = ""
    private var lastPage = 0
    private var isLoading = false

    private lateinit var adapter: CommodityAdapter

    override fun getLayoutResource(): Int = R.layout.fragment_commodity

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        activity.supportActionBar?.title = getString(R.string.commodity)
        dataBinding.viewModel = viewModel

        adapter = CommodityAdapter()
        dataBinding.rvCommodity.setHasFixedSize(true)
        dataBinding.rvCommodity.layoutManager = LinearLayoutManager(activity)
        dataBinding.rvCommodity.adapter = adapter

        //get first page
        getCommodity("", true)
        dataBinding.swipeLayout.setOnRefreshListener {
            currentPage = 1
            getCommodity("", true)
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
                    getCommodity(query, true)
                }

        //pagination
        dataBinding.rvCommodity.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && currentPage < lastPage) {
                    showLoading()
                    currentPage = currentPage.plus(1)
                    getCommodity(query, false)
                }
            }
        })
    }


    private fun getCommodity(search: String, isFirst: Boolean) {
        viewModel.getListPangan(linkedMapOf(
                "s" to search,
                "page" to currentPage.toString()
        ), isFirst)
                .observe(this, Observer {
                    lastPage = it.meta?.last_page ?: 0
                    if (currentPage == 1)
                        listCommodity.clear()

                    listCommodity.addAll(it.data ?: throw Exception("Error"))
                    adapter.updateCommodityAdapter(listCommodity)
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
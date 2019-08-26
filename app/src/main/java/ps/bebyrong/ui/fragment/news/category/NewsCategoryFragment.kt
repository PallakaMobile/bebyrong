package ps.bebyrong.ui.fragment.news.category

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
import ps.bebyrong.data.model.response.ResponseNews
import ps.bebyrong.databinding.FragmentNewsCategoryBinding
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
class NewsCategoryFragment : BaseFragment<FragmentNewsCategoryBinding>() {

    private val viewModel by viewModel<NewsCategoryViewModel>()
    private val rxBinding by inject<RxEditTextBinding>()

    private var listNews = mutableListOf<ResponseNews.DataItem>()
    private var currentPage = 1
    private var query = ""
    private var lastPage = 0
    private var isLoading = false

    private lateinit var adapter: NewsCategoryAdapter

    companion object {
        private const val PARAM_ID = "id"
        fun newInstance(id: String): NewsCategoryFragment {
            val mainFragment = NewsCategoryFragment()
            Bundle().apply {
                putString(PARAM_ID, id)
                mainFragment.arguments = this
            }
            return mainFragment
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_news_category

    override fun myCodeHere() {
        val args = arguments
        dataBinding.lifecycleOwner = this
        args?.apply {
            dataBinding.lifecycleOwner = this@NewsCategoryFragment
            dataBinding.viewModel = viewModel

            val idCategory = getString(PARAM_ID)

            adapter = NewsCategoryAdapter()
            dataBinding.rvNews.setHasFixedSize(true)
            dataBinding.rvNews.layoutManager = LinearLayoutManager(activity)
            dataBinding.rvNews.adapter = adapter


            //get first page
            getNews(idCategory, "", true)

            dataBinding.swipeLayout.setOnRefreshListener {
                currentPage = 1
                getNews(idCategory, "", true)
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
                        getNews(idCategory, query, true)
                    }

            //pagination
            dataBinding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition
                    if (!isLoading && isLastPosition && currentPage < lastPage) {
                        showLoading()
                        currentPage = currentPage.plus(1)
                        getNews(idCategory, query, false)
                    }
                }
            })
        }
    }

    private fun getNews(idCategory: String, search: String, isFirst: Boolean) {
        viewModel.getNews(linkedMapOf(
                "kat" to if (idCategory == "Semua") "" else idCategory,
                "s" to search,
                "page" to currentPage.toString()
        ), isFirst)
                .observe(this, Observer {
                    lastPage = it.meta?.last_page ?: 0
                    if (currentPage == 1)
                        listNews.clear()
                    listNews.addAll(it.data ?: throw Exception("Error"))
                    adapter.updateNewsCategoryAdapter(listNews)
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
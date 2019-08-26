package ps.bebyrong.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.data.model.response.ResponseFoodList
import ps.bebyrong.data.model.response.ResponseNews
import ps.bebyrong.data.model.response.ResponsePrice
import ps.bebyrong.data.model.response.ResponseStock
import ps.bebyrong.databinding.FragmentHomeBinding
import ps.bebyrong.ui.fragment.commodity.CommodityViewModel
import ps.bebyrong.ui.fragment.news.category.NewsCategoryViewModel
import ps.bebyrong.ui.fragment.price.PriceViewModel
import ps.bebyrong.ui.fragment.stock.StockTypeViewModel


/**
 **********************************************
 * Created by ukie on 4/14/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val newsViewModel by sharedViewModel<NewsCategoryViewModel>()
    private val commodityViewModel by sharedViewModel<CommodityViewModel>()
    private val stockViewModel by sharedViewModel<StockTypeViewModel>()
    private val priceViewModel by sharedViewModel<PriceViewModel>()

    val isLoading = MutableLiveData<Boolean>()
    val isContent = MutableLiveData<Boolean>()

    override fun getLayoutResource(): Int = R.layout.fragment_home

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        activity.supportActionBar?.title = getString(R.string.home)
        dataBinding.homeFragment = this

        //set loading first load
        isLoading.value = true


        val homeList = listOf(HomeList(getString(R.string.news), 5),
                HomeList(getString(R.string.commodity), 5),
                HomeList(getString(R.string.stock), 5),
                HomeList(getString(R.string.price), 5))

        dataBinding.rvHome.setHasFixedSize(true)
        dataBinding.rvHome.layoutManager = LinearLayoutManager(activity)
        val homeAdapter = HomeAdapter()

        val listNews = mutableListOf<ResponseNews.DataItem>()
        val listPangan = mutableListOf<ResponseFoodList.DataItem>()
        val listPrice = mutableListOf<ResponsePrice.DataItem>()
        val listStockType = mutableListOf<ResponseStock.DataItem>()

        newsViewModel.getNews(linkedMapOf(
                "kat" to "",
                "s" to "",
                "page" to "1"
        ), true).observe(this, Observer {
            listNews.addAll(it.data ?: throw Exception("Null"))

            val listImage = mutableListOf<String>()
            val listTitle = mutableListOf<String>()
            val listMessage = mutableListOf<String>()

            try {
                for (pos in 0..2) {
                    listImage.add(it.data[pos].gambar.toString())
                    listTitle.add(it.data[pos].judul.toString())
                    listMessage.add(it.data[pos].kategori.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val homeSliderAdapter = HomeSliderAdapter(
                    childFragmentManager, activity
                    , listImage, listTitle, listMessage
            )
            homeSliderAdapter.showIndicator(dataBinding.llIndicator, dataBinding.vpImageSlider)
            dataBinding.vpImageSlider.adapter = homeSliderAdapter

            //get commodity
            commodityViewModel.getListPangan(linkedMapOf(
                    "s" to "",
                    "page" to "1"
            ), true).observe(this, Observer { commodity ->
                listPangan.addAll(commodity.data ?: throw Exception("Null"))

                //get stock
                stockViewModel.getStock("1", "", true).observe(this, Observer { price ->
                    listStockType.addAll(price.data ?: throw Exception("Null"))

                    //get price
                    priceViewModel.getPrice(linkedMapOf(
                            "s" to "",
                            "page" to "1"
                    ), true).observe(this, Observer { price ->
                        listPrice.addAll(price.data ?: throw Exception("Null"))
                        homeAdapter.updateHomeAdapter(homeList, listNews,
                                listPangan,
                                listStockType,
                                listPrice)

                        isLoading.value = false
                        isContent.value = true
                    })
                })

            })
        })

        dataBinding.rvHome.adapter = homeAdapter
    }


    override fun onResume() {
        super.onResume()
        dataBinding.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        dataBinding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }

    data class HomeList(val title: String, val sizeContent: Int)
}
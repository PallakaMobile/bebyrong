package ps.bebyrong.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseFoodList
import ps.bebyrong.data.model.response.ResponseNews
import ps.bebyrong.data.model.response.ResponsePrice
import ps.bebyrong.data.model.response.ResponseStock
import ps.bebyrong.databinding.FragmentHomeItemBinding
import ps.bebyrong.ui.fragment.home.menulist.HomeCommodityAdapter
import ps.bebyrong.ui.fragment.home.menulist.HomeNewsAdapter
import ps.bebyrong.ui.fragment.home.menulist.HomePriceAdapter
import ps.bebyrong.ui.fragment.home.menulist.HomeStockAdapter

/**
 **********************************************
 * Created by ukie on 4/16/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    private lateinit var dataList: List<HomeFragment.HomeList>
    private lateinit var dataListNews: List<ResponseNews.DataItem>
    private lateinit var dataFoodList: List<ResponseFoodList.DataItem>
    private lateinit var dataListStock: List<ResponseStock.DataItem>
    private lateinit var dataListPrice: List<ResponsePrice.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder =
            HomeHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_home_item, parent, false))

    override fun onBindViewHolder(holder: HomeHolder, position: Int) = holder.bind(dataList[position],
            dataListNews,
            dataFoodList,
            dataListStock,
            dataListPrice)

    override fun getItemCount() = if (::dataList.isInitialized) dataList.size else 0

    fun updateHomeAdapter(dataList: List<HomeFragment.HomeList>,
                          dataListNews: List<ResponseNews.DataItem>?,
                          dataFoodList: List<ResponseFoodList.DataItem>?,
                          dataListStock: List<ResponseStock.DataItem>?,
                          dataListPrice: List<ResponsePrice.DataItem>?) {
        this.dataList = dataList
        this.dataListNews = dataListNews ?: throw Exception("Null")
        this.dataFoodList = dataFoodList ?: throw Exception("Null")
        this.dataListStock = dataListStock ?: throw Exception("Null")
        this.dataListPrice = dataListPrice ?: throw Exception("Null")
        notifyDataSetChanged()
    }

    inner class HomeHolder(private val binding: FragmentHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeFragment.HomeList,
                 dataListNews: List<ResponseNews.DataItem>,
                 dataFoodList: List<ResponseFoodList.DataItem>,
                 dataListStock: List<ResponseStock.DataItem>,
                 dataListPrice: List<ResponsePrice.DataItem>) = with(itemView) {
            // TODO: Bind data dengan View
            binding.tvTitle.text = data.title

            binding.tvShowAll.setOnClickListener {
                when (data.title) {
                    it.context.getString(R.string.news) -> {
                        findNavController().navigate(R.id.news_dest)
                    }
                    it.context.getString(R.string.commodity) -> {
                        findNavController().navigate(R.id.commodity_dest)
                    }
                    it.context.getString(R.string.price) -> {
                        findNavController().navigate(R.id.price_dest)
                    }
                    it.context.getString(R.string.stock) -> {
                        findNavController().navigate(R.id.stock_dest)
                    }
                }
            }

            when (data.title) {
                itemView.context.getString(R.string.news) -> {
                    val adapter = HomeNewsAdapter()
                    adapter.updateNewsCategoryAdapter(dataListNews)
                    binding.rvContent.adapter = adapter
                }
                itemView.context.getString(R.string.commodity) -> {
                    val adapter = HomeCommodityAdapter()
                    adapter.updateHomeCommodityAdapter(dataFoodList)
                    binding.rvContent.adapter = adapter
                }
                itemView.context.getString(R.string.stock) -> {
                    val adapter = HomeStockAdapter()
                    adapter.updateStockTypeAdapter(dataListStock)
                    binding.rvContent.adapter = adapter
                }

                itemView.context.getString(R.string.price) -> {
                    val adapter = HomePriceAdapter()
                    adapter.updatePriceAdapter(dataListPrice)
                    binding.rvContent.adapter = adapter
                }
            }

            binding.rvContent.setHasFixedSize(true)
            binding.rvContent.layoutManager = LinearLayoutManager(itemView.context, LinearLayout.HORIZONTAL, false)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvContent)

            setOnClickListener {
                // TODO: Action ketika item di klik
            }
        }
    }

    data class DataListContent(val title: String)
}
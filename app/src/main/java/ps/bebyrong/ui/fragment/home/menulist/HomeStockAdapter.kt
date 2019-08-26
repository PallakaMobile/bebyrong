package ps.bebyrong.ui.fragment.home.menulist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseStock
import ps.bebyrong.databinding.FragmentHomeStockTypeItemBinding
import ps.bebyrong.utils.GlideApp
import androidx.core.util.Pair as UtilPair


/**
 **********************************************
 * Created by ukie on 4/17/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class HomeStockAdapter : RecyclerView.Adapter<HomeStockAdapter.StockTypeHolder>() {
    private lateinit var dataStockList: List<ResponseStock.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockTypeHolder =
            StockTypeHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_home_stock_type_item, parent, false))

    override fun onBindViewHolder(holder: StockTypeHolder, position: Int) = holder.bindDataCategory(dataStockList[position])

    override fun getItemCount() = if (::dataStockList.isInitialized) dataStockList.size else 0

    fun updateStockTypeAdapter(dataStockList: List<ResponseStock.DataItem>) {
        this.dataStockList = dataStockList
        notifyDataSetChanged()
    }

    inner class StockTypeHolder(private val binding: FragmentHomeStockTypeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindDataCategory(dataStock: ResponseStock.DataItem) = with(itemView) {
            // TODO: Bind data dengan View
            binding.tvTitle.text = dataStock.nama
            binding.tvAddress.text = dataStock.alamat

            GlideApp.with(itemView.context)
                    .load(dataStock.gambar)
                    .centerCrop()
                    .into(binding.ivContent)
            binding.btnStockInfo.setOnClickListener {
                Bundle().apply {
                    putString("idMarket", dataStock.id.toString())
                    findNavController().navigate(R.id.act_stock_market_detail_dest, this)
                }
            }

            setOnClickListener {
            }
        }
    }
}
package ps.bebyrong.ui.fragment.commodity.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseFormersProduct
import ps.bebyrong.databinding.FragmentCommodityProfileItemBinding

/**
 **********************************************
 * Created by ukie on 5/2/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityProfileAdapter : RecyclerView.Adapter<CommodityProfileAdapter.CommodityProfileHolder>() {
    private lateinit var dataResponseFormersProduct: List<ResponseFormersProduct.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommodityProfileHolder =
            CommodityProfileHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_commodity_profile_item, parent, false))

    override fun onBindViewHolder(holder: CommodityProfileHolder, position: Int) = holder.bindResponseFormersProduct(dataResponseFormersProduct[position])

    override fun getItemCount() = if (::dataResponseFormersProduct.isInitialized) dataResponseFormersProduct.size else 0

    fun updateCommodityProfileAdapter(dataResponseFormersProduct: List<ResponseFormersProduct.DataItem>) {
        this.dataResponseFormersProduct = dataResponseFormersProduct
        notifyDataSetChanged()
    }

    inner class CommodityProfileHolder(private val binding: FragmentCommodityProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindResponseFormersProduct(dataResponseFormersProduct: ResponseFormersProduct.DataItem) = with(itemView) {
            binding.data = dataResponseFormersProduct
            // TODO: Bind data dengan View
            setOnClickListener {
                // TODO: Action ketika item di klik
            }
        }
    }
}
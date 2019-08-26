package ps.bebyrong.ui.fragment.commodity.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseListFormers
import ps.bebyrong.databinding.FragmentCommodityInfoItemBinding
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
class CommodityInfoAdapter : RecyclerView.Adapter<CommodityInfoAdapter.CommodityInfoHolder>() {
    private lateinit var dataKelompokTaniList: List<ResponseListFormers.DataItem>
    private lateinit var idPangan: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommodityInfoHolder =
            CommodityInfoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_commodity_info_item, parent, false))

    override fun onBindViewHolder(holder: CommodityInfoHolder, position: Int) = holder.bindDataCategory(dataKelompokTaniList[position], idPangan)

    override fun getItemCount() = if (::dataKelompokTaniList.isInitialized) dataKelompokTaniList.size else 0

    fun updateNewsCategoryAdapter(dataKelompokTaniList: List<ResponseListFormers.DataItem>, idPangan: String) {
        this.dataKelompokTaniList = dataKelompokTaniList
        this.idPangan = idPangan
        notifyDataSetChanged()
    }

    inner class CommodityInfoHolder(private val binding: FragmentCommodityInfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindDataCategory(dataFormers: ResponseListFormers.DataItem, idPangan: String) = with(itemView) {
            // TODO: Bind data dengan View
            binding.tvTitle.text = dataFormers.nama
            binding.tvStock.text = "Stok ${dataFormers.stok}"
            binding.tvDesc.text = dataFormers.tanggalPanen

            GlideApp.with(itemView.context)
                    .load(dataFormers.gambar)
                    .centerCrop()
                    .into(binding.ivContent)

            binding.btnDetail.setOnClickListener {
                Bundle().apply {
                    putString("id", dataFormers.id)
                    putString("idPangan", idPangan)
                    findNavController().navigate(R.id.act_commodity_profile, this)
                }
            }

            setOnClickListener {
            }
        }
    }
}
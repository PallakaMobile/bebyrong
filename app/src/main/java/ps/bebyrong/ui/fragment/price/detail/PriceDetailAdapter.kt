package ps.bebyrong.ui.fragment.price.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.DataBroadcast
import ps.bebyrong.data.model.response.ResponsePriceDetail
import ps.bebyrong.databinding.FragmentPriceDetailItemBinding
import ps.bebyrong.utils.GlideApp
import ps.bebyrong.utils.Hi

/**
 **********************************************
 * Created by ukie on 4/28/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class PriceDetailAdapter : RecyclerView.Adapter<PriceDetailAdapter.PriceDetailHolder>() {
    private lateinit var dataPriceDetail: ResponsePriceDetail.Data
    private lateinit var dataPriceDetailList: List<ResponsePriceDetail.ListPasarItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceDetailHolder =
            PriceDetailHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_price_detail_item, parent, false))

    override fun onBindViewHolder(holder: PriceDetailHolder, position: Int) = holder.bindDataPriceDetail(dataPriceDetailList[position])

    override fun getItemCount() = if (::dataPriceDetailList.isInitialized) dataPriceDetailList.size else 0

    fun updatePriceDetailAdapter(
            dataPriceDetail: ResponsePriceDetail.Data,
            dataPriceDetailList: List<ResponsePriceDetail.ListPasarItem>
    ) {
        this.dataPriceDetailList = dataPriceDetailList
        this.dataPriceDetail = dataPriceDetail
        notifyDataSetChanged()
    }

    inner class PriceDetailHolder(private val binding: FragmentPriceDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindDataPriceDetail(dataPriceDetailList: ResponsePriceDetail.ListPasarItem) = with(itemView) {
            // TODO: Bind data dengan View
            binding.tvTitle.text = dataPriceDetailList.nama
            binding.tvAddress.text = dataPriceDetailList.alamat
            binding.tvPrice.text = dataPriceDetailList.harga

            binding.btnLocation.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:0,0?q=${dataPriceDetailList.kordinat}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                itemView.context.startActivity(mapIntent)
            }

            binding.btnStock.setOnClickListener {
                Bundle().apply {
                    putString("idMarket",dataPriceDetailList.id.toString())
                    findNavController().navigate(R.id.act_stock_market_detail_dest,this)
                }
            }

            GlideApp.with(itemView.context)
                    .load(dataPriceDetailList.foto)
                    .thumbnail(0.5F)
                    .centerCrop()
                    .override(120, 120)
                    .into(binding.ivContent)

            Hi.broadcast(DataBroadcast(
                    dataPriceDetail.nama.toString(),
                    dataPriceDetail.harga.toString(),
                    dataPriceDetail.gambar.toString()
            ))
        }
    }
}
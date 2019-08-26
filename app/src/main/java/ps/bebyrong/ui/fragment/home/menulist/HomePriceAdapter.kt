package ps.bebyrong.ui.fragment.home.menulist

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponsePrice
import ps.bebyrong.databinding.FragmentHomePriceItemBinding
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
class HomePriceAdapter : RecyclerView.Adapter<HomePriceAdapter.PriceHolder>() {
    private lateinit var dataPriceList: List<ResponsePrice.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHolder =
            PriceHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_home_price_item, parent, false))

    override fun onBindViewHolder(holder: PriceHolder, position: Int) = holder.bindDataPrice(dataPriceList[position])

    override fun getItemCount() = if (::dataPriceList.isInitialized) dataPriceList.size else 0

    fun updatePriceAdapter(dataPriceList: List<ResponsePrice.DataItem>) {
        this.dataPriceList = dataPriceList
        notifyDataSetChanged()
    }

    inner class PriceHolder(private val binding: FragmentHomePriceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindDataPrice(dataPrice: ResponsePrice.DataItem) = with(itemView) {
            binding.tvTitle.text = dataPrice.nama
            binding.tvPrice.text = dataPrice.harga

            GlideApp.with(itemView.context)
                    .load(dataPrice.gambar)
                    .centerCrop()
                    .into(binding.ivContent)

            val layoutParams = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            layoutParams.topMargin = resources.displayMetrics.density.times(10).toInt()
            layoutParams.rightMargin = resources.displayMetrics.density.times(5).toInt()

            binding.llMarket.removeAllViews()
            for (loops in dataPrice.pasar?.indices ?: throw NullPointerException()) {
                val tv = AppCompatTextView(itemView.context)
                tv.text = dataPrice.pasar.get(0).nama.toString()
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
                tv.layoutParams = layoutParams
                tv.gravity = Gravity.CENTER
                tv.maxLines = 1
                tv.setPadding(30, 10, 30, 10)
                tv.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                tv.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_rounded_green)
                binding.llMarket.addView(tv)

                //limit 2 label
                if (loops == 1)
                        break
            }
            setOnClickListener {
                Bundle().apply {
                    putString("idPrice", dataPrice.id.toString())
                    findNavController().navigate(R.id.act_price_detail_dest, this)
                }
            }
        }
    }
}
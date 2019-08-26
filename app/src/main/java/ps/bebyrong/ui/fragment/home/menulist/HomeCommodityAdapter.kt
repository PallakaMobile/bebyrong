package ps.bebyrong.ui.fragment.home.menulist

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseFoodList
import ps.bebyrong.databinding.FragmentHomeCommodityItemBinding
import ps.bebyrong.utils.GlideApp

/**
 **********************************************
 * Created by ukie on 4/20/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class HomeCommodityAdapter : RecyclerView.Adapter<HomeCommodityAdapter.HomeCommodityHolder>() {
    private lateinit var dataDataCommodityFoodList: List<ResponseFoodList.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCommodityHolder =
            HomeCommodityHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_home_commodity_item, parent, false))

    override fun onBindViewHolder(holderHome: HomeCommodityHolder, position: Int) = holderHome.bindDataCommodity(dataDataCommodityFoodList[position])

    override fun getItemCount() = if (::dataDataCommodityFoodList.isInitialized) dataDataCommodityFoodList.size else 0

    fun updateHomeCommodityAdapter(dataDataCommodityFoodList: List<ResponseFoodList.DataItem>) {
        this.dataDataCommodityFoodList = dataDataCommodityFoodList
        notifyDataSetChanged()
    }

    inner class HomeCommodityHolder(private val binding: FragmentHomeCommodityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindDataCommodity(dataDataCommodity: ResponseFoodList.DataItem) = with(itemView) {
            binding.tvTitle.text = dataDataCommodity.nama
            binding.tvSubTitle.text = "${dataDataCommodity.dataKelompoktani?.size} Kelompok Petani"
            GlideApp.with(itemView.context)
                    .load(dataDataCommodity.gambar)
                    .centerCrop()
                    .into(binding.ivContent)

            val layoutParams = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            layoutParams.topMargin = resources.displayMetrics.density.times(10).toInt()
            layoutParams.rightMargin = resources.displayMetrics.density.times(5).toInt()
            binding.llLogo.removeAllViews() // refresh view
            if (dataDataCommodity.dataKelompoktani != null)
                for (item in dataDataCommodity.dataKelompoktani.indices) {
                    if (item <= 2) {
                        var img = AppCompatImageView(itemView.context)
                        if (item != 0)
                            layoutParams.leftMargin = resources.displayMetrics.density.times(5).toInt()
                        img.layoutParams = layoutParams

                        GlideApp.with(itemView.context)
                                .load(dataDataCommodity.dataKelompoktani[item].gambar)
                                .circleCrop()
                                .override(100, 100)
                                .into(img)

                        img.layoutParams.width = 100
                        img.layoutParams.height = 100
                        binding.llLogo.addView(img)
                    } else {
                        val tv = AppCompatTextView(itemView.context)
                        layoutParams.leftMargin = resources.displayMetrics.density.times(5).toInt()
                        tv.text = "+${dataDataCommodity.dataKelompoktani.size - item}"
                        tv.layoutParams = layoutParams
                        tv.setPadding(17, 15, 17, 15)
                        tv.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_oval)
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                        binding.llLogo.addView(tv)
                        break
                    }
                }
            // TODO: Bind data dengan View
            setOnClickListener {
                Bundle().apply {
                    putString("idPangan", dataDataCommodity.id)
                    putString("title", dataDataCommodity.nama)
                    findNavController().navigate(R.id.act_commodity_tab, this)
                }
            }
        }
    }
}
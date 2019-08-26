package ps.bebyrong.ui.fragment.home.menulist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseNews
import ps.bebyrong.databinding.FragmentHomeNewsItemBinding


/**
 **********************************************
 * Created by ukie on 4/17/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class HomeNewsAdapter : RecyclerView.Adapter<HomeNewsAdapter.NewsCategoryHolder>() {
    private lateinit var dataDataCategoryList: List<ResponseNews.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryHolder =
            NewsCategoryHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_home_news_item, parent, false))

    override fun onBindViewHolder(holder: NewsCategoryHolder, position: Int) = holder.bindDataCategory(dataDataCategoryList[position])

    override fun getItemCount() = if (::dataDataCategoryList.isInitialized) dataDataCategoryList.size else 0

    fun updateNewsCategoryAdapter(dataDataCategoryList: List<ResponseNews.DataItem>) {
        this.dataDataCategoryList = dataDataCategoryList
        notifyDataSetChanged()
    }

    inner class NewsCategoryHolder(private val binding: FragmentHomeNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindDataCategory(dataCategory: ResponseNews.DataItem) = with(itemView) {
            // TODO: Bind data dengan View
            binding.dataCategory = dataCategory

            setOnClickListener {
                val args = Bundle()
                args.putString("idBerita", dataCategory.id)
                findNavController().navigate(R.id.act_news_detail, args)

            }
        }
    }
}
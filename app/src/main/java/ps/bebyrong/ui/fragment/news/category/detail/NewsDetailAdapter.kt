package ps.bebyrong.ui.fragment.news.category.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.e
import ps.bebyrong.R
import ps.bebyrong.data.model.response.ResponseNewsComment
import ps.bebyrong.databinding.FragmentNewsDetailItemBinding

/**
 **********************************************
 * Created by ukie on 09/05/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class NewsDetailAdapter : RecyclerView.Adapter<NewsDetailAdapter.NewsDetailHolder>() {
    private lateinit var dataResponseNewsCommentList: List<ResponseNewsComment.DataItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDetailHolder =
            NewsDetailHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_news_detail_item, parent, false))

    override fun onBindViewHolder(holder: NewsDetailHolder, position: Int) = holder.bindResponseNewsComment(dataResponseNewsCommentList[position])

    override fun getItemCount(): Int {
        e { "${if (::dataResponseNewsCommentList.isInitialized) dataResponseNewsCommentList.size else 0}" }

        return if (::dataResponseNewsCommentList.isInitialized) dataResponseNewsCommentList.size else 0
    }

    fun updateNewsDetailAdapter(dataResponseNewsCommentList: List<ResponseNewsComment.DataItem>) {
        this.dataResponseNewsCommentList = dataResponseNewsCommentList
        notifyDataSetChanged()
    }

    inner class NewsDetailHolder(private val binding: FragmentNewsDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindResponseNewsComment(dataResponseNewsComment: ResponseNewsComment.DataItem) = with(itemView) {
            binding.item = dataResponseNewsComment
            // TODO: Bind data dengan View
            setOnClickListener {
                // TODO: Action ketika item di klik
            }
        }
    }
}
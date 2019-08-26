package ps.bebyrong.utils

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import ps.bebyrong.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * *********************************************
 * Created by ukie on 9/27/18 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 * *********************************************
 * © 2018 | All Right Reserved
 */

@BindingAdapter("app:loadingVisibility")
fun loadingVisibility(view: ShimmerFrameLayout?, isVisible: Boolean?) {
    if (isVisible != null)
        if (isVisible) {
            view?.visibility = View.VISIBLE
            view?.startShimmer()
        } else {
            view?.stopShimmer()
            view?.visibility = View.GONE
        }
}

@BindingAdapter("app:contentVisibility")
fun contentVisibility(view: View?, isVisible: Boolean?) {
//    Logger.d("content ${isVisible.toString()}")
    if (isVisible != null)
        if (isVisible)
            view?.visibility = View.VISIBLE
        else
            view?.visibility = View.GONE
}

@BindingAdapter("app:noDataVisibility")
fun noDataVisibility(view: View?, isVisible: Boolean?) {
    if (isVisible != null)
        if (isVisible)
            view?.visibility = View.VISIBLE
        else
            view?.visibility = View.GONE
}


@BindingAdapter("app:disconnect")
fun disconnect(view: View?, isVisible: Boolean?) {
//    Logger.d("disconnect ${isVisible.toString()}")
    if (isVisible != null)
        if (isVisible)
            view?.visibility = View.VISIBLE
        else
            view?.visibility = View.GONE
}

@Suppress("DEPRECATION")
@BindingAdapter("app:htmlToText")
fun HtmlToText(view: AppCompatTextView?, text: String?) {
    if (text != null)
        view?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else
            Html.fromHtml(text)
}

@BindingAdapter("app:isEnable")
fun setEnable(view: View?, isEnable: Boolean?) {
    if (isEnable != null)
        view?.isEnabled = !isEnable

}

@BindingAdapter("app:rvAdapter")
fun mutableRvAdapter(view: RecyclerView?, adapter: RecyclerView.Adapter<*>) {
    view?.adapter = adapter
}

@BindingAdapter("app:imageGrid")
fun imageGrid(view: ImageView?, url: String?) {
    if (url != null)
        GlideApp.with(view?.context ?: throw NullPointerException())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(view)
}


@BindingAdapter("app:imageRound")
fun imageCircle(view: ImageView?, url: String?) {
    if (url != null)
        GlideApp.with(view?.context ?: throw NullPointerException())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .circleCrop()
                .into(view)
}

@BindingAdapter("app:time", "app:date")
fun convertTime(view: TextView?, time: String?, tanggal: String?) {
    if (time != null && tanggal != null) {
        val clock24 = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
        clock24.timeZone = TimeZone.getTimeZone("GMT+7")
        val date = clock24.parse(time)
        view?.text = "$tanggal ${SimpleDateFormat("K:mm a").format(date)}"
    }
}

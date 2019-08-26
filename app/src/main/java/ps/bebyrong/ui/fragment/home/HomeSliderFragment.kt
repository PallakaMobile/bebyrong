package ps.bebyrong.ui.fragment.home

import android.os.Bundle
import android.widget.Toast
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentHomeSliderBinding
import ps.bebyrong.databinding.FragmentOnboardingBinding
import ps.bebyrong.utils.GlideApp

/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class HomeSliderFragment : BaseFragment<FragmentHomeSliderBinding>() {

    companion object {
        private const val PARAM_IMAGE = "image"
        private const val PARAM_TITLE = "title"
        private const val PARAM_MESSAGE = "message"

        fun newInstance(image: String, title: String, message: String): HomeSliderFragment {
            val mainFragment = HomeSliderFragment()
            val args = Bundle()
            args.putString(PARAM_IMAGE, image)
            args.putString(PARAM_TITLE, title)
            args.putString(PARAM_MESSAGE, message)
            mainFragment.arguments = args
            return mainFragment
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_home_slider

    override fun myCodeHere() {
        val args = arguments
        args?.apply {

            val image = getString(PARAM_IMAGE)
            val title = getString(PARAM_TITLE)
            val message = getString(PARAM_MESSAGE)

            GlideApp.with(activity)
                    .load(image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(dataBinding.ivBg)

            dataBinding.tvTitle.text = title
            dataBinding.tvMessage.text = message

            view?.setOnClickListener {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}
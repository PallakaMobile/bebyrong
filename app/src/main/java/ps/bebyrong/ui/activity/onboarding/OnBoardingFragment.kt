package bct.id.horekamobile.ui.activity.onboarding

import android.os.Bundle
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentOnboardingBinding

/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    companion object {
        private const val PARAM_IMAGE = "image"
        private const val PARAM_TITLE = "title"
        private const val PARAM_MESSAGE = "message"

        fun newInstance(image: Int, title: String, message: String): OnBoardingFragment {
            val mainFragment = OnBoardingFragment()
            val args = Bundle()
            args.putInt(PARAM_IMAGE, image)
            args.putString(PARAM_TITLE, title)
            args.putString(PARAM_MESSAGE, message)
            mainFragment.arguments = args
            return mainFragment
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_onboarding

    override fun myCodeHere() {
        val args = arguments
        args?.apply {

            val image = getInt(PARAM_IMAGE)
            val title = getString(PARAM_TITLE)
            val message = getString(PARAM_MESSAGE)

            dataBinding.sectionImage.setImageResource(image)
            dataBinding.sectionLabel.text = title
            dataBinding.sectionDesc.text = message
        }

    }


}
package ps.bebyrong.ui.fragment.home.about

import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentAboutBinding

/**
 **********************************************
 * Created by ukie on 5/2/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class FragmentAbout : BaseFragment<FragmentAboutBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_about

    override fun myCodeHere() {
        activity.supportActionBar?.title = getString(R.string.about)
    }

}
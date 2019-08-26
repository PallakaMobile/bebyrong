package ps.bebyrong.ui.fragment.commodity.info

import com.github.ajalt.timberkt.e
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentCommodityTabBinding
import ps.bebyrong.utils.ViewPagerAdapter

/**
 **********************************************
 * Created by ukie on 4/14/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class CommodityInfoTab : BaseFragment<FragmentCommodityTabBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_commodity_tab

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        e { activity.supportActionBar?.title.toString() }
        e { arguments?.getString("idPangan").toString() }
        activity.supportActionBar?.title = arguments?.getString("title")

        //add tabs
        val listMenu = listOf(
                getString(R.string.harvest_schedule),
                getString(R.string.production))

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        for (item in listMenu) {
            val code = if(item == getString(R.string.harvest_schedule)) "0" else "1"
            dataBinding.tlCommodity.addTab(dataBinding.tlCommodity.newTab())
            viewPagerAdapter.addFragment(CommodityInfoFragment.newInstance(code, arguments?.getString("idPangan").toString()), item)
        }
        dataBinding.vpCommodity.adapter = viewPagerAdapter
        dataBinding.tlCommodity.setupWithViewPager(dataBinding.vpCommodity)
    }

}
package ps.bebyrong.ui.fragment.stock

import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentStockBinding
import ps.bebyrong.utils.ViewPagerAdapter

/**
 **********************************************
 * Created by ukie on 4/14/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class StockFragment : BaseFragment<FragmentStockBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_stock

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        activity.supportActionBar?.title = getString(R.string.stock)

        //add tabs
        val listMenu = listOf(
                getString(R.string.market_traditional),
                getString(R.string.market_modern))

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        for (item in listMenu) {
            dataBinding.tlStock.addTab(dataBinding.tlStock.newTab())
            viewPagerAdapter.addFragment(StockTypeFragment.newInstance(item), item)
        }
        dataBinding.vpStock.adapter = viewPagerAdapter
        dataBinding.tlStock.setupWithViewPager(dataBinding.vpStock)
    }

}
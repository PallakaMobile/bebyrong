package ps.bebyrong.ui.fragment.news

import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentNewsBinding
import ps.bebyrong.ui.fragment.news.category.NewsCategoryFragment
import ps.bebyrong.utils.ViewPagerAdapter

/**
 **********************************************
 * Created by ukie on 4/14/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    private val viewModel by viewModel<NewsViewModel>()
    override fun getLayoutResource(): Int = R.layout.fragment_news

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        activity.supportActionBar?.title = getString(R.string.news)

        val listMenuID = linkedMapOf<String, String>()
        viewModel.getNewsCategory().observe(this, Observer {
            listMenuID[""] = getString(R.string.all)
            for (loops in it.data?.indices ?: throw Exception("")) {
                listMenuID[it.data[loops]?.id.toString()] = it.data[loops]?.nama.toString()
            }

            val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)

            listMenuID.entries.forEach { linked ->
                dataBinding.tlNews.addTab(dataBinding.tlNews.newTab())
                viewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(linked.key), linked.value)
            }
            dataBinding.vpNews.adapter = viewPagerAdapter
            dataBinding.tlNews.setupWithViewPager(dataBinding.vpNews)

        })
    }
}
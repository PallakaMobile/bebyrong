package ps.bebyrong.ui.fragment.home

import android.content.Context
import android.os.Handler
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import ps.bebyrong.R

/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

class HomeSliderAdapter(
        fragmentManager: FragmentManager,
        private val context: Context,
        private val listImage: List<String>,
        private val listTitle: List<String>,
        private val listMessage: List<String>
) : FragmentStatePagerAdapter(fragmentManager), ViewPager.OnPageChangeListener {

    override fun getItem(position: Int): Fragment =
            HomeSliderFragment.newInstance(listImage[position], listTitle[position], listMessage[position])

    override fun getCount(): Int = listImage.size

    private var indexPage = 0

    private lateinit var linearLayout: LinearLayoutCompat
    private lateinit var viewPager: ViewPager

    fun showIndicator(linearLayout: LinearLayoutCompat, viewPager: ViewPager) {
        this.linearLayout = linearLayout
        this.viewPager = viewPager

        val res = context.resources
        for (i in 0 until listImage.size) {
            val view = View(context)
            val dimen: Int = res.displayMetrics.density.times(10).toInt()
            val layoutParams = LinearLayoutCompat.LayoutParams(dimen, dimen)
            layoutParams.leftMargin = res.displayMetrics.density.times(6).toInt()
            layoutParams.rightMargin = res.displayMetrics.density.times(6).toInt()
            view.layoutParams = layoutParams
            view.setBackgroundResource(R.drawable.ic_indicator)
            view.isSelected = i == 0
            this.linearLayout.addView(view)
        }
        viewPager.addOnPageChangeListener(this)
        setSelectedIndicator()

        autoSlideViewPager()
    }

    private fun autoSlideViewPager() {
        val handler = Handler()
//        var arrow = 0
        val runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 3000)

                /**
                 * loop left to right if last index to first index
                 */
                if (indexPage < listImage.size)
                    viewPager.setCurrentItem(indexPage++, true)
                else
                    viewPager.setCurrentItem(0, true)

                /**
                 * loop left to right if last index right to left
                 */
                /* if (indexPage <= listImage.size) {

                    if (arrow == 0)
                        arrow = 1

                    if (arrow == 1) {
                        viewPager.setCurrentItem(indexPage, true)
                        indexPage++
                    }

                    if (indexPage == listImage.size) {
                        arrow = 2
                        indexPage--
                    }

                    if (arrow == 2) {
                        viewPager.setCurrentItem(indexPage, true)
                        indexPage--

                        if (indexPage < 0)
                            arrow = 1
                    }
                }*/
            }
        }
        handler.post(runnable)
    }

    private fun setSelectedIndicator() {
        for (i in 0 until listImage.size) {
            val view = linearLayout.getChildAt(i)
            view.isSelected = i == indexPage
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        indexPage = position
        setSelectedIndicator()
    }
}
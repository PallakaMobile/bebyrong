package bct.id.horekamobile.ui.activity.onboarding

import android.content.Context
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

class OnBoardingAdapter(
        fragmentManager: FragmentManager,
        private val context: Context,
        private val listImage: Array<Int>,
        private val listTitle: Array<String>,
        private val listMessage: Array<String>
) : FragmentStatePagerAdapter(fragmentManager), ViewPager.OnPageChangeListener {

    var indexPage = 0
    override fun getItem(position: Int): Fragment =
            OnBoardingFragment.newInstance(listImage[position], listTitle[position], listMessage[position])

    override fun getCount(): Int = listImage.size


    private lateinit var linearLayout: LinearLayoutCompat
    private lateinit var viewPager: ViewPager

    fun showIndicator(linearLayout: LinearLayoutCompat, viewPager: ViewPager) {
        this.linearLayout = linearLayout
        this.viewPager = viewPager

        val res = context.resources
        for (i in 0 until listImage.size) {
            val view = View(context)
            val dimen: Int = res.displayMetrics.density.times(12).toInt()
            val layoutParams = LinearLayoutCompat.LayoutParams(dimen, dimen)
            layoutParams.leftMargin = res.displayMetrics.density.times(6).toInt()
            layoutParams.rightMargin = res.displayMetrics.density.times(6).toInt()
            view.layoutParams = layoutParams
            view.setBackgroundResource(R.drawable.ic_indicator_onboard)
            view.isSelected = i == 0
            this.linearLayout.addView(view)
        }
        viewPager.addOnPageChangeListener(this)
        setSelectedIndicator()

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
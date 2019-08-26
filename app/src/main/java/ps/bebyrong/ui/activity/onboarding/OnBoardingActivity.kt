package bct.id.horekamobile.ui.activity.onboarding

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.viewpager.widget.ViewPager
import org.koin.android.ext.android.inject
import ps.bebyrong.R
import ps.bebyrong.base.BaseActivity
import ps.bebyrong.databinding.ActivityOnboardingBinding
import ps.bebyrong.ui.activity.main.MainActivity
import ps.bebyrong.utils.PrefManager


/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>() {
    private val prefManager by inject<PrefManager>()

    override fun getToolbarResource(): Int = 0

    override fun getLayoutResource(): Int = R.layout.activity_onboarding

    override fun myCodeHere() {

        val listImage = arrayOf(R.drawable.page_1, R.drawable.page_2, R.drawable.page_3)
        val listTitle = arrayOf(
                getString(R.string.onboard_title1),
                getString(R.string.onboard_title2),
                getString(R.string.onboard_title3)
        )
        val listMessage = arrayOf(
                getString(R.string.onboard_message1),
                getString(R.string.onboard_message2),
                getString(R.string.onboard_message3)
        )

        val onboardAdapter = OnBoardingAdapter(
                supportFragmentManager, this
                , listImage, listTitle, listMessage
        )
        onboardAdapter.showIndicator(dataBinding.llIndicator, dataBinding.vpOnboarding)

        var curPosition = 0
        dataBinding.vpOnboarding.adapter = onboardAdapter
        dataBinding.vpOnboarding.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                curPosition = position
                if (position == onboardAdapter.count - 1) {
                    dataBinding.btnStart.visibility = View.VISIBLE
                    dataBinding.btnSkip.visibility=View.GONE
                    dataBinding.btnNext.visibility =View.GONE
                } else {
                    dataBinding.btnStart.visibility = View.GONE
                    dataBinding.btnSkip.visibility=View.VISIBLE
                    dataBinding.btnNext.visibility =View.VISIBLE
                }

            }
        })
        dataBinding.btnStart.setOnClickListener {
            prefManager.saveBoolean(prefManager.PREF_ONBOARD, true)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        dataBinding.btnNext.setOnClickListener {
                curPosition += 1
                dataBinding.vpOnboarding.setCurrentItem(curPosition, true)
        }

        dataBinding.btnSkip.setOnClickListener {
            prefManager.saveBoolean(prefManager.PREF_ONBOARD, true)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                prefManager.saveBoolean(prefManager.PREF_ONBOARD, true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        prefManager.saveBoolean(prefManager.PREF_ONBOARD, true)
    }
}
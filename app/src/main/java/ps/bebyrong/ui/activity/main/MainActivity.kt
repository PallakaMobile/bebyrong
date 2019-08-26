package ps.bebyrong.ui.activity.main

import android.content.Intent
import android.content.res.Resources
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bct.id.horekamobile.ui.activity.onboarding.OnBoardingActivity
import com.github.ajalt.timberkt.e
import io.reactivex.functions.Consumer
import org.koin.android.ext.android.inject
import ps.bebyrong.R
import ps.bebyrong.base.BaseActivity
import ps.bebyrong.data.DataBroadcast
import ps.bebyrong.databinding.ActivityMainBinding
import ps.bebyrong.ui.fragment.BackPressListener
import ps.bebyrong.utils.GlideApp
import ps.bebyrong.utils.Hi
import ps.bebyrong.utils.PrefManager


/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val prefManager: PrefManager by inject()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var hostFragment: NavHostFragment

    override fun getToolbarResource(): Int = R.id.toolbar
    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        if (!prefManager.getBoolean(prefManager.PREF_ONBOARD)) {
            startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            finish()
            return
        }

        hostFragment = supportFragmentManager
                .findFragmentById(R.id.beby_rong_nav_host) as NavHostFragment? ?: return
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.home_dest, R.id.news_dest, R.id.commodity_dest, R.id.stock_dest, R.id.price_dest),
                null)

        //setup actionbar
        val navController = hostFragment.navController
        dataBinding.collapsingToolbarLayout.setupWithNavController(dataBinding.toolbar, navController, appBarConfiguration)
        dataBinding.collapsingToolbarLayout.isTitleEnabled = false

        //setup bottom navigation
        dataBinding.bottomNavView.setupWithNavController(navController)

        dataBinding.ivAbout.setOnClickListener {
            hostFragment.navController.navigate(R.id.about_dest)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            e { dest }


            dataBinding.ivAbout.visibility = if (destination.id == R.id.home_dest) View.VISIBLE else View.GONE

            when (destination.id) {
                R.id.commodity_profile_dest, R.id.stock_market_detail_dest, R.id.price_detail_dest -> {
                    //reset previous view
                    dataBinding.tvName.text = ""
                    dataBinding.tvSchedule.text = ""
                    dataBinding.ivHeader.setImageResource(0)

                    dataBinding.bottomNavView.visibility = View.GONE
                    dataBinding.appBarLayout.setExpanded(true, true)
                    dataBinding.rlHeader.visibility = View.VISIBLE
                    dataBinding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                    dataBinding.collapsingToolbarLayout.invalidate()
                }
                else -> {
                    dataBinding.appBarLayout.setExpanded(false, false)
                    dataBinding.bottomNavView.visibility = View.VISIBLE
                    dataBinding.rlHeader.visibility = View.GONE
                    dataBinding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                }
            }
        }

        //set receiver from broadcast Commodity Profile
        Hi.listen(DataBroadcast::class.java,
                Consumer {
                    dataBinding.tvName.text = it.Title
                    dataBinding.tvSchedule.text = it.SubTitle
                    GlideApp.with(this)
                            .load(it.ImageURL)
                            .centerCrop()
                            .into(dataBinding.ivHeader)
                }, Consumer {
            it.printStackTrace()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {   //create listener to fragment whos implement BackPressListener
                val currentFragment = hostFragment.childFragmentManager.fragments[0]
                val controller = Navigation.findNavController(this, R.id.beby_rong_nav_host)
                if (currentFragment is BackPressListener)
                    (currentFragment as BackPressListener).onBackPressed()
                else if (!controller.popBackStack())
                    finish()
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.beby_rong_nav_host).navigateUp()
    }

    override fun onBackPressed() {
        //create listener to fragment whos implement BackPressListener
        val currentFragment = hostFragment.childFragmentManager.fragments[0]
        val controller = Navigation.findNavController(this, R.id.beby_rong_nav_host)
        if (currentFragment is BackPressListener)
            (currentFragment as BackPressListener).onBackPressed()
        else if (!controller.popBackStack())
            finish()
    }
}
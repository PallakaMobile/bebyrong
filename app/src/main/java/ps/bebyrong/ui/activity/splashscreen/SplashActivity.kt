package ps.bebyrong.ui.activity.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ps.bebyrong.R
import ps.bebyrong.base.BaseActivity
import ps.bebyrong.databinding.ActivitySplashBinding
import ps.bebyrong.ui.activity.main.MainActivity
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getToolbarResource(): Int = 0
    override fun getLayoutResource(): Int = R.layout.activity_splash

    @SuppressLint("MissingPermission")
    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
    }

}

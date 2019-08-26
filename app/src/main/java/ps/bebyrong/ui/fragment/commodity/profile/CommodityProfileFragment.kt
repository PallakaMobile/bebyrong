package ps.bebyrong.ui.fragment.commodity.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentCommodityProfileBinding
import ps.bebyrong.utils.Hi

/**
 **********************************************
 * Created by ukie on 4/20/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

class CommodityProfileFragment : BaseFragment<FragmentCommodityProfileBinding>() {
    private val viewModel by viewModel<CommodityProfileViewModel>()
    override fun getLayoutResource(): Int = R.layout.fragment_commodity_profile

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel

        arguments?.apply {
            val layoutParams = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            layoutParams.topMargin = resources.displayMetrics.density.times(10).toInt()
            layoutParams.rightMargin = resources.displayMetrics.density.times(8).toInt()
            layoutParams.leftMargin = resources.displayMetrics.density.times(8).toInt()

            viewModel.getProfileFormers(getString("id"), getString("idPangan"))

            viewModel.getProductFormers(getString("id")).observe(this@CommodityProfileFragment, Observer {response->
                val adapter = CommodityProfileAdapter()
                adapter.updateCommodityProfileAdapter(response.data ?: throw Exception("Error"))
                dataBinding.rvProduct.setHasFixedSize(true)
                dataBinding.rvProduct.layoutManager = LinearLayoutManager(activity)
                dataBinding.rvProduct.adapter = adapter

            })

            viewModel.profileFormers.observe(this@CommodityProfileFragment, Observer { profile->
                dataBinding.btnPhone.setOnClickListener {
                    val phoneIntent = Intent(Intent.ACTION_DIAL)
                    phoneIntent.data = Uri.parse("tel:${profile.data?.telp}")
                    activity.startActivity(phoneIntent)
                    }

                dataBinding.btnLocation.setOnClickListener {
                    val gmmIntentUri = Uri.parse("geo:${profile.data?.lokasi}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    activity.startActivity(mapIntent)
                }
            })
        }
        activity.supportActionBar?.title = ""
        Hi.broadcast(CommodityProfile("test", "Ini Contoh"))
    }

    data class CommodityProfile(var title: String, var est: String)


}
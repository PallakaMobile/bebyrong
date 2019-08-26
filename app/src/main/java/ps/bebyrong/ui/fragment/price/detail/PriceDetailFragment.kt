package ps.bebyrong.ui.fragment.price.detail

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.databinding.FragmentPriceDetailBinding

/**
 **********************************************
 * Created by ukie on 4/28/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class PriceDetailFragment : BaseFragment<FragmentPriceDetailBinding>() {
    private val viewModel by viewModel<PriceDetailViewModel>()

    override fun getLayoutResource(): Int = R.layout.fragment_price_detail

    override fun myCodeHere() {
        activity.supportActionBar?.title = ""
        dataBinding.lifecycleOwner = this

        val adapter = PriceDetailAdapter()

        dataBinding.rvDetailPrice.setHasFixedSize(true)
        dataBinding.rvDetailPrice.layoutManager = LinearLayoutManager(activity)
        dataBinding.rvDetailPrice.adapter = adapter

        viewModel.getPriceDetail(arguments?.getString("idPrice") ?: "0").observe(this, Observer {
            adapter.updatePriceDetailAdapter(it.data ?: throw Exception("Null"),
                    it.data.listPasar?:throw Exception("Null"))
        })
    }

}
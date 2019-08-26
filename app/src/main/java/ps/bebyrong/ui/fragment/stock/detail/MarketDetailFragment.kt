package ps.bebyrong.ui.fragment.stock.detail

import android.graphics.Typeface.BOLD
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.e
import org.koin.androidx.viewmodel.ext.android.viewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.data.DataBroadcast
import ps.bebyrong.data.model.response.ResponseDetailStock
import ps.bebyrong.data.model.response.ResponseYear
import ps.bebyrong.databinding.FragmentStockMarketDetailBinding
import ps.bebyrong.utils.Hi

/**
 **********************************************
 * Created by ukie on 4/24/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class MarketDetailFragment : BaseFragment<FragmentStockMarketDetailBinding>() {
    private val viewModel by viewModel<MarketDetailViewModel>()
    private var listRank = mutableListOf<ResponseDetailStock.ItemItem>()

    private var month = ""
    private var year = ""
    private var sort = ""


    override fun getLayoutResource(): Int = R.layout.fragment_stock_market_detail

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel
        activity.supportActionBar?.title = ""

        val months = arrayListOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Ags", "Sep", "Okt", "Nov", "Des")
        val sortBy = arrayOf("Tertinggi", "Terendah")
        viewModel.getYear().observe(this, Observer { years ->
            val adapterYear = ArrayAdapter<ResponseYear.DataItem>(activity, android.R.layout.simple_spinner_item, years)
            adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataBinding.spYear.adapter = adapterYear
            dataBinding.spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    year = years[position].tahun.toString()
                    getDetailStok()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            val adapterMonth = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, months)
            adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataBinding.spMonth.adapter = adapterMonth
            dataBinding.spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    month = "${position + 1}"
                    getDetailStok()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            val adapterSort = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, sortBy)
            adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataBinding.spHigh.adapter = adapterSort
            dataBinding.spHigh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    sort = sortBy[position]

                    if (listRank != null) {

                        val sortRank =
                                if (sort == "Tertinggi") listRank.sortedByDescending { rank -> rank.stokMasuk }
                                else listRank.sortedBy { rank -> rank.stokMasuk }
                        listRank.clear()
                        listRank.addAll(sortRank)
                        setTableRank()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        })
    }

    private fun getDetailStok() {
        if (arguments?.getString("idMarket").toString().isNotEmpty() and month.isNotEmpty() and year.isNotEmpty())
            viewModel.getDetailStock(linkedMapOf(
                    "idPasar" to arguments?.getString("idMarket").toString(),
                    "bulan" to month,
                    "tahun" to year
            )).observe(this, Observer {
                e {
                    it.nama.toString() +
                            it.alamat.toString() +
                            it.gambar.toString()
                }
                Hi.broadcast(DataBroadcast(
                        it.nama.toString(),
                        it.alamat.toString(),
                        it.gambar.toString()
                ))
                listRank.clear()
                listRank.addAll(it.item ?: throw Exception("Null"))


                if (listRank != null) {

                    val sortRank =
                            if (sort == "Tertinggi") listRank.sortedByDescending { rank -> rank.stokMasuk }
                            else listRank.sortedBy { rank -> rank.stokMasuk }
                    listRank.clear()
                    listRank.addAll(sortRank)
                    setTableRank()
                }
            })
    }

    private fun setTableRank() {
        //clear table first
        dataBinding.tableStock.removeAllViews()

        //setup title table
        val rowLp = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT, 0.4f)
        rowLp.height = resources.getDimension(R.dimen.dp30).toInt()
        val cellLp = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 0.3f)


        val tableRowTitle = TableRow(activity)
        for (i in 1..5) {
            val tvTitle = TextView(activity)
            tvTitle.setTypeface(null, BOLD)
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
            val text = when (i) {
                1 -> "No."
                2 -> "Item Pangan"
                3 -> "Masuk"
                4 -> "Distribusi"
                else -> "Stok"
            }
            tvTitle.text = text
            when (i) {
                1 -> {
                    tvTitle.gravity = Gravity.CENTER
                    tvTitle.setPaddingRelative(0, 0, 0, 0)
                }
                2 -> {
                    tvTitle.setPaddingRelative(50, 0, 50, 0)
                    tvTitle.gravity = Gravity.CENTER
                }
                3 -> {
                    tvTitle.setPaddingRelative(10, 0, 10, 0)
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(activity, R.drawable.ic_stor_up), null, null, null)
                    tvTitle.compoundDrawablePadding = 2
                    tvTitle.gravity = Gravity.CENTER
                }
                4 -> {
                    tvTitle.setPaddingRelative(10, 0, 10, 0)
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(activity, R.drawable.ic_stor_down), null, null, null)
                    tvTitle.compoundDrawablePadding = 2
                    tvTitle.gravity = Gravity.CENTER
                }
                5 -> {
                    tvTitle.setPaddingRelative(10, 0, 10, 0)
                    tvTitle.gravity = Gravity.CENTER
                }
            }
            tableRowTitle.addView(tvTitle, cellLp)

        }
        dataBinding.tableStock.addView(tableRowTitle, rowLp)


        for (item in listRank.indices) {
            val tableRow = TableRow(activity)
            for (loops in 1..5) {
                val tv = TextView(activity)
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                tv.text = when (loops) {
                    1 -> "${item + 1}"
                    2 -> listRank[item].itemPangan
                    3 -> listRank[item].stokMasuk.toString()
                    4 -> listRank[item].stokKeluar.toString()
                    else -> listRank[item].sisaStok.toString()
                }

                when (loops) {
                    1, 3, 4, 5 -> tv.gravity = Gravity.CENTER
                    2 -> tv.gravity = Gravity.CENTER_VERTICAL
                }

                tableRow.addView(tv, cellLp)

                e { loops.toString() }
            }
            e { item.toString() }
            dataBinding.tableStock.addView(tableRow, rowLp)
        }
    }

    override fun onResume() {
        super.onResume()
        dataBinding.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        dataBinding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }
}
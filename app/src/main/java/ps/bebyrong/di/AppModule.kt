package ps.bebyrong.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ps.bebyrong.ui.fragment.commodity.CommodityViewModel
import ps.bebyrong.ui.fragment.commodity.info.CommodityInfoViewModel
import ps.bebyrong.ui.fragment.commodity.profile.CommodityProfileViewModel
import ps.bebyrong.ui.fragment.news.NewsViewModel
import ps.bebyrong.ui.fragment.news.category.NewsCategoryViewModel
import ps.bebyrong.ui.fragment.news.category.detail.NewsDetailViewModel
import ps.bebyrong.ui.fragment.price.PriceViewModel
import ps.bebyrong.ui.fragment.price.detail.PriceDetailViewModel
import ps.bebyrong.ui.fragment.stock.StockTypeViewModel
import ps.bebyrong.ui.fragment.stock.detail.MarketDetailViewModel
import ps.bebyrong.utils.PrefManager
import ps.bebyrong.utils.RxEditTextBinding

/**
 **********************************************
 * Created by ukie on 10/4/18 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2018 | All Right `
 */

/**
 *  ViewModel inject
 */
//TODO change with project name sample tiketViewModel
val viewModels = module {
    // TODO define view model to inject
    viewModel { CommodityViewModel(get()) }
    viewModel { CommodityInfoViewModel(get()) }
    viewModel { CommodityProfileViewModel(get()) }
    viewModel { NewsViewModel(get()) }
    viewModel { NewsCategoryViewModel(get()) }
    viewModel { NewsDetailViewModel(get()) }
    viewModel { PriceViewModel(get()) }
    viewModel { PriceDetailViewModel(get()) }
    viewModel { StockTypeViewModel(get()) }
    viewModel { MarketDetailViewModel(get()) }
}

val globalModule = module {
    single { PrefManager(androidContext()) }
    single { RxEditTextBinding() }
}
val baseApp = listOf(viewModels, networkModule, globalModule, repoModule)
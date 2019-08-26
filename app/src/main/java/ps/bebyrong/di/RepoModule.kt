package ps.bebyrong.di

import org.koin.dsl.module
import ps.bebyrong.data.repo.RepoCommodity
import ps.bebyrong.data.repo.RepoNews
import ps.bebyrong.data.repo.RepoPrice
import ps.bebyrong.data.repo.RepoStock

/**
 **********************************************
 * Created by ukie on 2/25/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */

val repoModule = module {
    single { RepoCommodity(get()) }
    single { RepoNews(get()) }
    single { RepoPrice(get()) }
    single { RepoStock(get()) }
}
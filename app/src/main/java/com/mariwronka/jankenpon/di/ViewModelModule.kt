package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.ui.common.IO_DISPATCHER
import com.mariwronka.jankenpon.ui.viremodels.JankenponViewModel
import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel {
        PlayersViewModel(
            ioDispatcher = get(named(IO_DISPATCHER)),
            repository = get(),
            playerDataManager = get(),
        )
    }

    viewModel {
        JankenponViewModel(
            ioDispatcher = get(named(IO_DISPATCHER)),
            repository = get(),
            playerDataManager = get(),
        )
    }
}

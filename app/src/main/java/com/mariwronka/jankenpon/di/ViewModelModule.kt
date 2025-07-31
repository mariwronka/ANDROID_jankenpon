package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.ui.viremodels.PlayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { PlayersViewModel(get()) }
}

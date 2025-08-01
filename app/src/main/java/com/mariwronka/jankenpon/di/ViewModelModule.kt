package com.mariwronka.jankenpon.di

import com.mariwronka.jankenpon.ui.viewmodels.PlayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { PlayersViewModel(get()) }
}

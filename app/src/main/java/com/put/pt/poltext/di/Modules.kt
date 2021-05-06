package com.put.pt.poltext.di



import com.put.pt.poltext.repository.users.FirebaseUsersRepository
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.home.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel


import org.koin.dsl.module

val chatViewModelModule = module {
    single { FirebaseUsersRepositoryImpl() }
    viewModel {
        ChatViewModel(get())
    }
}


val viewModelModules = listOf(chatViewModelModule)

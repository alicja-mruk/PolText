package com.put.pt.poltext

import android.app.Application
import com.put.pt.poltext.data.firebase.common.FirebaseAuthManager
import com.put.pt.poltext.di.viewModelModules
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    val usersRepo by lazy { FirebaseUsersRepositoryImpl() }
    val authManager by lazy { FirebaseAuthManager() }

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(moduleList)
        }
    }

    private val moduleList =  viewModelModules
}
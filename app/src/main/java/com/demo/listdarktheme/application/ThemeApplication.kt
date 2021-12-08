package com.demo.listdarktheme.application

import android.app.Application
import android.content.Context
import com.demo.listdarktheme.database.RecipeDatabase
import com.demo.listdarktheme.rest.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ThemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        lateinit var appInstance: ThemeApplication
        val appContext: Context by lazy {
            appInstance.applicationContext
        }
        private val applicationScope = CoroutineScope(SupervisorJob())
        private val database by lazy { RecipeDatabase.getDatabase(appContext,applicationScope) }
        val repository by lazy { RecipeRepository(database.recipeDao()) }
    }
}
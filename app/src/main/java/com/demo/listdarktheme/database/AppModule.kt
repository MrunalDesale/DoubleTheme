package com.demo.listdarktheme.database

import android.content.Context
import androidx.room.Room
import com.demo.listdarktheme.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRecipeDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, RecipeDatabase::class.java, "RecipeDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRecipeDAO(appDatabase: RecipeDatabase): RecipeDao {
        return appDatabase.recipeDao()
    }
}
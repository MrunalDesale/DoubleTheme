package com.demo.listdarktheme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.listdarktheme.dao.RecipeDao
import com.demo.listdarktheme.ui.model.RecipeModel

@Database(entities = [RecipeModel::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
package com.demo.listdarktheme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.demo.listdarktheme.dao.RecipeDao
import com.demo.listdarktheme.ui.model.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [RecipeModel::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        private var mInstance: RecipeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RecipeDatabase {
            return mInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "RecipeDB"
                ).addCallback(RecipeDatabaseCallback(scope))
                    .build()
                mInstance = instance
                instance
            }
        }
    }

    class RecipeDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            mInstance?.let {
                scope.launch {
                    populateDatabase()
                }
            }
        }

        private fun populateDatabase() {

        }
    }
}
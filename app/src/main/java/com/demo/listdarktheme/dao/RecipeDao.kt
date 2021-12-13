package com.demo.listdarktheme.dao

import androidx.room.*
import com.demo.listdarktheme.ui.model.RecipeModel

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeModel: RecipeModel)

    @Query("Select * from Recipe")
    suspend fun getAllRecipe(): List<RecipeModel?>?

    @Update
    suspend fun updateRecipe(recipeModel: RecipeModel)
}
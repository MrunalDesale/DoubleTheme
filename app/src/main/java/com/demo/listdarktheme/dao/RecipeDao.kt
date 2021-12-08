package com.demo.listdarktheme.dao

import androidx.room.*
import com.demo.listdarktheme.ui.model.RecipeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeModel: RecipeModel)

    @Query("Select * from Recipe")
    fun getAllRecipe(): Flow<List<RecipeModel?>?>

    @Update
    suspend fun updateRecipe(recipeModel: RecipeModel)
}
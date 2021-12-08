package com.demo.listdarktheme.rest.repository

import androidx.annotation.WorkerThread
import com.demo.listdarktheme.dao.RecipeDao
import com.demo.listdarktheme.rest.api.RestApi
import com.demo.listdarktheme.rest.api.RetrofitBuilder
import com.demo.listdarktheme.rest.mapper.DataMapper
import com.demo.listdarktheme.ui.model.RecipeModel
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val dao: RecipeDao) {

    val recipeList : Flow<List<RecipeModel?>?> = dao.getAllRecipe()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertRecipe(recipeModel: RecipeModel) {
        dao.insertRecipe(recipeModel)
    }

    private val restApi: RestApi = RetrofitBuilder.apiInterface
    private val mapper = DataMapper()

    suspend fun getApiResponse(pageNo: Int, size: Int): List<RecipeModel> {
        restApi.getApiResult(pageNo, size).let {
            return mapper.mapRecipe(it)
        }
    }
}
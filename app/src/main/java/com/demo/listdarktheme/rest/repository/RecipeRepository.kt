package com.demo.listdarktheme.rest.repository

import androidx.annotation.WorkerThread
import com.demo.listdarktheme.dao.RecipeDao
import com.demo.listdarktheme.rest.api.RestApi
import com.demo.listdarktheme.rest.api.RetrofitBuilder
import com.demo.listdarktheme.rest.mapper.DataMapper
import com.demo.listdarktheme.rest.model.ResultWrapper
import com.demo.listdarktheme.rest.utils.NetworkUtils
import com.demo.listdarktheme.ui.model.RecipeModel
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class RecipeRepository @Inject constructor(private val dao: RecipeDao) {

    suspend fun getRecipes(): List<RecipeModel?>? {
        return dao.getAllRecipe()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertRecipe(recipeModel: RecipeModel) {
        dao.insertRecipe(recipeModel)
    }

    private val restApi: RestApi = RetrofitBuilder.apiInterface
    private val mapper = DataMapper()

    suspend fun getApiResponse(pageNo: Int, size: Int): ResultWrapper<List<RecipeModel>>? {
        return NetworkUtils.makeApiCall {
            val res = restApi.getApiResult(pageNo, size)
            mapper.mapRecipe(res)
        }
    }
}
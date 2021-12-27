package com.demo.listdarktheme.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.listdarktheme.rest.model.ErrorResponse
import com.demo.listdarktheme.rest.model.ResultWrapper
import com.demo.listdarktheme.rest.repository.RecipeRepository
import com.demo.listdarktheme.ui.model.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    application: Application,
    private val recipeRepository: RecipeRepository
) :
    AndroidViewModel(application) {
    private val mRecipesResult: MutableLiveData<List<RecipeModel>?> = MutableLiveData()
    private val mError: MutableLiveData<ErrorResponse> = MutableLiveData()

    fun getRecipes(pageNo: Int, size: Int) = viewModelScope.launch {
        when (val recipeList = recipeRepository.getApiResponse(pageNo, size)) {
            is ResultWrapper.NetworkError -> {
                mError.postValue(ErrorResponse("Network error"))
            }
            is ResultWrapper.GenericError -> {
                mError.postValue(
                    recipeList.error ?: ErrorResponse("Something went wrong. Please try again.")
                )
            }

            is ResultWrapper.Success -> {
                mRecipesResult.postValue(recipeList.value)
            }
        }
    }

    fun getRecipesList() = mRecipesResult
    fun getError() = mError

    suspend fun getRecipes(): List<RecipeModel?>? {
        return recipeRepository.getRecipes()
    }

    suspend fun insertRecipe(recipeModel: RecipeModel) = viewModelScope.launch {
        recipeRepository.insertRecipe(recipeModel)
    }
}
package com.demo.listdarktheme.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.AndroidViewModel
import com.demo.listdarktheme.rest.model.ResultWrapper
import com.demo.listdarktheme.rest.repository.RecipeRepository
import com.demo.listdarktheme.rest.utils.NetworkUtils
import com.demo.listdarktheme.ui.model.RecipeModel
import kotlinx.coroutines.launch

open class MainViewModel(application: Application,private val recipeRepository: RecipeRepository) :
    AndroidViewModel(application) {
    private val mRecipesResult: MutableLiveData<List<RecipeModel>?> = MutableLiveData()

    fun getRecipes(pageNo: Int, size: Int) = viewModelScope.launch {
        val recipeList = NetworkUtils.makeApiCall {
            recipeRepository.getApiResponse(pageNo, size)
        }
        when (recipeList) {
            is ResultWrapper.NetworkError -> {

            }
            is ResultWrapper.GenericError -> {

            }

            is ResultWrapper.Success -> {
                mRecipesResult.postValue(recipeList.value)
            }
        }
    }

    fun getRecipesList() = mRecipesResult

    val recipeModelList: LiveData<List<RecipeModel?>?> = recipeRepository.recipeList.asLiveData()

    suspend fun insertRecipe(recipeModel: RecipeModel) = viewModelScope.launch {
        recipeRepository.insertRecipe(recipeModel)
    }

    class MainViewModelFactory(private val application: Application,private val repository: RecipeRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(application ,repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
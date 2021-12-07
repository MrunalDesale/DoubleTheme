package com.demo.listdarktheme.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.listdarktheme.rest.repository.BaseRepository
import com.demo.listdarktheme.ui.model.RecipeModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val baseRepository: BaseRepository = BaseRepository()
    private val mRecipesResult: MutableLiveData<List<RecipeModel>> = MutableLiveData()

    fun getRecipes(pageNo: Int, size: Int) = viewModelScope.launch {
        showLoader.postValue(true)
        val recipeList = baseRepository.makeApiCall(this@MainViewModel) {
            baseRepository.getResponse(pageNo, size)
        }
        mRecipesResult.postValue(recipeList ?: ArrayList())
        showLoader.postValue(false)
    }

    fun getRecipesList() = mRecipesResult
}
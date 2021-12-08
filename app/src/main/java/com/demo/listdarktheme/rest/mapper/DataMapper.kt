package com.demo.listdarktheme.rest.mapper

import com.demo.listdarktheme.rest.model.Recipe
import com.demo.listdarktheme.ui.model.RecipeModel

class DataMapper {
    fun mapRecipe(recipe: Recipe): List<RecipeModel> {
        val recipeModel = ArrayList<RecipeModel>()
        recipe.results?.forEach {
            val model = RecipeModel()
            if (it.credits.isNotEmpty())
                model.credit_name = it.credits[0].name
            model.name = it.name
            model.id= it.id
            model.thumbnail_url = it.thumbnail_url
            recipeModel.add(model)
        }
        return recipeModel

    }
}
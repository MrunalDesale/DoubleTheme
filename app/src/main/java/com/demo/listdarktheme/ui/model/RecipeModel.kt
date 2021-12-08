package com.demo.listdarktheme.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
data class RecipeModel(
    @PrimaryKey
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "credit_name")
    var credit_name: String? = null,
    @ColumnInfo(name = "thumbnail_url")
    var thumbnail_url: String? = null
)
package com.demo.listdarktheme.rest.model

data class Recipe(
    val count: Int?=0,
    val results: List<Result>?=null
){
    data class Component(
        val extra_comment: String,
        val id: Int,
        val ingredient: Ingredient,
        val measurements: List<Measurement>,
        val position: Int,
        val raw_text: String
    )
    data class Credit(
        val name: String,
        val type: String
    )
    data class Ingredient(
        val created_at: Int,
        val display_plural: String,
        val display_singular: String,
        val id: Int,
        val name: String,
        val updated_at: Int
    )
    data class Instruction(
        val appliance: String,
        val display_text: String,
        val end_time: Int,
        val id: Int,
        val position: Int,
        val start_time: Int,
        val temperature: Int
    )
    data class Measurement(
        val id: Int,
        val quantity: String,
        val unit: Unit
    )
    data class Nutrition(
        val calories: Int,
        val carbohydrates: Int,
        val fat: Int,
        val fiber: Int,
        val protein: Int,
        val sugar: Int,
        val updated_at: String
    )
    data class Result(
        val approved_at: Int,
        val aspect_ratio: String,
        val beauty_url: Any,
        val brand: Any,
        val brand_id: Any,
        val buzz_id: Any,
        val canonical_id: String,
        val compilations: List<Any>,
        val cook_time_minutes: Any,
        val country: String,
        val created_at: Int,
        val credits: List<Credit>,
        val description: String,
        val draft_status: String,
        val facebook_posts: List<Any>,
        val id: Int,
        val inspired_by_url: Any,
        val instructions: List<Instruction>,
        val is_one_top: Boolean,
        val is_shoppable: Boolean,
        val keywords: String,
        val language: String,
        val name: String,
        val num_servings: Int,
        val nutrition: Nutrition,
        val nutrition_visibility: String,
        val original_video_url: Any,
        val prep_time_minutes: Any,
        val promotion: String,
        val renditions: List<Any>,
        val sections: List<Section>,
        val seo_title: String,
        val servings_noun_plural: String,
        val servings_noun_singular: String,
        val show: Show,
        val show_id: Int,
        val slug: String,
        val tags: List<Tag>,
        val thumbnail_alt_text: String,
        val thumbnail_url: String,
        val tips_and_ratings_enabled: Boolean,
        val topics: List<Topic>,
        val total_time_minutes: Any,
        val total_time_tier: TotalTimeTier,
        val updated_at: Int,
        val user_ratings: UserRatings,
        val video_ad_content: Any,
        val video_id: Any,
        val video_url: Any,
        val yields: String
    )
    data class Section(
        val components: List<Component>,
        val name: Any,
        val position: Int
    )
    data class Show(
        val id: Int,
        val name: String
    )
    data class Tag(
        val display_name: String,
        val id: Int,
        val name: String,
        val type: String
    )
    data class Topic(
        val name: String,
        val slug: String
    )
    data class TotalTimeTier(
        val display_tier: String,
        val tier: String
    )
    data class Unit(
        val abbreviation: String,
        val display_plural: String,
        val display_singular: String,
        val name: String,
        val system: String
    )
    data class UserRatings(
        val count_negative: Int,
        val count_positive: Int,
        val score: Any
    )
}
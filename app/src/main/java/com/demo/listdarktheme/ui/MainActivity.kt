package com.demo.listdarktheme.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.listdarktheme.R
import com.demo.listdarktheme.rest.utils.NetworkUtils
import com.demo.listdarktheme.ui.model.RecipeModel
import com.demo.listdarktheme.ui.viewmodel.MainViewModel
import com.demo.listdarktheme.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: FoodAdapter

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getThemeType(this) == AppConstants.DARK_THEME)
            setTheme(R.style.Theme_AppCompat_ThemeDark)
        else
            setTheme(R.style.Theme_AppCompat_ThemeLight)
        setContentView(R.layout.activity_main)
        setView()
        setViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.item_theme)
        menuItem?.setActionView(R.layout.layout_toggle_item)
        val switch = menuItem?.actionView?.findViewById<SwitchCompat>(R.id.switch_theme)
        switch?.isChecked = getThemeType(this) == AppConstants.DARK_THEME
        switch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setThemeType(this,AppConstants.DARK_THEME)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                setThemeType(this,AppConstants.LIGHT_THEME)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setView() {
        mAdapter = FoodAdapter()
        rv_food.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }

    private fun setViewModel() {
        mainViewModel.getRecipesList().observe(this, {
            progress_bar.visibility = View.GONE
            if (it != null && it.isNotEmpty()) {
                mAdapter.updateList(it as ArrayList<RecipeModel>)
                lifecycleScope.launch {
                    it.forEach { recipeMode ->
                        mainViewModel.insertRecipe(recipeMode)
                    }
                }
            }
        })
        mainViewModel.getError().observe(this, {
            progress_bar.visibility = View.GONE
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
        if (NetworkUtils.isInternetAvailable(this)) {
            progress_bar.visibility = View.VISIBLE
            mainViewModel.getRecipes(0, 10)
        } else {
            showToast("No internet available. Fetching data from database..")
            lifecycleScope.launch {
                if (mainViewModel.getRecipes()?.size ?: 0 > 0)
                    mAdapter.updateList(mainViewModel.getRecipes() as ArrayList<RecipeModel>)
            }
        }
    }
}
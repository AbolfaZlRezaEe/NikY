package com.abproject.niky.view.favoriteslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abproject.niky.databinding.ActivityFavoritesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
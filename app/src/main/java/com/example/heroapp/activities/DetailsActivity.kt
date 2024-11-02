package com.example.heroapp.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.heroapp.HeroesDetailsViewModel
import com.example.heroapp.HeroesExpandableAdapter
import com.example.heroapp.databinding.DetailsExpandableListBinding



class DetailsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailsListActivityBinding: DetailsExpandableListBinding = DetailsExpandableListBinding.inflate(layoutInflater)
        val view = detailsListActivityBinding.root
        setContentView(view)
        // Create a reference to the expandable list view in your layout
        val expandableListView = detailsListActivityBinding.listView
        val viewModel = ViewModelProvider(this)[HeroesDetailsViewModel::class.java]
        //get the Id from HomeActivity with intent & set it to the ViewModel
        val heroId = intent.getIntExtra("hero_ID", 0)
        viewModel.fetchCharacters(heroId)
        // Observe the changes in heroName,heroImage & character
        viewModel.heroName.observe(this) { heroName ->
            detailsListActivityBinding.heroName.text = heroName
        }
        viewModel.heroImage.observe(this) { heroImage ->
            Glide.with(view)
                .load(heroImage)
                .into(detailsListActivityBinding.heroImage)
        }
        viewModel.character.observe(this) { character ->
            Log.i("In Details Activity", "Character details: $character")
            val adapter = HeroesExpandableAdapter(view.context, viewModel.character)
            expandableListView.setAdapter(adapter)
        }
        viewModel.error.observe(this){
            Toast.makeText(this, "Failed to fetch character details", Toast.LENGTH_LONG).show()
        }
    }
}

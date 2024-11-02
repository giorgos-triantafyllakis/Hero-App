package com.example.heroapp.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heroapp.R
import com.example.heroapp.paging.HeroesPagingAdapter
import com.example.heroapp.HeroesViewModel
import com.example.heroapp.OnImageClickInterface
import com.example.heroapp.databinding.ActivityHomeBinding
import com.example.heroapp.network.dataClasses.CharacterRestModel


class HomeActivity : AppCompatActivity(), OnImageClickInterface   {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var heroesAdapter: HeroesPagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        val view = activityHomeBinding.root
        setContentView(view)
        val viewModel = ViewModelProvider(this)[HeroesViewModel::class.java]
        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager?
        val recyclerView = activityHomeBinding.recyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        heroesAdapter = HeroesPagingAdapter(this)
        recyclerView.adapter = heroesAdapter

        // Define a boolean flag to keep track of whether the end-of-list message has been shown
        var endOfListMessageShown = false
        // Observe the charactersLiveData property of the view model
        viewModel.charactersLiveData.observe(this) { pagingData ->
            heroesAdapter.submitData(lifecycle, pagingData)
        }

        // Add a load state listener to the adapter
        heroesAdapter.addLoadStateListener { loadState ->
            // Check if we have reached the end of the list and the message hasn't been shown before
            if (!endOfListMessageShown && loadState.append.endOfPaginationReached) {

                Log.i("EndOfList:You've reached the end of the list", loadState.toString())
                Toast.makeText(this, "You've reached the end of the list", Toast.LENGTH_LONG).show()
                // Set the flag to true to indicate that the message has been shown
                endOfListMessageShown = true
            } else if (endOfListMessageShown && !loadState.append.endOfPaginationReached) {
                // If the message has been shown before and the user scrolls back up the list,
                // reset the flag to false
                Log.i("EndOfList: You've scrolled back up", loadState.toString())
                endOfListMessageShown = false
            }
        }

        //add a progressbar everytime a new page is loading
        heroesAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                // Show ProgressBar
                activityHomeBinding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide ProgressBar
                activityHomeBinding.progressBar.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        if (!isNetworkConnected(this)) {
            setContentView(R.layout.no_data)
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    override fun onItemClick(character: CharacterRestModel?) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("hero_ID", character?.id)
        startActivity(intent)
    }

}
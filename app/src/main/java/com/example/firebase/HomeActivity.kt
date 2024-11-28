package com.example.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.firebase.databinding.ItemPokemonBinding
import org.json.JSONException

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        fetchPokemons()
    }

    private fun fetchPokemons() {
        val url = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val dataArray = response.getJSONArray("results")
                    val pokemonContainer = findViewById<LinearLayout>(R.id.llPokemonContainer)
                    for (i in 0 until dataArray.length()) {
                        val jsonObj = dataArray.getJSONObject(i)

                        val name = jsonObj.getString("name")
                        val url = jsonObj.getString("url")
                        val pokemonId = url.split("/").filter { it.isNotEmpty() }.last()
                        val userView = createPokemonView(pokemonId, name, url)
                        pokemonContainer.addView(userView)
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Error al procesar datos", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(this, "Error en la solicitud: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }


    private fun createPokemonView(id:String, name: String, url:String): LinearLayout {

        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(this))
        binding.tvId.text = "ID: $id"
        binding.tvName.text = "Name: $name"
        binding.tvUrl.text = "URL: $url"

        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .centerCrop()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivAvatar)
        return binding.root as LinearLayout
    }
}
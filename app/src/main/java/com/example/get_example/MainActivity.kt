package com.example.get_example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.get_example.ui.theme.Get_ExampleTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Get_ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    getAllComments()

                }

            }
        }
    }
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val TAG : String = "CHECK_RESPONSE"

    fun getAllComments(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        for (comment in it){
                            Log.i(TAG, "onResponse: ${comment.body}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }
}

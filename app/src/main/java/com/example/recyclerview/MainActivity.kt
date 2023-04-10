package com.example.recyclerview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var imageList : MutableList<String>
    private lateinit var userList : MutableList<String>
    private lateinit var likeList : MutableList<String>
    private lateinit var tagList : MutableList<String>
    private lateinit var rvImages : RecyclerView
    var inputText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvImages = findViewById(R.id.image_list)
        imageList = mutableListOf()
        userList = mutableListOf()
        likeList = mutableListOf()
        tagList = mutableListOf()

        var button = findViewById<Button>(R.id.button)

        Log.d("imageUrl","image URL set")
        getImages(button)
    }

    /*override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
    */

    private fun getImages(button: Button){
        button.setOnClickListener {
            var input = findViewById<EditText>(R.id.input)
            inputText = input.getText().toString()
            imageList.clear()
            userList.clear()
            likeList.clear()
            tagList.clear()

            getImagesURL()

            val text = "Search successful!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    private fun getImagesURL() {

        var client = AsyncHttpClient()

        client["https://pixabay.com/api/?key=35133480-0dc23bf9666b92ce7fa4e5c7b&q="+inputText+"&image_type=photo", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Image Success", "$json")

                var getImage = json.jsonObject.getJSONArray("hits")

                for (i in 0 until getImage.length()) {
                    var imageArray = getImage.getJSONObject(i)
                    imageList.add(imageArray.getString("webformatURL"))
                }

                for (i in 0 until getImage.length()) {
                    var imageArray = getImage.getJSONObject(i)
                    userList.add(imageArray.getString("user"))
                }
                for (i in 0 until getImage.length()) {
                    var imageArray = getImage.getJSONObject(i)
                    likeList.add(imageArray.getString("likes"))
                }
                for (i in 0 until getImage.length()) {
                    var imageArray = getImage.getJSONObject(i)
                    tagList.add(imageArray.getString("tags"))
                }

                var adapter = ImageAdapter(imageList, userList, likeList, tagList)
                rvImages.adapter = adapter
                rvImages.layoutManager = LinearLayoutManager(this@MainActivity)
                rvImages.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Image Error", errorResponse)
            }
        }]
    }
}
package com.example.printfuljun

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    var listView: RecyclerView? = null
    var TAG = "DEBUG"
    var listItemsName = arrayListOf<String>()
    var listItemsImage = arrayListOf<String>()
    val API = "https://www.json-generator.com/api/json/get/bVlogMnEeq?indent=2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.list)
        var fr = findViewById(R.id.fragment) as FragmentContainerView
        fillTheList()
    }

    private fun fillTheList() {
        try {
            Thread {
                val APIResponse = URL(API).readText()
                val arr = JSONArray(APIResponse)
                for (item in 0 until arr.length()) {
                    val it = arr.getJSONObject(item)
                    listItemsName.add(it.getString("company"))
                    listItemsImage.add(it.getString("picture"))
                }
                /*for (item in listItemsName) {
                    Log.i(TAG, item)
                }
                for (item in listItemsImage) {
                    Log.i(TAG, item)
                }*/
                runOnUiThread {
                    listView!!.adapter = CustomAdapter(listItemsName, listItemsImage, this)
                    listView!!.layoutManager = LinearLayoutManager(this)
                }
            }.start()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }
}



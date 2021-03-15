package com.example.printfuljun

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.core.Observable.interval
import io.reactivex.rxjava3.kotlin.Observables.zip
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

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
        var fr = findViewById<FragmentContainerView>(R.id.fragment)
        fillTheList()
    }

    private fun fillTheList() {
        try {
            val thread = Thread {
                Log.i(TAG, "new request");
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

            }
            thread.start()
            Thread.sleep(1000L)
            thread.interrupt()


        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }
}



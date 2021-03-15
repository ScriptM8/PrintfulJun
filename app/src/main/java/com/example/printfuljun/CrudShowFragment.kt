package com.example.printfuljun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import java.net.URL
import java.util.concurrent.TimeUnit

@GlideModule
class CrudShowFragment : Fragment(), IFragment {
    var teksts: TextView? = null
    var teksts2: TextView? = null
    var teksts3: TextView? = null
    var img1: ImageView? = null
    var img2: ImageView? = null
    var API: String? = null
    var elements: Int? = null
    var thread: Thread? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_layout, container, false)
        (activity as MainActivity).listView!!.visibility = INVISIBLE
        elements = requireArguments().getInt("element")
        getBasicRes()
        initializeAddCont(view)
        initializeCoreAddCont()
        fillTheInfo()
        return view
    }

    override fun getBasicRes() {
        this.API = (activity as MainActivity).API
    }

    override fun initializeAddCont(view: View) {
        teksts = view.findViewById(R.id.teksts)
        teksts2 = view.findViewById(R.id.teksts2)
        teksts3 = view.findViewById(R.id.teksts3)
        img1 = view.findViewById(R.id.imageView)
        img2 = view.findViewById(R.id.imageView2)
        img1!!.setOnClickListener {
            (activity as MainActivity).listView!!.visibility = VISIBLE
            this.requireView().visibility = INVISIBLE
            thread?.interrupt()
        }
    }

    override fun initializeLisForAddCont() {
    }

    override fun initializeCoreAddCont() {

    }

    override fun initializeLisForCoreAddCont() {
    }

    private fun fillTheInfo() {
        try {
            thread = Thread {
                val APIResponse = URL(API).readText()
                val arr = JSONArray(APIResponse).getJSONObject(elements!!)
                (activity as MainActivity).runOnUiThread {
                    teksts!!.text = arr.getString("company")
                    teksts2!!.text = arr.getString("email")
                    teksts3!!.text = arr.getString("about")
                    Glide.with(this).load(R.drawable.back)
                        .apply(RequestOptions().override(128, 128)).into(img1!!)
                    Glide.with(this).load(arr.getString("picture"))
                        .apply(RequestOptions().override(128, 128)).into(img2!!)

                }
            }
            thread?.start()

        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }

    }
}
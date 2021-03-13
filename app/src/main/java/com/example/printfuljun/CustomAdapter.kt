package com.example.printfuljun

import android.content.Context
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions


@GlideModule
class CustomAdapter(
    private val dataSet: ArrayList<String>,
    private val dataSet2: ArrayList<String>,
    private val context: Context
) :
    Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imgView: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.txt)
            imgView = view.findViewById(R.id.image)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
        Glide.with(context).load(dataSet2[position]).apply(RequestOptions().override(128, 128))
            .into(holder.imgView)
        holder.itemView.setOnClickListener(Clicker(this, position, context))
        holder.itemView.tag = holder

    }


    override fun getItemCount(): Int {
        return dataSet.size
    }

}

private fun Clicker(viewHolder: CustomAdapter, position: Int, context: Context): OnClickListener {
    return OnClickListener {
        val frP = (context as AppCompatActivity).supportFragmentManager.popBackStackImmediate(
            CrudShowFragment().javaClass.name,
            0
        )
        var tf: FragmentTransaction =
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putInt("element", position)
        val fragobj = CrudShowFragment()
        fragobj.arguments = bundle
        tf.replace(R.id.fragment, fragobj)
        tf.addToBackStack(CrudShowFragment().javaClass.name)
        tf.commit()

    }
}


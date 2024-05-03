package com.munna.faceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.face.Face

class ImageAdapter(private val context: Context,
                   private var list: MutableList<Face>
): RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var img= list.get(position)

        if(holder is MyViewHolder){
            holder.tv_title.text= img.toString()

            }
    }

    class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val tv_title=view.findViewById<TextView>(R.id.tv_item)
    }
}
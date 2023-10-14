package com.gwallaz.qrcode.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gwallaz.qrcode.R

class Adapter(private val model: List<Model>,
              private val listen: OnItemClickListener) : RecyclerView.Adapter<Adapter.Viewholder>() {

      inner class Viewholder(view : View) : RecyclerView.ViewHolder(view),View.OnClickListener {

          val text: TextView
          val image: ImageView
          init {
              text = view.findViewById(R.id.recycler_text)
              image = view.findViewById(R.id.recycler_imageView)
          }

          init {
              view.setOnClickListener(this)
          }

          override fun onClick(p0: View?) {
              val position = adapterPosition
              if (position != RecyclerView.NO_POSITION){

                  listen.onItemClicked(position)
              }
          }


      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout,parent,false)
        return Viewholder(view)
    }


    override fun getItemCount(): Int {

        return model.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val itemsViewModel = model[position]
        holder.image.setImageResource(itemsViewModel.image)
        holder.text.text = itemsViewModel.text

    }
    interface OnItemClickListener{
        fun onItemClicked(position: Int)
    }

}
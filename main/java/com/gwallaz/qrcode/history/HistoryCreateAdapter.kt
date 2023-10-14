package com.gwallaz.qrcode.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.database.TableCreate

class HistoryCreateAdapter (
    private val listener: CreateOnClickListener
        ): RecyclerView.Adapter<HistoryCreateAdapter.CreateViewHolder>() {

    private var table = emptyList<TableCreate>()

    inner class CreateViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView),View.OnClickListener{

         val nametext: TextView
         val typetext: TextView
         val historyimage: ImageView
         private val cardView: CardView
         private val createHistoryDelete: ImageButton
        init {


            nametext = itemView.findViewById(R.id.history_name_textview)
            typetext = itemView.findViewById(R.id.history_type_textview)
            historyimage = itemView.findViewById(R.id.history_create_imageView)
            cardView = itemView.findViewById(R.id.cardView_create)
            createHistoryDelete = itemView.findViewById(R.id.history_create_delete)
        }
        init {
            createHistoryDelete.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onClick(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateViewHolder {

        return  CreateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_create_recyclerview_layout,parent,false))
    }

    override fun getItemCount(): Int {

        return table.size
    }

    override fun onBindViewHolder(holder: CreateViewHolder, position: Int) {
        val currentItem = table[position]
        holder.nametext.text = currentItem.name
        holder.typetext.text = currentItem.type
        holder.historyimage.setImageResource(currentItem.image)

    }
    fun setData(user : List<TableCreate>){
        this.table = user
        notifyDataSetChanged()

    }

    interface CreateOnClickListener{
        fun onClick(position: Int)
    }

    fun getPosition(position:Int): TableCreate{
        return table[position]

    }

}
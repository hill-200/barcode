package com.gwallaz.qrcode.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.database.TableCreate
import com.gwallaz.qrcode.database.Tablescan

class ScanRecyclerViewAdapter(
private  val listener:HistoryScanOnclickListener):
    RecyclerView.Adapter<ScanRecyclerViewAdapter.MyViewholder>()
{

    private var table = emptyList<Tablescan>()

    inner class MyViewholder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
         val scanImage : ImageView
         val name : TextView
         val type : TextView
        private val carView : CardView
        private val scanImageButton: ImageButton

        init {
            scanImage = view.findViewById(R.id.scanImage)
            name = view.findViewById(R.id.scan_Name)
            type = view.findViewById(R.id.scan_Type)
            carView = view.findViewById(R.id.scan_cardView)
            scanImageButton = view.findViewById(R.id.scan_ImageButton)
        }
        init {
            scanImageButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.scanrecyclerviewlayout,parent,false)

        return MyViewholder(view)

    }

    override fun getItemCount(): Int {
        return table.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val scanViewModel = table[position]
        holder.scanImage.setImageResource(scanViewModel.image)
        holder.name.text = scanViewModel.name
        holder.type.text = scanViewModel.type

    }

    interface HistoryScanOnclickListener{
        fun onClick(position: Int)
    }


    fun setData(user : List<Tablescan>){
        this.table = user
        notifyDataSetChanged()

    }
}
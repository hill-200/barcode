package com.gwallaz.qrcode.create

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.activities.Calender
import com.gwallaz.qrcode.activities.Clipboard
import com.gwallaz.qrcode.activities.Contact
import com.gwallaz.qrcode.activities.Mycard
import com.gwallaz.qrcode.activities.Sms
import com.gwallaz.qrcode.activities.Telephone
import com.gwallaz.qrcode.activities.Text
import com.gwallaz.qrcode.activities.Website
import com.gwallaz.qrcode.activities.Wifi
import java.util.ArrayList


class CreateFragment : Fragment(),Adapter.OnItemClickListener  {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create, container, false)
        val recyclerView : RecyclerView = root.findViewById(R.id.recyclerView)

        val data = ArrayList<Model>()
        data.add(Model(R.drawable.baseline_clipboard,"Clipboard"))
        data.add(Model(R.drawable.baseline_link,"Website"))
        data.add(Model(R.drawable.wifi,"Wi-Fi"))
        data.add(Model(R.drawable.baseline_text,"Text"))
        data.add(Model(R.drawable.baseline_contact,"Contact"))
        data.add(Model(R.drawable.baseline_phone,"Tel"))
        data.add(Model(R.drawable.baseline_email,"SMS"))
        data.add(Model(R.drawable.baseline_credit_card,"My Card"))
        data.add(Model(R.drawable.baseline_calendar_month,"Calender"))

        val adapter = Adapter(data,this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        return root
    }

    override fun onItemClicked(position: Int) {


        when(position){
            0 -> startActivity(Intent(activity?.applicationContext,Clipboard::class.java))
            1 -> startActivity(Intent(activity?.applicationContext,Website::class.java))
            2 -> startActivity(Intent(requireActivity(),Wifi::class.java))
            3 -> startActivity(Intent(requireActivity(),Text::class.java))
            4 -> startActivity(Intent(requireContext(), Contact::class.java))
            5 -> startActivity(Intent(requireActivity(),Telephone::class.java))
            6 -> startActivity(Intent(requireActivity(), Sms::class.java))
            7 -> startActivity(Intent(requireActivity(),Mycard::class.java))
            8 -> startActivity(Intent(requireActivity(),Calender::class.java))
        }
    }
}
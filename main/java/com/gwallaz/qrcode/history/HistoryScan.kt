package com.gwallaz.qrcode.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwallaz.qrcode.database.ViewModelDatabase
import com.gwallaz.qrcode.databinding.FragmentHistoryScanBinding


class HistoryScan : Fragment(),ScanRecyclerViewAdapter.HistoryScanOnclickListener {
    private var _binding : FragmentHistoryScanBinding? = null
    private  val binding get() = _binding!!
    private lateinit var viewModelDatabase: ViewModelDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryScanBinding.inflate(inflater, container, false)


        val adapter = ScanRecyclerViewAdapter(this)
        val scanRecyclerView = binding.historyScanRecyclerView
        scanRecyclerView.adapter = adapter
        scanRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        viewModelDatabase = ViewModelProvider(requireActivity())[ViewModelDatabase::class.java]



        return binding.root
    }

    override fun onClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete item")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setPositiveButton("Ok"){_,_ ->

        }
        builder.setNegativeButton("Cancel"){
            _,_ ->
        }
        builder.create().show()

    }


}
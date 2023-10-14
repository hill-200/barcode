package com.gwallaz.qrcode.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwallaz.qrcode.database.Repositorydatabase
import com.gwallaz.qrcode.database.TableCreate
import com.gwallaz.qrcode.database.ViewModelDatabase
import com.gwallaz.qrcode.databinding.FragmentHistoryCreateBinding


class HistoryCreate : Fragment(),HistoryCreateAdapter.CreateOnClickListener {
    private  var _binding : FragmentHistoryCreateBinding? = null
    private  val binding get() = _binding!!
    private lateinit var viewModelDatabase: ViewModelDatabase
    private lateinit var adapter : HistoryCreateAdapter
    private lateinit var tableCreate: TableCreate
    private lateinit var viewHolder : HistoryCreateAdapter.CreateViewHolder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryCreateBinding.inflate(inflater,container,false)

        adapter = HistoryCreateAdapter(this)


        val createRecyclerView = binding.ucreateRecyclerView
        createRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        createRecyclerView.adapter = adapter

        viewModelDatabase = ViewModelProvider(requireActivity())[ViewModelDatabase::class.java]
        viewModelDatabase.getAllData.observe(viewLifecycleOwner, Observer { user ->
        adapter.setData(user)
        })

        return binding.root
    }



    override fun onClick(position: Int) {
        val userToDelete=TableCreate(1,"John","Long",1)


        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Delete item")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setPositiveButton("Ok"){_,_ ->


            viewModelDatabase.deleteRecord(userToDelete)

        }
        builder.setNegativeButton("Cancel"){
                _,_ ->
        }
        builder.create().show()

    }
}
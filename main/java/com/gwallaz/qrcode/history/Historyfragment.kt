package com.gwallaz.qrcode.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.database.ViewModelDatabase
import com.gwallaz.qrcode.databinding.FragmentHistoryfragmentBinding


class Historyfragment : Fragment() {
    private var _binding: FragmentHistoryfragmentBinding? = null
    private val binding get() = _binding!!
    private val tabNames = arrayOf("CREATE")
    private lateinit var viewModelDatabase: ViewModelDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryfragmentBinding.inflate(inflater, container, false)

        viewModelDatabase = ViewModelProvider(requireActivity())[ViewModelDatabase::class.java]
         val pager = binding.viewPager2

        val tablayout = binding.tabLayout
        val adapter = Fragmentstateadapter(childFragmentManager, lifecycle)
        pager.adapter = adapter
        pager.isSaveEnabled = false

        TabLayoutMediator(tablayout, pager) { tab,position->
            tab.text = tabNames[position]


        }.attach()
        deleteAll()


        return binding.root
    }

    private fun deleteAll(){


        val delete = binding.deleteButton
        delete.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            with(builder){
                setTitle("Delete everything")
                setMessage("Are you sure you want to delete everything?")
                setPositiveButton("Ok"){_,_->
                    viewModelDatabase.deleteall()
                }
                setNegativeButton("Cancel"){_,_ ->}

            }
            builder.create().show()

        })
    }



}
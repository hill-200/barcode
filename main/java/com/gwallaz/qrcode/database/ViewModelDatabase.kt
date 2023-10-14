package com.gwallaz.qrcode.database


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

 class ViewModelDatabase(application: Application):AndroidViewModel(application) {



     val getAllData : LiveData<List<TableCreate>>

    private val repository : Repositorydatabase

    init {

        val createDao = CreateDatabase.getDatabase(application).getCreateDao()
        repository = Repositorydatabase(createDao)
        getAllData = repository.getAllData

    }

     fun deleteRecord(tableCreate: TableCreate){
               viewModelScope.launch(Dispatchers.IO){
          repository.deleteRecord(tableCreate)
      }

    }

    fun upsertRecord(tableCreate: TableCreate){

        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertRecord(tableCreate)
        }
    }

     fun deleteall(){
         viewModelScope.launch (Dispatchers.IO){
             repository.deleteAllCreateData()
         }

     }




}
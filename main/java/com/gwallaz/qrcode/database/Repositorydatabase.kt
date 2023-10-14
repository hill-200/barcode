package com.gwallaz.qrcode.database


import androidx.lifecycle.LiveData

class Repositorydatabase (private val createDao:CreateDao){

    val getAllData: LiveData<List<TableCreate>> = createDao.getAllData()


    suspend fun upsertRecord(tableCreate: TableCreate){
            createDao.upsertRecord(tableCreate)
    }

    suspend fun deleteRecord(tableCreate: TableCreate){
            createDao.deleteRecord(tableCreate)
    }

    suspend fun deleteAllCreateData(){
        createDao.deleteAllCreateData()
    }




}
package com.gwallaz.qrcode.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface CreateDao {

    @Upsert
    suspend fun upsertRecord(tableCreate: TableCreate)

    @Delete
    suspend fun deleteRecord(tableCreate: TableCreate)

    @Query("SELECT * FROM tableCreate ORDER BY id ASC")
    fun getAllData(): LiveData<List<TableCreate>>

    @Query("DELETE FROM tableCreate")
    suspend fun deleteAllCreateData()




}
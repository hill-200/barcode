package com.gwallaz.qrcode.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tablescan(
    @PrimaryKey(autoGenerate = true)
   val id : Int,
   val name : String,
   val type : String,
   val image : Int
)


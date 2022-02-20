package com.astery.weatherapp.model.pogo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class City(@PrimaryKey val id:Int, val name:String, val longitude:Double, val latitude:Double):Parcelable
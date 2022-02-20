package com.astery.weatherapp.model.pogo

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.NO_ACTION
import androidx.room.ForeignKey.SET_NULL
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class City(
    @PrimaryKey @SerializedName("Key") val id: String, @SerializedName("LocalizedName") val name: String,
    @Embedded @SerializedName("Country") val country: Country,
    val isFavourite: Boolean? = false
):Parcelable

@Parcelize
data class Country(
    @SerializedName("ID") @ColumnInfo(name = "country_id") val id: String,
    @SerializedName("LocalizedName") @ColumnInfo(name = "country_name") val name: String,
):Parcelable

package com.astery.weatherapp.storage.preferences

/**
 * value that can be stored in sharedPreferences. Mainly enum
 * */
sealed class PreferenceEntity<T>(val value:T, val default:T? = null){
    val name:String = this::class.simpleName!!
}
/** last found with geolocation city */
class LastCityId(value:String?): PreferenceEntity<String>(value?: "")
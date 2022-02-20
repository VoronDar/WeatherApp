package com.astery.weatherapp.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

/** Transporter between sharedPreferences and others
 * */
class Preferences @Inject constructor(private var ctx: Context) {

    private fun getPref(): SharedPreferences {
        return ctx.getSharedPreferences("prefs", 0)
    }

    // MARK: getters
    fun get(pref: PreferenceEntity<Long>) = getPref().getLong(pref.name, pref.default ?: 0)
    fun get(pref: PreferenceEntity<Float>) = getPref().getFloat(pref.name, pref.default ?: 0f)
    fun get(pref: PreferenceEntity<Int>) = getPref().getInt(pref.name, pref.default ?: 0)
    fun get(pref: PreferenceEntity<String>) = getPref().getString(pref.name, pref.default ?: "")
    fun get(pref: PreferenceEntity<Boolean>) =
        getPref().getBoolean(pref.name, pref.default ?: false)

    // MARK: setters
    @JvmName("long")
    fun set(pref: PreferenceEntity<Long>) = getPref().edit { putLong(pref.name, pref.value) }

    @JvmName("float")
    fun set(pref: PreferenceEntity<Float>) = getPref().edit { putFloat(pref.name, pref.value) }

    @JvmName("int")
    fun set(pref: PreferenceEntity<Int>) = getPref().edit { putInt(pref.name, pref.value) }

    @JvmName("string")
    fun set(pref: PreferenceEntity<String>) = getPref().edit { putString(pref.name, pref.value) }

    @JvmName("boolean")
    fun set(pref: PreferenceEntity<Boolean>) =
        getPref().edit { putBoolean(pref.name, pref.value) }

}


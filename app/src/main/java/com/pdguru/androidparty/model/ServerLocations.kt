package com.pdguru.androidparty.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@JsonClass(generateAdapter = true)
data class ServerLocations(val name: String, val distance: String):Parcelable

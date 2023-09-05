package se.linerotech.module202.project1.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCities(
    @StringRes val name: Int,
    @DrawableRes val image: Int,
) : Parcelable

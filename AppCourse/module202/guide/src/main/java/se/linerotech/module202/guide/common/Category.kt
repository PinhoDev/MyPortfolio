package se.linerotech.module202.guide.common

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: String,
    @StringRes val name: Int,
    @DrawableRes val image: Int,
) : Parcelable

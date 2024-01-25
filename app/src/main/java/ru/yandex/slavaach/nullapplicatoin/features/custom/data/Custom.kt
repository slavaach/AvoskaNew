package ru.yandex.slavaach.nullapplicatoin.features.custom.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Custom")
data class Custom(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("home")
    val home: Long,
    @Expose
    @SerializedName("sale")
    val sale: Long = -1,
    @Expose
    @SerializedName("isBuy")
    val isBuy: Boolean = false,
) : Parcelable

data class CustomWithText(
    val id: Long,
    var name: String,
    var home: Long,
    var sale: Long = -1,
    val salename: String? = "",
    val homename: String? = "",
    val isBuy: Boolean = false,
    val saleOrder: Int = 0,
)

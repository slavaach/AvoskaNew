package ru.yandex.slavaach.nullapplicatoin.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "SettingHome")
data class SettingHome(
    @PrimaryKey val id: Long,
    @Expose
    @SerializedName("idHome")
    var idHome: Long,
    @Expose
    @SerializedName("idBuyHome")
    var idBuyHome: List<Long>,
) : Parcelable

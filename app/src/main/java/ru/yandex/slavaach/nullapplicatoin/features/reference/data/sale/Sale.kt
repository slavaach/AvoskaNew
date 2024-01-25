package ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceDt

@Parcelize
@Entity(tableName = "Sale")
data class Sale(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    @Expose
    @SerializedName("name")
    override var name: String,
    @Expose
    @SerializedName("order")
    override var order: Int,
) : ReferenceDt, Parcelable

package ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHomes(cards: List<Sale>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHome(cards: Sale)

    @Query("DELETE FROM Sale where id = :idDel")
    fun deleteHome(idDel: Long)

    @Query("SELECT * FROM Sale order by  id Desc ")
    fun findAllHomes(): List<Sale>
}

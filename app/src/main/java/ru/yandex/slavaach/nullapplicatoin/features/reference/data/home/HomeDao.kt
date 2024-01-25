package ru.yandex.slavaach.nullapplicatoin.features.reference.data.home

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHomes(cards: List<Home>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHome(cards: Home)

    @Query("DELETE FROM Home where id = :idDel")
    fun deleteHome(idDel: Long)

    @Query("SELECT * FROM Home order by   id Desc ")
    fun findAllHomes(): List<Home>
}

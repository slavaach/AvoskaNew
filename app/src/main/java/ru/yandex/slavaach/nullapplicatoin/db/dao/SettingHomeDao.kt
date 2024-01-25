package ru.yandex.slavaach.nullapplicatoin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.yandex.slavaach.nullapplicatoin.db.entity.SettingHome

@Dao
interface SettingHomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(cards: List<SettingHome>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cards: SettingHome)

    @Query("DELETE FROM SettingHome where id = :idDel")
    fun delete(idDel: Long)

    @Query("SELECT * FROM SettingHome where id = 0 ")
    fun find(): SettingHome?
}

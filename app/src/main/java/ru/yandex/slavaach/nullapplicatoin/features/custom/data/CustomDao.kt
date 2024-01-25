package ru.yandex.slavaach.nullapplicatoin.features.custom.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CustomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(cards: List<Custom>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cards: Custom)

    @Query("DELETE FROM Custom where id = :idDel")
    fun delete(idDel: Long)

    @Query("DELETE FROM Custom where isBuy = 1")
    fun deleteBuy()

    @Query("SELECT * FROM Custom where home =:home order by  id Desc ")
    fun findAll(home: Long): List<Custom>

    @Query(
        "SELECT cs.id , cs.name , cs.home , cs.sale , s.name as salename , h.name as homename  , cs.isBuy  , s.[order] as saleOrder FROM Custom cs left join Sale s on s.id = cs.sale left join Home h on h.id = cs.home where cs.home = :home  order by  cs.id Desc "
    )
    fun findAllWithText(home: Long): List<CustomWithText>

    @Query(
        "SELECT cs.id , cs.name , cs.home , cs.sale , s.name as salename , h.name as homename , cs.isBuy , s.[order] as saleOrder FROM Custom cs left join Sale s on s.id = cs.sale left join Home h on h.id = cs.home where cs.home IN (:home)  order by  cs.id Desc "
    )
    fun findAllWithText(home: List<Long>): List<CustomWithText>
}

package com.example.sumatifroom_anggita_eka_damayanti_genap.room

import androidx.room.*

@Dao
interface barangDAO {
    @Insert
    fun addtb_barang(tbBarang: tb_barang)
    @Update
    fun updatetb_barang(tbBarang: tb_barang)
    @Delete
    fun deletetb_barang(tbBarang: tb_barang)
    @Query("SELECT * FROM tb_barang")
    fun tampilsemua() : List<tb_barang>
    @Query("SELECT * FROM tb_barang WHERE id=:idbarang")
    fun getTampilid(idbarang: Int): List<tb_barang>
}
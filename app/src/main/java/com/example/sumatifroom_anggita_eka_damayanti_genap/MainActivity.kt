package com.example.sumatifroom_anggita_eka_damayanti_genap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.Constant
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.codepelita
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.tb_barang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy {codepelita(this)}
    private lateinit var BarangAdapter: barangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerview()
    }

    override fun onStart() {
        super.onStart()
        loadBarang()
    }
    fun loadBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.brgDAO().tampilsemua()
            Log.d("MainActivity", "dbResponse: $barang")
            withContext(Dispatchers.Main){
                BarangAdapter.setData(barang)
            }
        }
    }
    private fun halEdit(){
        btinput.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }
    fun intentEdit(idbarang: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_id", idbarang)
                .putExtra("intent_type", intentType)
        )
    }
    private fun setupRecyclerview(){
        BarangAdapter = barangAdapter(arrayListOf(),object :barangAdapter.OnadapterListener {
            override fun onClick(tbBarang: tb_barang) {
                intentEdit(tbBarang.id, Constant.TYPE_READ)
            }

            override fun onUpdate(tbBarang: tb_barang) {
                intentEdit(tbBarang.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbBarang: tb_barang) {
                deleteDialog(tbBarang)
            }
        })
        listnamabarang.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = BarangAdapter
        }
    }
    private fun deleteDialog(tbBarang: tb_barang) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${tbBarang.id}")
            setNegativeButton("Batal") {dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.brgDAO().deletetb_barang(tbBarang)
                    loadBarang()
                    }
                }
            }
            alertDialog.show()
        }
    }
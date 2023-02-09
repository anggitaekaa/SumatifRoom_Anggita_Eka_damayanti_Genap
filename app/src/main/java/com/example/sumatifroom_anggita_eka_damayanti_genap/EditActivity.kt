package com.example.sumatifroom_anggita_eka_damayanti_genap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.Constant
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.codepelita
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.tb_barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { codepelita(this) }
    private var idbarang: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        simpandata()
        setupView()
        idbarang = intent.getIntExtra("intent_barang", 0)

    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType= intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btupdate.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                btsave.visibility = View.GONE
                btupdate.visibility = View.GONE
                getTampilid()
            }
            Constant.TYPE_UPDATE -> {
                btsave.visibility = View.GONE
                getTampilid()
            }
        }
    }
    fun simpandata(){
        btsave.setOnClickListener{
            CoroutineScope(Dispatchers.IO) .launch {
                db.brgDAO().addtb_barang(
                    tb_barang(et_id.text.toString().toInt(), et_namabrg.text.toString(),
                        et_harga.text.toString().toInt(), et_qty.text.toString().toInt())
                )
                finish()
            }
        }
        btupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO) .launch {
                db.brgDAO().updatetb_barang(
                    tb_barang(et_id.text.toString().toInt(), et_namabrg.text.toString(),
                    et_harga.text.toString().toInt(), et_qty.text.toString().toInt()
                    )
                )
                finish()
            }
        }
    }
    fun getTampilid(){
        idbarang = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val tampil = db.brgDAO().getTampilid(idbarang)[0]
            val dataId : String = tampil.id.toString()
            val harga : String = tampil.harga.toString()
            val dataqty : String =  tampil.qty.toString()
            et_id.setText(dataId)
            et_namabrg.setText(tampil.nama_barang)
            et_harga.setText(harga)
            et_qty.setText(dataqty)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
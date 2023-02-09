package com.example.sumatifroom_anggita_eka_damayanti_genap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sumatifroom_anggita_eka_damayanti_genap.room.tb_barang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*
import kotlinx.android.synthetic.main.activity_edit.view.*

class barangAdapter (private val barang: ArrayList<tb_barang>, private val listener: OnadapterListener):RecyclerView.Adapter<barangAdapter.barangViewHolder>(){
    class barangViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): barangViewHolder {
        return barangViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_barang_adapter,parent, false)
        )
    }

    override fun onBindViewHolder(holder: barangViewHolder, position: Int) {
        val brg = barang[position]
        holder.view.tv_Id.text = brg.id.toString()
        holder.view.tv_namabrg.text = brg.nama_barang
        holder.view.tv_namabrg.setOnClickListener{
            listener.onClick(brg)
        }
        holder.view.imedit.setOnClickListener{
            listener.onUpdate(brg)
        }
        holder.view.imdelete.setOnClickListener{
            listener.onDelete(brg)
        }
    }

    override fun getItemCount(): Int = barang.size


    fun setData(list: List<tb_barang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }
    interface OnadapterListener{
        fun onClick(tbBarang: tb_barang)
        fun onUpdate(tbBarang: tb_barang)
        fun onDelete(tbBarang: tb_barang)
    }
}
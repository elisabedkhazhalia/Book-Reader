package com.example.gmerto.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gmerto.R
import com.example.gmerto.model.PdfData

class LibraryAdapter(private val arrayOfPdf: ArrayList<PdfData>, private val listener: OnItemClickListener): RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val pdfTitle: TextView = view.findViewById(R.id.titlePdf)
        init {
            pdfTitle.setOnClickListener {
                val position: Int = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return  ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPdf = arrayOfPdf[position]
        holder.pdfTitle.text = currentPdf.name
    }
    override fun getItemCount(): Int = arrayOfPdf.count()
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}
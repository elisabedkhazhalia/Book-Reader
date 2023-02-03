package com.example.gmerto.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.gmerto.R
import com.example.gmerto.model.PdfData
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.net.URI

class PdfViewFragment: Fragment(R.layout.fragment_pdf_view) {

    private lateinit var pdfView: PDFView
    private lateinit var databaseReference: DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads").child(auth.currentUser!!.uid)
        pdfView = view.findViewById(R.id.pdfView)
        val pdfId = arguments?.getString("id")?: "_"

        databaseReference.child(pdfId).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val pdf = snapshot.getValue(PdfData::class.java)?: return
                val reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdf.url!!)
                reference.getBytes(50000000)
                    .addOnSuccessListener { bytes ->
                        pdfView.fromBytes(bytes).load()
                    }.addOnFailureListener {
                    }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
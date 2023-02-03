package com.example.gmerto.FragmentsProfile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmerto.R
import com.example.gmerto.adapter.LibraryAdapter
import com.example.gmerto.model.PdfData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LibraryFragment: Fragment(R.layout.fragment_library), LibraryAdapter.OnItemClickListener {

    private lateinit var recyclerViewPdfs: RecyclerView
    private lateinit var arrayOfPdfs: ArrayList<PdfData>
    private lateinit var databaseReference: DatabaseReference
    private var auth = FirebaseAuth.getInstance()
    private lateinit var buttonUpload: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonUpload = view.findViewById(R.id.buttonUpload)
        recyclerViewPdfs = view.findViewById(R.id.booksList)
        val layoutManger = LinearLayoutManager(activity)
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads").child(auth.currentUser!!.uid)
        recyclerViewPdfs.layoutManager = layoutManger
        arrayOfPdfs = arrayListOf()
        recyclerViewPdfs.adapter = LibraryAdapter(arrayOfPdfs, this@LibraryFragment)
        registerListener()
        loadData()
    }

    private fun loadData() {
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val currentPdf = dataSnapshot.getValue(PdfData::class.java)?: return
                    arrayOfPdfs.add(currentPdf)
                }
                recyclerViewPdfs.adapter = LibraryAdapter(arrayOfPdfs, this@LibraryFragment)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun registerListener() {
        buttonUpload.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_secondFragment_to_fragmentUploadPdf)
        }
    }
    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        val currentPdfId = arrayOfPdfs[position].id
        bundle.putString("id", currentPdfId)
        Navigation.findNavController(requireView()).navigate(R.id.action_secondFragment_to_pdfViewFragment, bundle)
    }

}

package com.example.gmerto.Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gmerto.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Dictionary

class FragmentUploadPdf: Fragment(R.layout.fragment_upload_pdf) {

    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private var auth = FirebaseAuth.getInstance()
    private lateinit var editTextName: EditText
    private lateinit var buttonUpload: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads").child(auth.currentUser!!.uid)
        buttonUpload = view.findViewById(R.id.buttonUpload)
        editTextName = view.findViewById(R.id.editTextName)
        registerListener()
    }

    private fun registerListener() {
        buttonUpload.setOnClickListener {
            selectFile()
        }
    }
    private fun selectFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            uploadFile(data.data!!)
        }
    }
    private fun uploadFile(data: Uri) {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        val reference = storageReference.child("Uploads/" + auth.currentUser!!.uid + "/" + System.currentTimeMillis() + ".pdf")
        reference.putFile(data).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { url ->
                val id = databaseReference.push().key!!
                val pdfData = mapOf("id" to id,"name" to editTextName.text.trim().toString(), "url" to url.toString())
                databaseReference.child(id).setValue(pdfData).addOnSuccessListener {
                    Toast.makeText(activity, "File Uploaded!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                    requireActivity().onBackPressed()
                }
            }
        }.addOnProgressListener { snapshot ->
            val progress: Double = (100.0 * snapshot.bytesTransferred/ snapshot.totalByteCount)
            progressDialog.setMessage("Uploaded: $progress%")
        }
    }

}
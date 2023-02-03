package com.example.gmerto.FragmentsProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gmerto.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException


class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {
    private var auth = FirebaseAuth.getInstance()
    private lateinit var editTextNewPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonChangePassword: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerListener()
    }

    private fun init(view: View) {
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword)
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
        buttonChangePassword = view.findViewById(R.id.buttonChangePassword)
    }

    private fun registerListener() {
        buttonChangePassword.setOnClickListener {
            val newPassword = editTextNewPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (newPassword.trim() != confirmPassword.trim()) {
                Toast.makeText(activity, "Confirm password failed.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPassword.isNotEmpty() && newPassword==confirmPassword){
            auth.currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Toast.makeText(activity, "Password changed.", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthRecentLoginRequiredException) {
                        Toast.makeText(activity, "Please re-login and try again.", Toast.LENGTH_SHORT).show()
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(activity, "Change Password failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }}
    }
}
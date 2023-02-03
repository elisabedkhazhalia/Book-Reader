package com.example.gmerto.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.gmerto.R
import com.google.firebase.auth.FirebaseAuth


class ForgotPassFragment : Fragment() {
    private lateinit var resetPass: Button
    private lateinit var loginReset: TextView
    private lateinit var sigupReset : TextView
    private lateinit var emailInp: EditText
    private var auth=FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_forgot_pass, container, false)
        resetPass  = view.findViewById(R.id.reset_button)
        loginReset = view.findViewById(R.id.textView3)
        sigupReset = view.findViewById(R.id.textView5)
        emailInp = view.findViewById(R.id.emailInp)
        resetPass.setOnClickListener {
            val email = emailInp.text.toString()
            if (email.isNotEmpty()){
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Check email.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Can't reset password.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        sigupReset.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_forgotPassFragment_to_registrationFragment)
        }
        loginReset.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_forgotPassFragment_to_firstFragment2)
        }
        return view
    }


}
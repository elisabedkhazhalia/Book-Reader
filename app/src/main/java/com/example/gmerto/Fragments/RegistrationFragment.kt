package com.example.gmerto.Fragments

import android.os.Bundle
import android.util.Log
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


class RegistrationFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var registerBtn: Button
    private lateinit var loginRegistracia: TextView
    private lateinit var email: EditText
    private lateinit var passwrod: EditText
    private lateinit var confirmPasswordInp: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_registration, container, false)
        registerBtn=view.findViewById(R.id.registerBtn)
        loginRegistracia=view.findViewById(R.id.textView2)

        loginRegistracia.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_firstFragment2)
        }
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        email = view.findViewById(R.id.emailInp)
        confirmPasswordInp=  view.findViewById(R.id.confirmPasswordInp)
        passwrod = view.findViewById(R.id.passwordInp)
        registerListener(view)
    }

    private fun registerListener(view: View) {
        registerBtn.setOnClickListener {

            val email = email.text.toString()
            val password = passwrod.text.toString()
            val confirmPass = confirmPasswordInp.text.toString()

            if  (email.isNotEmpty() && password==confirmPass && password.isNotEmpty() && confirmPass.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_firstFragment2)
                    } else {
                        Toast.makeText(activity, "Registration failed.", Toast.LENGTH_SHORT).show()

                    }
            }


            }else{
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
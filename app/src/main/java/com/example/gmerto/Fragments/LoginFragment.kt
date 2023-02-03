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

class LoginFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var knopka: Button
    private lateinit var forgotPass : TextView
    private lateinit var signUp : TextView
    private lateinit var email: EditText
    private lateinit var passwrod: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        email = view.findViewById(R.id.emailInp)
        passwrod = view.findViewById(R.id.passwordInp)
        knopka = view.findViewById(R.id.button)
        forgotPass = view.findViewById(R.id.ForgotPass)
        signUp = view.findViewById(R.id.SignUp)

        knopka.setOnClickListener {
            val email = email.text.toString()
            val password = passwrod.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Navigation.findNavController(view).navigate(R.id.action_firstFragment2_to_secondFragment)
                        } else {
                            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(context, "Account doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }
        forgotPass.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_firstFragment2_to_forgotPassFragment)
        }
        signUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_firstFragment2_to_registrationFragment)
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firebaseAuth.currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_firstFragment2_to_secondFragment)
        }
    }
}




package com.example.gmerto.FragmentsProfile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import com.example.gmerto.R
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var changePass: Button
    private lateinit var gasvla: Button
    private lateinit var notepad: Button
    private lateinit var toggleButton: MaterialButtonToggleGroup

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        toggleButton = view.findViewById(R.id.toggleButton)
        changePass = view.findViewById(R.id.changePass)
        gasvla = view.findViewById(R.id.gasvla)
        changePass.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_changePasswordFragment2)
        }

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerListener(view)
    }

    private fun init() {

        val sharedPreference = activity?.getSharedPreferences("options", AppCompatActivity.MODE_PRIVATE)!!
        val darkMode = sharedPreference.getBoolean("darkMode", false)

        toggleButton.isSingleSelection = true

        if (darkMode) {
            toggleButton.check(R.id.dark)
        } else {
            toggleButton.check(R.id.light)
        }

        toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (isChecked) {
                if (checkedId == R.id.dark) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPreference.edit().putBoolean("darkMode", true).apply()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPreference.edit().putBoolean("darkMode", false).apply()
                }
            }
        }
    }

    private fun registerListener(view: View) {
        gasvla.setOnClickListener {
            firebaseAuth.signOut()
            Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_firstFragment2)
        }
    }

}
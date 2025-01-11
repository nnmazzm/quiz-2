package com.example.mobileapplications22.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mobileapplications22.MainFragment
import com.example.mobileapplications22.R
import com.example.mobileapplications22.databinding.FragmentSignUpBinding
import com.example.mobileapplications22.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding

    private val auth = Firebase.auth

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding){
        btnSignUp.setOnClickListener {
            val email = etEmailSignUp.text.toString()
            val password = etPasswordSignUp.text.toString()

            if (email.isEmpty() || password.isEmpty() || password.contains(' ') || password.length < 5){
                Toast.makeText(
                    requireContext(),
                    "enter correct email & password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    loadFragment(MainFragment.newInstance())

                    val uid = auth.currentUser!!.uid
                    val userInfo = User(uid, "https://t4.ftcdn.net/jpg/03/59/58/91/360_F_359589186_JDLl8dIWoBNf1iqEkHxhUeeOulx0wOC5.jpg")

                    FirebaseDatabase.getInstance().getReference("USER").child(uid).setValue(userInfo)

                    Toast.makeText(requireContext(), task.result.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvGoToSignInSignUp.setOnClickListener {
            loadFragment(SignInFragment.newInstance())
        }
    }

    private fun loadFragment(f: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.placeHolder, f).addToBackStack(null).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}
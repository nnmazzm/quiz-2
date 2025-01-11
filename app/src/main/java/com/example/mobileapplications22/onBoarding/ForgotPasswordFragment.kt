package com.example.mobileapplications22.onBoarding

import android.os.Bundle
import android.os.ParcelFormatException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mobileapplications22.R
import com.example.mobileapplications22.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    lateinit var binding: FragmentForgotPasswordBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        btnSendForgotPassword.setOnClickListener {

            val email = etEmailForgotPassword.text.toString()

            if (email.isEmpty()){
                Toast.makeText(requireContext(), "email is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "email sent", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction().replace(R.id.placeHolder,SignInFragment.newInstance()).commit()
                } else {
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgotPasswordFragment()
    }
}
package com.example.mobileapplications22.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mobileapplications22.MainFragment
import com.example.mobileapplications22.R
import com.example.mobileapplications22.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    lateinit var binding : FragmentSignInBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding){
        tvForgotPasswordSignIn.setOnClickListener {
            loadFragment(ForgotPasswordFragment.newInstance())
        }

        btnSignIn.setOnClickListener {
            val email = etEmailSignIn.text.toString()
            val password = etPasswordSignIn.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(), "email or password is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    loadFragment(MainFragment.newInstance())
                } else {
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        tvGoToSignUpSignIn.setOnClickListener {
            loadFragment(SignUpFragment.newInstance())
        }

    }

    private fun loadFragment(f: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.placeHolder,f).addToBackStack(null).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}
package com.example.mobileapplications22.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mobileapplications22.R
import com.example.mobileapplications22.models.Story
import com.example.mobileapplications22.adapters.RcViewStoryAdapter
import com.example.mobileapplications22.adapters.ViewPagerAdapter
import com.example.mobileapplications22.databinding.FragmentProfileBinding
import com.example.mobileapplications22.models.User
import com.example.mobileapplications22.onBoarding.SignInFragment
import com.example.mobileapplications22.profile.fragments.BlankFragment1
import com.example.mobileapplications22.profile.fragments.BlankFragment2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private lateinit var storyAdapter: RcViewStoryAdapter

    private val auth = Firebase.auth
    private val auth2 = FirebaseAuth.getInstance()

    val db = FirebaseDatabase.getInstance().getReference("USER")

    private val fList = listOf(
        BlankFragment1.newInstance(), BlankFragment2.newInstance()
    )

    private val tList = listOf("POSTS","TAGGED")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRV()
        initVP()
    }

    private fun init() = with(binding){
        db.child(auth2.currentUser!!.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(User::class.java) ?: return
                tvUserName.text = userInfo.uid
                Glide.with(requireContext()).load(userInfo.avatar).into(ivMainUserAvatar)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })

        btnLogOut.setOnClickListener {
            auth.signOut()
            parentFragmentManager.beginTransaction().replace(R.id.placeHolder,SignInFragment.newInstance()).commit()
        }
    }

    private fun initRV() = with(binding){
        rcViewProfile.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        storyAdapter = RcViewStoryAdapter()
        rcViewProfile.adapter = storyAdapter
        val list = listOf(
            Story("","qweyuiop"),
            Story("","09876543"),
            Story("","oiuytrew")
        )
        storyAdapter.submitList(list)
    }

    private fun initVP() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){ tab, position ->
            tab.text = tList[position]
        }.attach()
    }

    companion object {
          @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
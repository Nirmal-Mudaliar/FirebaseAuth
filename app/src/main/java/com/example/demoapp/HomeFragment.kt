package com.example.demoapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapp.databinding.FragmentHomeBinding
import com.example.demoapp.feature_auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private var perCollRef = Firebase.firestore.collection("users")


    private val onlineAdapter = OnlineAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.rcyHome.layoutManager = LinearLayoutManager(requireContext())
        binding.rcyHome.adapter = onlineAdapter

        retriveData()




        return binding.root
    }

    private fun retriveData() = CoroutineScope(Dispatchers.IO).launch {
        val onlineList = ArrayList<User>()
        val querySnapshot = perCollRef
            .whereEqualTo("online", true)
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val user = document.toObject<User>()
            if (user != null) {
                onlineList.add(user)
            }
        }
        withContext(Dispatchers.Main) {
            onlineAdapter.setData(onlineList)
        }
    }

}
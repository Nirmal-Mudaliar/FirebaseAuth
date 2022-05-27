package com.example.demoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.demoapp.databinding.FragmentProfileBinding
import com.example.demoapp.feature_auth.LogIn
import com.example.demoapp.feature_auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.model.DocumentCollections
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val personCollRef = Firebase.firestore.collection("users")
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater)



        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val uid = user?.uid
        Log.d("User id ---", uid.toString())
        retriveUser(uid.toString())

        if (uid != null) {
            listenerForLogOut(uid)
        }
        listenerForBackBtn()
        return binding.root




    }

    private fun listenerForBackBtn() {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
    }

    private fun listenerForLogOut(uid: String) {
        binding.btnLogOut.setOnClickListener {

            personCollRef.document(uid).update("online", false)
                .addOnSuccessListener {
                    Log.d("Not Active", "User id: ${uid}")
                }
                .addOnFailureListener {
                    Log.d("Failed update", "User id: ${uid}")
                }

            firebaseAuth.signOut()
            val intent = Intent(activity, LogIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun retriveUser(uid: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshots = personCollRef
                .whereEqualTo("uid", uid)
                .get().await()
            val user = querySnapshots.documents[0].toObject<User>()
            withContext(Dispatchers.IO) {
                binding.tvFirstName.text = user?.firstName
                binding.tvLastName.text = user?.lastName
                binding.tvEmail.text = user?.email
                binding.tvPhoneNo.text = user?.phoneNo
            }

        }catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                Log.d("Error --- ", e.message.toString())
            }
        }
    }


}
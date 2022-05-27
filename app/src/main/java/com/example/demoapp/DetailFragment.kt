package com.example.demoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.demoapp.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)

        binding.tvFirstName.text = args.currentItem.firstName
        binding.tvLastName.text = args.currentItem.lastName
        binding.tvEmail.text = args.currentItem.email
        binding.tvPhoneNo.text = args.currentItem.phoneNo

        listenerForBackBtn()



        return binding.root
    }

    private fun listenerForBackBtn() {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }


}
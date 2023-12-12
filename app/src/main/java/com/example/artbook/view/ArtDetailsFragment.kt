package com.example.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbook.R
import com.example.artbook.databinding.FragmentArtdetailsBinding
import com.example.artbook.util.Status
import com.example.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(private val glide:RequestManager)  :Fragment(R.layout.fragment_artdetails) {

    private var fragmentBinding : FragmentArtdetailsBinding?= null

    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var binding = FragmentArtdetailsBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscribeToObservers()

        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageAPIFragment())
        }

        val callBack = object : OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.buttonSave.setOnClickListener {
            viewModel.makeArt(name = binding.nameText.text.toString(),
            artistName = binding.artistText.text.toString(),
            year = binding.yearText.text.toString())
        }


    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner,{url->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()

                }
                Status.LOADING->{

                }
            }
        })

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}
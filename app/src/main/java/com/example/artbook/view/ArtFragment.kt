package com.example.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.artbook.R
import com.example.artbook.adapter.ArtRvAdapter
import com.example.artbook.databinding.FragmentArtsBinding
import com.example.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artrvAdapter:ArtRvAdapter
) : Fragment(R.layout.fragment_arts) {


    private var swipeCallBack = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artrvAdapter.arts.get(layoutPosition)
            viewModel.deleteArt(selectedArt)

        }

    }

    private var fragmentBinding : FragmentArtsBinding ? = null
    lateinit var viewModel:ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val binding =  FragmentArtsBinding.bind(view)
        fragmentBinding = binding
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscribeToObservers()
        binding.rvArt.adapter = artrvAdapter
        binding.rvArt.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.rvArt)

        binding.fab.setOnClickListener{
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }

    }

    fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner,{
            artrvAdapter.arts = it
        })
    }


    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}
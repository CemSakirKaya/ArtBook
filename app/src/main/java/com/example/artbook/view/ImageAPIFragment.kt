package com.example.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.artbook.R
import com.example.artbook.adapter.ImageRvAdapter
import com.example.artbook.databinding.FragmentImageapiBinding
import com.example.artbook.roomdb.Art
import com.example.artbook.util.Status
import com.example.artbook.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(
    private val imageRvAdapter: ImageRvAdapter
):Fragment(R.layout.fragment_imageapi) {

    lateinit var viewModel: ArtViewModel
    private var fragmentBinding : FragmentImageapiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentImageapiBinding.bind(view)
        fragmentBinding = binding
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeToObserver()



        var job : Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
              delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }

            }

        }

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding.ImageRecyclerView.adapter= imageRvAdapter
        binding.ImageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        imageRvAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    fun subscribeToObserver(){
        viewModel.imageList.observe(requireActivity(),{
            when(it.status){
                Status.SUCCESS->{
                   val urls = it.data?.images?.map {imageResult->
                        imageResult.previewURL
                   }
                    imageRvAdapter.images = urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility = View.GONE

                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE

                }

                Status.LOADING->{

                    fragmentBinding?.progressBar?.visibility = View.VISIBLE

                }

            }
        })
    }


    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}
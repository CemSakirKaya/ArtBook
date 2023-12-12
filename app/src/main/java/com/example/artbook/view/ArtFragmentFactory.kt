package com.example.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.artbook.adapter.ArtRvAdapter
import com.example.artbook.adapter.ImageRvAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager,
    private val artRvAdapter: ArtRvAdapter,
    private val imageRvAdapter: ImageRvAdapter
) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

     return   when(className){
         ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
         ArtFragment::class.java.name -> ArtFragment(artRvAdapter)
         ImageAPIFragment::class.java.name -> ImageAPIFragment(imageRvAdapter)
         else -> super.instantiate(classLoader, className)
        }
    }

}
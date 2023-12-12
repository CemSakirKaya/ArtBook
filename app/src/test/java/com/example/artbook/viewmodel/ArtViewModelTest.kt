package com.example.artbook.viewmodel

import android.net.http.UrlRequest.Status
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.annotation.ExperimentalTestApi
import com.example.artbook.MainCoroutineRule
import com.example.artbook.getOrAwaitValueTest
import com.example.artbook.repo.FakeArtRepository
import com.example.artbook.roomdb.Art
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest (){


   @get:Rule
    var MainCoroutineRule = MainCoroutineRule()

     @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    private lateinit var viewModel : ArtViewModel

     @Before
    fun setUp(){
         viewModel = ArtViewModel(FakeArtRepository())
    }

       @Test
        fun `insert art without age return error `(){
            viewModel.makeArt("mona lisa","da vinci","")
            val value = viewModel.insertArtMessage.getOrAwaitValueTest()
           assertThat(value.status).isEqualTo(com.example.artbook.util.Status.ERROR)
        }

       @Test
        fun `insert art without name return error  `(){
           viewModel.makeArt("","da vinci","1922")
           val value = viewModel.insertArtMessage.getOrAwaitValueTest()
           assertThat(value.status).isEqualTo(com.example.artbook.util.Status.ERROR)

        }

       @Test
        fun `insert art without artistName return error  `(){
           viewModel.makeArt("mona lisa","","1923")
           val value = viewModel.insertArtMessage.getOrAwaitValueTest()
           assertThat(value.status).isEqualTo(com.example.artbook.util.Status.ERROR)
        }








}
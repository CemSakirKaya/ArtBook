package com.example.artbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.artbook.model.ImageResponse
import com.example.artbook.roomdb.Art
import com.example.artbook.util.Resource

class FakeArtRepository  : ArtRepositoryInterface{

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)



    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return  Resource.success(ImageResponse(listOf(),0,0))
    }

    fun refreshData(){
        artsLiveData.postValue(arts)
    }


}
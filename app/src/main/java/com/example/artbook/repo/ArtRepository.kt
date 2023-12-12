package com.example.artbook.repo

import androidx.lifecycle.LiveData
import com.example.artbook.api.RetrofitAPI
import com.example.artbook.model.ImageResponse
import com.example.artbook.roomdb.Art
import com.example.artbook.roomdb.ArtDao
import com.example.artbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val retrofitAPI: RetrofitAPI,
    private val artDao: ArtDao
): ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
       artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
       artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
      return  artDao.observeData()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
       return  try {
           val response = retrofitAPI.searchImage(imageString)

           if (response.isSuccessful){
               response?.body().let {
               return@let  Resource.success(it)
               } ?: Resource.error("Error ",null)
           } else{
               Resource.error("Error",null)
           }

       }catch (e:Exception){
           Resource.error("No data",null)
       }
    }
}
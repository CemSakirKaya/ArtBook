package com.example.artbook.model


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    val images: List<Image>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)
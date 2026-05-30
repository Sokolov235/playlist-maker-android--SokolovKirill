package com.example.myapplication.creator

import com.example.myapplication.data.network.RetrofitNetworkClient
import com.example.myapplication.data.repository.TracksRepositoryImpl
import com.example.myapplication.domain.api.TracksRepository

object Creator {

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            RetrofitNetworkClient(Storage())
        )
    }
}
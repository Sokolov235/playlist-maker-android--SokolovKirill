package com.example.myapplication.data.network

import com.example.myapplication.creator.Storage
import com.example.myapplication.data.dto.TracksSearchRequest
import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.domain.api.NetworkClient

class RetrofitNetworkClient(
    private val storage: Storage
) : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {


        val request = dto as TracksSearchRequest
        val result = storage.search(request.expression)

        return TracksSearchResponse(result).apply {
            resultCode = 200
        }
    }
}
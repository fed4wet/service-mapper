package com.compress.servicemapper.service

import com.compress.servicemapper.dto.UserDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserService(
    val restTemplate: RestTemplate,
    @Value("\${db.microservice.url}") val dbMicroServiceUrl: String,
    @Value("\${user.end.point}") val endPoint: String
) {
    fun fetchData(): List<UserDto> {
        val responseType = object : ParameterizedTypeReference<List<UserDto>>() {}
        return restTemplate.exchange("$dbMicroServiceUrl/$endPoint", HttpMethod.GET, null, responseType).body ?: emptyList()
    }
}
package com.compress.servicemapper.service

import com.compress.servicemapper.dto.UserDto
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.io.ByteArrayInputStream
import java.util.zip.GZIPInputStream

@Service
class UserDecompressionService(
    @Value("\${db.microservice.url}") val dbMicroServiceUrl: String,
    @Value("\${user.end.point}") val endPoint: String,
    val restTemplate: RestTemplate,
    private var objectMapper: ObjectMapper
) {

    fun fetchDataCompressed(): ResponseEntity<ByteArray> {
        return restTemplate.getForEntity("$dbMicroServiceUrl$endPoint/compressed", ByteArray::class.java)
    }
    fun decompress(compressedData: ByteArray): ByteArray {
        ByteArrayInputStream(compressedData).use { bis ->
            GZIPInputStream(bis).use { gzip ->
                return gzip.readBytes()
            }
        }
    }
    fun convertToUserDto(decompressedData: ByteArray): List<UserDto> {
        return objectMapper.readValue(decompressedData, object : TypeReference<List<UserDto>>() {})
    }

}
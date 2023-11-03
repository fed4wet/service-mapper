package com.compress.servicemapper.controller

import com.compress.servicemapper.dto.UserDto
import com.compress.servicemapper.service.UserDecompressionService
import com.compress.servicemapper.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class DataMapperController(
    val userService: UserService,
    val userDecompressionService: UserDecompressionService,
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<UserDto>> {
        return try {
            val originalData = userService.fetchData()
            ResponseEntity.ok(originalData)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

    }

    @GetMapping("/decompress")
    fun transformData(): ResponseEntity<List<UserDto>> {
        val compressedData = userDecompressionService.fetchDataCompressed()

        return try {
            val decompressedData = userDecompressionService.decompress(compressedData.body!!)
            val users = userDecompressionService.convertToUserDto(decompressedData)
            ResponseEntity.ok(users)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
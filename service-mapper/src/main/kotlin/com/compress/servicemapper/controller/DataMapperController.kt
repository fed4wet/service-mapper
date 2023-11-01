package com.compress.servicemapper.controller

import com.compress.servicemapper.dto.UserDto
import com.compress.servicemapper.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class DataMapperController(
    val userService: UserService
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<UserDto>> {
        val originalData = userService.fetchData()
        return ResponseEntity.ok(originalData)
    }
}
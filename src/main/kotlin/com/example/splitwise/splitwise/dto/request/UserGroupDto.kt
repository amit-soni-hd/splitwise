package com.example.splitwise.splitwise.dto.request

data class UserGroupDto(
        var groupName: String,
        var users:List<Long>
)
package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.UserGroup
import org.springframework.data.jpa.repository.JpaRepository

interface UserGroupRepository:JpaRepository<UserGroup, Long> {

    fun findAllByGroupId(groupId:Long) : Iterable<UserGroup>
}
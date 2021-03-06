package com.example.splitwise.splitwise.module

import javax.persistence.*

@Entity
@Table(name = "user_group")
class UserGroup(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id:Long = 0,

        @Column(name = "group_id")
        var groupId: Long,

        @Column(name = "user_id")
        var userId: Long
)
package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.module.User
import lombok.Data

@Data
class Group {

    var groupId: String? = null
    var groupName: String? = null
    var userList: MutableList<User>? = null

    constructor(groupName: String?, userList: MutableList<User>?) {
        this.groupName = groupName
        this.userList = userList
    }
}
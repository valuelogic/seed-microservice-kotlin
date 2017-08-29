package com.valuelogic.service.web

import javax.persistence.*

typealias EMail = String
typealias Password = String

@Entity
data class UserInfo(
        val firstName: String,
        val lastName: String,
        @Id
        val eMail: EMail
)

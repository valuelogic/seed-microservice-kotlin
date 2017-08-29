package com.valuelogic.service.web

import com.valuelogic.service.db.RepositoryFactory
import com.valuelogic.service.monitoring.error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = "/users")
class UsersWebService {

    private val repoFactory: RepositoryFactory

    @Autowired
    constructor(repoFactory: RepositoryFactory) {
        this.repoFactory = repoFactory
    }

    @PostMapping
    fun createUser(): UserInfo? {
        //TODO add your logic here and additional services if needed
        val u = UserInfo(firstName = "mike", lastName = "wrona", eMail = UUID.randomUUID().toString())
        return repoFactory.transactional { r ->
            r.save(u).get()
        }
                .onFailure { ex -> error("Couldn't save user", ex) }
                .get()
    }

    //TODO add your logic here and additional services if needed
    @GetMapping
    fun getUsers(
            @RequestParam(required = false, defaultValue = "0") offset: Long = 0,
            @RequestParam(required = false, defaultValue = "50") limit: Long = 50

    ): List<UserInfo> = repoFactory.repository().query { q ->
        q.select(QUserInfo.userInfo)
                .from(QUserInfo.userInfo)
                .offset(offset)
                .limit(limit)
                .fetch()
    }
            .onFailure { ex -> error("Couldn't get users", ex) }
            .getOrElse { listOf() }

}

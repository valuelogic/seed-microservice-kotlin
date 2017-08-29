package com.valuelogic.service.db.sql

import com.valuelogic.service.db.ReadRepository
import com.valuelogic.service.db.RepositoryFactory
import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManager

@Configuration
@EntityScan(basePackages = arrayOf("com.valuelogic.service"))
class JdbcConfig {

    @Bean
    fun repository(em: EntityManager): ReadRepository = BasicReadRepository(em)

    @Bean
    fun repositoryFactory(tm: PlatformTransactionManager, em: EntityManager): RepositoryFactory = SqlRepositoryFactory(tm, em)

    companion object {
        val BASE_PACKAGE = "com.valuelogic.service"
    }

}
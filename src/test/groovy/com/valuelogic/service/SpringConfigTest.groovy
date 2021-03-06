package com.valuelogic.service

import com.valuelogic.service.db.RepositoryFactory
import com.valuelogic.service.test.sql.InMemorySqlDbConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.sql.DataSource

@Import([
        InMemorySqlDbConfig.class
])
@SpringBootTest
class SpringConfigTest extends Specification {

    @Autowired
    RepositoryFactory repositoryFactory
    @Autowired
    DataSource dataSource

    def "should inject beans from config"() {
        expect:
        dataSource != null
        repositoryFactory != null
    }

}

package com.valuelogic.service.db.sql

import com.valuelogic.service.db.ReadRepository
import com.valuelogic.service.db.RepositoryFactory
import com.valuelogic.service.db.WriteRepository
import org.funktionale.tries.Try
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import javax.persistence.EntityManager

class SqlRepositoryFactory : RepositoryFactory {

    private val tm: PlatformTransactionManager
    private val em: EntityManager
    private val repo: BasicWriteRepository

    private fun <R> transaction(operation: () -> R): Try<R> {
        val t = tm.getTransaction(DefaultTransactionDefinition())
        try {
            val r = operation()
            tm.commit(t)
            return Try.Success(r)
        } catch (e: Exception) {
            tm.rollback(t)
            return Try.Failure(e)
        }
    }

    override fun <T> transactional(operation: (WriteRepository) -> T): Try<T> = transaction { operation(repo) }

    override fun repository(): ReadRepository = repo

    constructor(tm: PlatformTransactionManager, em: EntityManager) {
        this.tm = tm
        this.em = em
        repo = BasicWriteRepository(em)
    }
}

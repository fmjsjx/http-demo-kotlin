package com.github.fmjsjx.demo.http.core.dao

import com.github.fmjsjx.demo.http.core.entity.Account
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.isEqual
import org.springframework.stereotype.Component

@Component
class AccountRepository(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)!!

    suspend fun insertOne(account: Account): Account {
        return r2dbcEntityTemplate.insert(account).doOnError {
            logger.error("insert account failed: {}", account, it)
        }.awaitSingle()
    }

    suspend fun save(account: Account): Account =
        if (account.id == 0L) {
            insertOne(account)
        } else {
            updateOne(account)
        }

    suspend fun updateOne(account: Account): Account {
        return r2dbcEntityTemplate.update(account).doOnError {
            logger.error("update account failed: {}", account, it)
        }.awaitSingle()
    }

    suspend fun selectOne(id: Long): Account? {
        return r2dbcEntityTemplate.select<Account>().matching(query(where("id") isEqual id)).one().doOnError {
            logger.error("select one account failed: id = {}", id, it)
        }.awaitSingleOrNull()
    }

    suspend fun selectOneByUdid(udid: String): Account? {
        return r2dbcEntityTemplate.select<Account>().matching(query(where("udid") isEqual udid)).one().doOnError {
            logger.error("select one account failed: udid = {}", udid, it)
        }.awaitSingleOrNull()
    }

}
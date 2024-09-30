package com.github.fmjsjx.demo.http.core.service

import com.github.fmjsjx.libcommon.util.RandomUtil
import com.mongodb.reactivestreams.client.MongoDatabase
import io.netty.util.collection.IntObjectHashMap
import io.netty.util.collection.IntObjectMap
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.getBeansOfType
import org.springframework.stereotype.Service

@Service
class MongoDBManager(
    private val beanFactory: ListableBeanFactory,
) : InitializingBean {

    private val logger = LoggerFactory.getLogger(javaClass)!!
    private val databases: IntObjectMap<MongoDatabase> = IntObjectHashMap()
    private lateinit var groupIds: IntArray
    private var singleMode: Boolean = false
    private lateinit var singleDatabase: Pair<Int, MongoDatabase>

    override fun afterPropertiesSet() {
        logger.info("[mongodb] Initialize groups...")
        fun String.parseGroupId(): Int? =
            """demo(\d+)MongoDatabase""".toRegex().find(this)?.let { it.groupValues[1].toInt() }
        beanFactory.getBeansOfType<MongoDatabase>().forEach { (name, bean) ->
            name.parseGroupId()?.also { groupId ->
                databases.put(groupId, bean)
            }
        }
        groupIds = databases.keys.sorted().toIntArray()
        singleMode = databases.size == 1
        if (singleMode) {
            singleDatabase = databases.entries.first().toPair()
        }
        logger.info("[mongodb] Available groups: {}", groupIds)
    }

    fun nextGroupId(): Int = if (singleMode) {
        singleDatabase.first
    } else {
        RandomUtil.randomOne(groupIds)
    }

    fun database(groupId: Int): MongoDatabase =
        databases.get(groupId) ?: throw NoSuchElementException("No such MongoDatabase with groupId $groupId")

}

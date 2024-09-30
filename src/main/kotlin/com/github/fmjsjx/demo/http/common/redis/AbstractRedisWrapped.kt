package com.github.fmjsjx.demo.http.common.redis

import org.slf4j.LoggerFactory

abstract class AbstractRedisWrapped : RedisWrapped {

    override val logger = LoggerFactory.getLogger(javaClass)!!

}

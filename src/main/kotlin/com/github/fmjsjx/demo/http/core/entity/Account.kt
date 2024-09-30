package com.github.fmjsjx.demo.http.core.entity

import com.alibaba.fastjson2.annotation.JSONField
import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.fmjsjx.libcommon.util.DateTimeUtil
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.InsertOnlyProperty
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("tb_account")
data class Account(
    @Id
    @Column("id")
    @ReadOnlyProperty
    var id: Long = 0,
    @Column("group_id")
    @InsertOnlyProperty
    var groupId: Int = 0,
    @Column("type")
    var type: Int = 0,
    @Column("state")
    var state: Int = 0,
    @Column("remote_address")
    @InsertOnlyProperty
    var remoteAddress: String = "",
    @Column("client_version")
    @InsertOnlyProperty
    var clientVersion: String = "",
    @Column("udid")
    @InsertOnlyProperty
    var udid: String = "",
    @Column("slot")
    @InsertOnlyProperty
    var slot: Int = 0,
    @Column("device_info")
    @InsertOnlyProperty
    var deviceInfo: String = "",
    @Column("os_info")
    @InsertOnlyProperty
    var osInfo: String = "",
    @Column("created_at")
    @InsertOnlyProperty
    var createdAt: Long = 0,
    @Column("updated_at")
    var updatedAt: Long = 0,
) {

    companion object {
        const val GUEST = 1
        const val USER = 2
        const val GM = 3
        const val NORMAL = 1
        const val FORBIDDEN = 2
    }

    @Transient
    private var _createTime: LocalDateTime? = null

    @get:JsonIgnore
    @get:JSONField(serialize = false)
    @get:com.jsoniter.annotation.JsonIgnore
    val createTime: LocalDateTime
        get() = _createTime ?: DateTimeUtil.ofEpochMilli(createdAt).also { _createTime = it }

    @get:JsonIgnore
    @get:JSONField(serialize = false)
    @get:com.jsoniter.annotation.JsonIgnore
    val updateTime: LocalDateTime get() = DateTimeUtil.ofEpochMilli(updatedAt)

    override fun toString(): String =
        "Account(id=$id, groupId=$groupId, type=$type, state=$state, remoteAddress=$remoteAddress, clientVersion=$clientVersion, udid=$udid, slot=$slot, deviceInfo=$deviceInfo, osInfo=$osInfo, createTime=$createTime, updateTime=$updateTime)"

}

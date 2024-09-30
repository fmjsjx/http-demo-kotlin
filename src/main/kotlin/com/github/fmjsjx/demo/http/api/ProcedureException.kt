package com.github.fmjsjx.demo.http.api

import com.github.fmjsjx.demo.http.exception.DemoHttpException
import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus.OK

open class ProcedureException : DemoHttpException {

    val code: Int
    val status: HttpResponseStatus

    constructor(code: Int, message: String, cause: Throwable, status: HttpResponseStatus = OK) : super(message, cause) {
        this.code = code
        this.status = status
    }

    constructor(code: Int, message: String, status: HttpResponseStatus = OK) : super(message) {
        this.code = code
        this.status = status
    }

    override val message: String get() = super.message!!

    fun toResult(): ProcedureResult = ProcedureResult.fail(code, message)

}
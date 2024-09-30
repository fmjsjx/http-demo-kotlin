package com.github.fmjsjx.demo.http.api

import io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST

class BadRequestException : ProcedureException {

    constructor(code: Int, message: String, cause: Throwable): super(code, message, cause, BAD_REQUEST)
    constructor(code: Int, message: String): super(code, message, BAD_REQUEST)

}

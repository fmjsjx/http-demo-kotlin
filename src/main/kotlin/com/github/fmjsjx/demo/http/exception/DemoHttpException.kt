package com.github.fmjsjx.demo.http.exception

import java.util.concurrent.CompletionException

abstract class DemoHttpException : CompletionException {

    protected constructor() : super()
    protected constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

}

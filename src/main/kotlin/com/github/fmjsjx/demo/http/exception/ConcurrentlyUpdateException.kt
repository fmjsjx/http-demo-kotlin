package com.github.fmjsjx.demo.http.exception

object ConcurrentlyUpdateException: DemoHttpException() {
    private fun readResolve(): Any = ConcurrentlyUpdateException
}
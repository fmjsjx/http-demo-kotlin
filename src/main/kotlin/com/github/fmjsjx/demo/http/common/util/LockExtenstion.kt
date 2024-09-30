package com.github.fmjsjx.demo.http.common.util

import java.util.concurrent.locks.Lock

inline fun <T : Lock, R> T.use(block: () -> R): R {
    lock()
    try {
        return block()
    } finally {
        unlock()
    }
}
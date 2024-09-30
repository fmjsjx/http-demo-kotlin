package com.github.fmjsjx.demo.http.api

data class ProcedureResult(
    val code: Int = 0,
    val message: String? = null,
    val data: ProcedureResultData? = null,
) {
    companion object {
        @JvmStatic
        fun ok(data: ProcedureResultData): ProcedureResult = ProcedureResult(data = data)

        @JvmStatic
        fun ok(): ProcedureResult = ok(ProcedureResultData())

        @JvmStatic
        fun fail(code: Int, message: String): ProcedureResult = ProcedureResult(code = code, message = message)
    }
}

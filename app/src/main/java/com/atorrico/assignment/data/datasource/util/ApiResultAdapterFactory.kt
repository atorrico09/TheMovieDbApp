package com.atorrico.assignment.data.datasource.util

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ApiResult::class.java) return null
        check(responseType is ParameterizedType)

        val successType = getParameterUpperBound(0, responseType)

        return ApiResultCallAdapter<Any>(successType)
    }
}
internal class ApiResultCallAdapter<T>(
    private val successType: Type,
) : CallAdapter<T, Call<ApiResult<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiResult<T>> = ApiResultCall(call)
}

internal class ApiResultCall<T> constructor(
    private val callDelegate: Call<T>,
) : Call<ApiResult<T>> {

    @Suppress("detekt.MagicNumber")
    override fun enqueue(callback: Callback<ApiResult<T>>) = callDelegate.enqueue(object :
        Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                when (response.code()) {
                    in 200..208 -> {
                        callback.onResponse(this@ApiResultCall, Response.success(ApiResult.Success(it)))
                    }
                    in 400..409 -> {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Error(response.code(), response.message()))
                        )
                    }
                }
            } ?: callback.onResponse(this@ApiResultCall, Response.success(ApiResult.Error(123, "message")))
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            callback.onResponse(this@ApiResultCall, Response.success(ApiResult.Exception(throwable)))
            call.cancel()
        }
    })

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(callDelegate.clone())

    override fun execute(): Response<ApiResult<T>> =
        throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()
}


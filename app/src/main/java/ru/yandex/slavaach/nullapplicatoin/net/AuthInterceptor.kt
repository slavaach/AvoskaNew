package ru.yandex.slavaach.nullapplicatoin.net

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor constructor(private val holder: AuthTokenHolder) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
        holder.xsrf?.let {
            newRequest.addHeader("X-XSRF-TOKEN", it)
        }
        return chain.proceed(newRequest.build())
    }
}

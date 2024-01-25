package ru.yandex.slavaach.nullapplicatoin.net

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import timber.log.Timber

class SessionCookieJar(
    private val domain: String,
    private val holder: AuthTokenHolder,
) : CookieJar {

    companion object {
        private const val TOKEN_NAME = "XSRF-TOKEN"
        private const val JSID_NAME = "JSESSIONID"
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val list = mutableListOf<Cookie>()
        holder.xsrf?.let { list.add(generateCookie(TOKEN_NAME, it)) }
        holder.jsid?.let { list.add(generateCookie(JSID_NAME, it)) }
        Timber.d("Loaded cookies ${list.joinToString(";")}")
        return list
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach { cookie ->
            if (cookie.domain == domain) {
                if (cookie.name == TOKEN_NAME) holder.xsrf = cookie.value
                if (cookie.name == JSID_NAME) holder.jsid = cookie.value
            }
        }
        Timber.d("Saved cookies ${cookies.joinToString(";")}")
    }

    private fun generateCookie(key: String, value: String): Cookie {
        return Cookie.Builder()
            .domain(domain)
            .path("/")
            .name(key)
            .value(value)
            .httpOnly()
            .secure()
            .build()
    }
}

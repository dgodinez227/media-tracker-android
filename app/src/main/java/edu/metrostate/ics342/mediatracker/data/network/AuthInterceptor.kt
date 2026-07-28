package edu.metrostate.ics342.mediatracker.data.network

import android.util.Log
import edu.metrostate.ics342.mediatracker.data.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionRepository: SessionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val storedToken = runBlocking {
            sessionRepository.getAccessToken()
        }?.trim()

        check(!storedToken.isNullOrBlank()) {
            "Access token is missing from the saved session"
        }

        Log.i(
            "AuthCheck",
            "tokenLength=${storedToken.length}, " +
                    "startsWithBearer=${storedToken.startsWith("Bearer ", ignoreCase = true)}, " +
                    "startsWithJwt=${storedToken.startsWith("eyJ")}, " +
                    "hasQuotes=${storedToken.startsWith("\"") || storedToken.endsWith("\"")}, " +
                    "hasSpaces=${storedToken.contains(" ")}"
        )

        val authorizationValue =
            if (storedToken.startsWith("Bearer ", ignoreCase = true)) {
                storedToken
            } else {
                "Bearer $storedToken"
            }

        val request = chain.request()
            .newBuilder()
            .header("Authorization", authorizationValue)
            .build()

        check(!request.header("Authorization").isNullOrBlank()) {
            "Authorization header was not added to the media request"
        }

        Log.i(
            "AuthCheck",
            "Authorization header attached=${!request.header("Authorization").isNullOrBlank()}"
        )

        check(!request.header("Authorization").isNullOrBlank()) {
            "Authorization header was not added to the media request"
        }

        return chain.proceed(request)
    }
}
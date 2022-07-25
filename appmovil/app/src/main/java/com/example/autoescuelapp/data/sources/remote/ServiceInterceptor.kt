package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.utils.UsuarioSing
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ServiceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        var request = original.newBuilder().build()

        if (UsuarioSing.token == null) {
            if (UsuarioSing.dni != null && UsuarioSing.pass != null) {
                request = original.newBuilder().header("Authorization", Credentials.basic(
                    UsuarioSing.dni!!,
                    UsuarioSing.pass!!)).build()
            }
        } else {
            request = original.newBuilder()
                .header("Authorization", "Bearer " + UsuarioSing.token!!).build()
        }

        var response = chain.proceed(request)

        if (response.header("Authorization") != null) {
            UsuarioSing.token = response.header("Authorization")
        }
        if (!response.isSuccessful && response.header("Expires") != null) {
            response.close()
            request =
                original.newBuilder().header("Authorization", Credentials.basic(UsuarioSing.dni!!,
                    UsuarioSing.pass!!)).build()
            response = chain.proceed(request)
            if (response.header("Authorization") != null) {
                UsuarioSing.token = response.header("Authorization")
            }
        }

        return response
    }
}
package com.brian.kotlinkoinmvvmcoroutines

import com.brian.kotlinkoinmvvmcoroutines.api.ApiServices
import com.brian.kotlinkoinmvvmcoroutines.module.appModules
import com.brian.kotlinkoinmvvmcoroutines.module.provideNetworkModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.MockitoAnnotations
import java.io.File

abstract class BaseTest : KoinTest {
    protected val apiServices: ApiServices by inject()
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockServer = MockWebServer()
        mockServer.start()
        startKoin {
            modules(
                listOf(
                    provideNetworkModule(mockServer.url("/").toString()),
                    appModules
                )
            )
        }
    }

    @After
    open fun tearDown() {
        mockServer.shutdown()
        stopKoin()
    }

    fun mockHttpResponse(mockServer: MockWebServer, fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}

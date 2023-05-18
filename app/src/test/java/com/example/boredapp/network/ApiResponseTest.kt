package com.example.boredapp.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.boredapp.persistence.BoredActivityDao
import com.example.boredapp.persistence.BoredActivityDatabase
import com.example.boredapp.ui.main.MainRepository
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], application = HiltTestApplication::class)
class ApiResponseTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: BoredActivityDatabase
    private lateinit var activityDao: BoredActivityDao

    private lateinit var repository: MainRepository
    private lateinit var mockedResponse: String
    private val mockWebServer = MockWebServer()
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun init() {
        hiltRule.inject()
        activityDao = database.boredActivityDao()

        mockWebServer.start(8000)
        val BASE_URL = mockWebServer.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(BoredActivityService::class.java)

        repository = MainRepository(service, activityDao)
    }

    @Test
    fun testApiSuccess() {
        mockedResponse = MockResponseFileReader("./success_response.json").content
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { repository.getActivityByKey(3943505) }
        val json = gson.toJson(response)
        val resultResponse = JsonParser.parseString(json)
        val expectedResponse = JsonParser.parseString(mockedResponse)
        Assert.assertNotNull(response)
        Assert.assertTrue(resultResponse.equals(expectedResponse))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        database.close()
    }

}
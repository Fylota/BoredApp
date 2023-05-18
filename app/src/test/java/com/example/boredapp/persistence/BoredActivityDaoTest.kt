package com.example.boredapp.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.boredapp.model.BoredActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], application = HiltTestApplication::class)
@SmallTest
class BoredActivityDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: BoredActivityDatabase
    private lateinit var activityDao: BoredActivityDao

    @Before
    fun setup() {
        hiltRule.inject()
        activityDao = database.boredActivityDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertActivity() = runBlocking {
        val mockData: BoredActivity = BoredActivity.mock()
        val mockDataList: List<BoredActivity> = listOf(mockData)
        activityDao.insertBoredActivity(mockData)
        val allActivities = activityDao.getAllBoredActivities()
        assertThat(allActivities.first(), `is` (mockDataList))
    }

    @Test
    fun modifyActivity() = runBlocking {
        val mockData: BoredActivity = BoredActivity.mock()
        activityDao.insertBoredActivity(mockData)

        val editedActivity: BoredActivity = mockData.copy(participants = 10)

        activityDao.updateBoredActivity(editedActivity)

        val getUpdatedActivity = activityDao.getBoredActivity(editedActivity.key)
        assertThat(getUpdatedActivity.first().participants, `is` (10))
    }

    @Test
    fun deleteActivity() = runBlocking {
        val mockData: BoredActivity = BoredActivity.mock()
        val activityFlow = activityDao.getAllBoredActivities()

        activityDao.insertBoredActivity(mockData)

        assertFalse(activityFlow.take(1).toList().flatten().isEmpty())

        activityDao.deleteBoredActivity(mockData)

        assertTrue(activityFlow.take(1).toList().flatten().isEmpty())
    }
}

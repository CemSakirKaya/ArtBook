package com.example.artbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.artbook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()


    @Inject
    @Named("testDatabase")
     lateinit var database: ArtDatabase

    private lateinit var dao: ArtDao

    @Before
    fun setUp(){
        hiltRule.inject()
        dao = database.artDao()

    }
    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertArtTesting() = runBlockingTest{
        val  art = Art("mona lisa","göktuğ",2000,"dkafl",1)
        dao.insertArt(art)
        val list = dao.observeData().getOrAwaitValue()
          assertThat(list).contains(art)

    }

    @Test
    fun deleteArtTesting() = runBlockingTest{
        val  art = Art("mona lisa","göktuğ",2000,"dkafl",1)
        dao.insertArt(art)
        dao.deleteArt(art)
        val list = dao.observeData().getOrAwaitValue()
        assertThat(list).doesNotContain(art)


    }





}
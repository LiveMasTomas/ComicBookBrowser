package com.mastomas.comicbookbrowser

import android.net.Uri
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.model.MarvelCharacter.MarvelUrl.UrlType.DETAIL
import com.mastomas.comicbookbrowser.repository.MarvelRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ApiTests {

    //region Variables
    private val mockRepo = object : MarvelRepository {
        override suspend fun getCharacters(): List<MarvelCharacter> = listOf(
            makeMockCharacter(1),
            makeMockCharacter(2),
            makeMockCharacter(3)
        )
    }
    //endregion

    //region Tests
    @Before
    fun before() {
        mockkStatic(Uri::class)
        every { Uri.parse("test") } returns mockk()
    }

    @Test
    fun testGetCharacters() = runBlocking {
        val list = mockRepo.getCharacters()
        Assert.assertFalse(list.isEmpty())
    }

    @Test
    fun testFirstCharacter() = runBlocking {
        val list = mockRepo.getCharacters()
        val first = list.firstOrNull()
        Assert.assertTrue(first != null)
        Assert.assertEquals(first?.name, "Test 1")
    }
    //endregion

    //region Functions
    private fun makeMockCharacter(i: Int): MarvelCharacter =
        MarvelCharacter(
            "Test $i",
            "Test Description",
            Uri.parse("test"),
            listOf(
                MarvelCharacter.MarvelUrl(
                    DETAIL,
                    Uri.parse("test")
                )
            )
        )
    //endregion
}
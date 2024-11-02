package com.example.heroapp

import com.example.heroapp.network.dataClasses.Thumbnail
import org.junit.Assert.assertEquals
import org.junit.Test

class GetUrlFunctionTest {


    @Test
    fun `given a thumbnail with a proper http URL then getUrl() should replace http with https`() {
        val thumbnail = Thumbnail("http://example.com/image", "jpg")
        val expectedUrl = "https://example.com/image.jpg"
        assertEquals(expectedUrl, thumbnail.getUrl())
    }
    @Test
    fun `given a thumbnail with a proper https URL then getUrl() should return the same URL`() {
        val thumbnail = Thumbnail("https://example.com/image", "jpg")
        val expectedUrl = "https://example.com/image.jpg"
        assertEquals(expectedUrl, thumbnail.getUrl())
    }
    @Test
    fun `given a thumbnail with an empty path then getUrl() should return an empty string`() {
        val thumbnail = Thumbnail("", "jpg")
        val expectedUrl = ".jpg"
        assertEquals(expectedUrl, thumbnail.getUrl())
    }
    @Test
    fun `given a thumbnail with an empty extension then getUrl() should return a URL without an extension`() {
        val thumbnail = Thumbnail("https://example.com/image", "")
        val expectedUrl = "https://example.com/image"
        assertEquals(expectedUrl, thumbnail.getUrl())
    }
    @Test
    fun `given a thumbnail with both empty path and extension then getUrl() should return an empty string`() {
        val thumbnail = Thumbnail("", "")
        val expectedUrl = ""
        assertEquals(expectedUrl, thumbnail.getUrl())
    }
    @Test
    fun `given a thumbnail with a path that doesn't contain http then getUrl() should return the same URL`() {
        val thumbnail = Thumbnail("example.com/image", "jpg")
        val expectedUrl = "example.com/image.jpg"
        assertEquals(expectedUrl, thumbnail.getUrl())
    }
}
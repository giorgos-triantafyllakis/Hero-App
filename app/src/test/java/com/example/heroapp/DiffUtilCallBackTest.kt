package com.example.heroapp

import com.example.heroapp.network.dataClasses.CharacterRestModel
import com.example.heroapp.paging.DiffUtilCallBack
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class DiffUtilCallBackTest {
    private val diffUtilCallBack = DiffUtilCallBack()
    private val oldItem = Mockito.mock(CharacterRestModel::class.java)
    private val newItem = Mockito.mock(CharacterRestModel::class.java)

    @Test
    fun `test areItemsTheSame returns true when old and new items have the same name`() {

        Mockito.`when`(oldItem.name).thenReturn("Iron Man")
        Mockito.`when`(newItem.name).thenReturn("Iron Man")

        // when
        val result = diffUtilCallBack.areItemsTheSame(oldItem, newItem)

        // then
        assertEquals(true, result)
    }

    @Test
    fun `test areItemsTheSame returns false when old and new items have different names`() {

        Mockito.`when`(oldItem.name).thenReturn("Iron Man")
        Mockito.`when`(newItem.name).thenReturn("Captain America")

        // when
        val result = diffUtilCallBack.areItemsTheSame(oldItem, newItem)

        // then
        assertEquals(false, result)
    }

    @Test
    fun `test areContentsTheSame returns true when old and new items have the same name`() {

        Mockito.`when`(oldItem.name).thenReturn("Iron Man")
        Mockito.`when`(newItem.name).thenReturn("Iron Man")

        // when
        val result = diffUtilCallBack.areContentsTheSame(oldItem, newItem)

        // then
        assertEquals(true, result)
    }

    @Test
    fun `test areContentsTheSame returns false when old and new items have different names`() {

        Mockito.`when`(oldItem.name).thenReturn("Iron Man")
        Mockito.`when`(newItem.name).thenReturn("Captain America")

        // when
        val result = diffUtilCallBack.areContentsTheSame(oldItem, newItem)

        // then
        assertEquals(false, result)
    }
}

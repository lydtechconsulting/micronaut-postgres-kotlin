package demo.service

import demo.domain.Item
import demo.exception.ItemNotFoundException
import demo.repository.ItemRepository
import demo.util.TestDomainData
import demo.util.TestRestData
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional
import java.util.UUID.randomUUID
import kotlin.test.assertEquals

class ItemServiceTest {

    private lateinit var service: ItemService
    val itemRepositoryMock = mockk<ItemRepository>()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        service = ItemService(itemRepositoryMock)
    }

    @Test
    fun testCreateItem() {
        val itemId = randomUUID()
        every { itemRepositoryMock.save(any(Item::class)) } returns TestDomainData.buildItem(itemId, randomAlphabetic(8))

        val request = TestRestData.buildCreateItemRequest(randomAlphabetic(8))
        val newItemId = service.createItem(request)

        assertEquals(itemId, newItemId)
        verify(exactly = 1) { itemRepositoryMock.save(any(Item::class)) }
    }

    @Test
    fun testGetItem() {
        val itemId = randomUUID()
        val item = TestDomainData.buildItem(itemId, randomAlphabetic(8))

        every { itemRepositoryMock.findById(itemId) } returns Optional.of(item)
        val response = service.getItem(itemId)

        assert(response.id == itemId)
        assert(response.name == item.name)
        verify(exactly = 1) { itemRepositoryMock.findById(itemId) }
    }

    @Test
    fun testGetItem_NotFound() {
        val itemId = randomUUID()

        every { itemRepositoryMock.findById(itemId) } returns Optional.empty()

        assertThrows(ItemNotFoundException::class.java) { service.getItem(itemId) }
    }

    @Test
    fun testUpdateItem() {
        val itemId = randomUUID()
        val item = TestDomainData.buildItem(itemId, randomAlphabetic(8))
        val updateRequest = TestRestData.buildUpdateItemRequest(randomAlphabetic(8))
        every { itemRepositoryMock.findById(itemId) } returns Optional.of(item)
        every { itemRepositoryMock.update(any()) } returns item

        service.updateItem(itemId, updateRequest)

        verify(exactly = 1) { itemRepositoryMock.findById(itemId) }
        verify { itemRepositoryMock.update(match { it.id == itemId && it.name == updateRequest.name }) }
    }

    @Test
    fun testUpdateItem_NotFound() {
        val itemId = randomUUID()
        val updateRequest = TestRestData.buildUpdateItemRequest(randomAlphabetic(8))
        every { itemRepositoryMock.findById(itemId) } returns Optional.empty()

        assertThrows(ItemNotFoundException::class.java) { service.updateItem(itemId, updateRequest) }
    }

    @Test
    fun testDeleteItem() {
        val itemId = randomUUID()
        val item = TestDomainData.buildItem(itemId, randomAlphabetic(8))
        every { itemRepositoryMock.findById(itemId) } returns Optional.of(item)
        every { itemRepositoryMock.deleteById(itemId) } just Runs

        service.deleteItem(itemId)

        verify(exactly = 1) { itemRepositoryMock.findById(itemId) }
        verify(exactly = 1) { itemRepositoryMock.deleteById(itemId) }
    }

    @Test
    fun testDeleteItem_NotFound() {
        val itemId = randomUUID()

        every { itemRepositoryMock.findById(itemId) } returns Optional.empty()

        assertThrows(ItemNotFoundException::class.java) { service.deleteItem(itemId) }
    }
}

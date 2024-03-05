package demo.service

import demo.domain.Item
import demo.exception.ItemNotFoundException
import demo.repository.ItemRepository
import demo.rest.api.CreateItemRequest
import demo.rest.api.GetItemResponse
import demo.rest.api.UpdateItemRequest
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.util.UUID

@Singleton
class ItemService(private val itemRepository: ItemRepository) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun createItem(request: CreateItemRequest): UUID {
        var item = Item(request.name)
        item = itemRepository.save(item)
        log.info("Item created with id: {}", item.id)
        return item.id as UUID
    }

    fun getItem(itemId: UUID): GetItemResponse {
        val item = itemRepository.findById(itemId).orElseThrow {
            log.warn("Item with id: $itemId not found")
            ItemNotFoundException()
        }
        log.info("Found item with id: {}", item.id)
        return GetItemResponse(item.id as UUID, item.name)
    }

    fun updateItem(itemId: UUID, request: UpdateItemRequest) {
        val item = itemRepository.findById(itemId).orElseThrow {
            log.warn("Item with id: $itemId not found")
            ItemNotFoundException()
        }
        log.info("Found item with id: $itemId")
        val updatedItem = Item(item.id, request.name)
        itemRepository.update(updatedItem)
        log.info("Item updated with id: {} - name: {}", itemId, request.name)
    }

    fun deleteItem(itemId: UUID) {
        val item = itemRepository.findById(itemId).orElseThrow {
            log.warn("Item with id: $itemId not found")
            ItemNotFoundException()
        }
        itemRepository.deleteById(item.id)
        log.info("Deleted item with id: {}", item.id)
    }
}

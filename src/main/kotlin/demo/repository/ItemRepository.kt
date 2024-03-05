package demo.repository

import demo.domain.Item
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface ItemRepository : CrudRepository<Item, UUID>

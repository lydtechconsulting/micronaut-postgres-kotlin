package demo.util

import demo.domain.Item
import java.util.UUID

object TestDomainData {

    fun buildItem(id: UUID, name: String): Item {
        return Item(id, name)
    }
}

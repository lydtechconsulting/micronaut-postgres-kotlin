package demo.domain

import io.micronaut.data.annotation.AutoPopulated
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import java.util.UUID
import javax.persistence.Id

@MappedEntity("ITEM")
data class Item(
    @Id
    @AutoPopulated
    @MappedProperty("ID")
    var id: UUID? = null,

    @MappedProperty("NAME")
    var name: String
) {
    constructor(name: String) : this(null, name)
}

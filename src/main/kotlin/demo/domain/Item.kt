package demo.domain

import io.micronaut.data.annotation.AutoPopulated
import io.micronaut.data.annotation.MappedEntity
import java.util.UUID
import javax.persistence.Id

@MappedEntity
data class Item(
    @Id
    @AutoPopulated
    var id: UUID? = null,

    var name: String
) {
    constructor(name: String) : this(null, name)
}

package demo.repository

import demo.domain.Item
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.jdbc.annotation.JdbcRepository

import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ItemRepository : CrudRepository<Item, UUID>

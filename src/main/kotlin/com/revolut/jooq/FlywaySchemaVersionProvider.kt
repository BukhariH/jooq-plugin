package com.revolut.jooq

import org.jooq.impl.DSL.*
import org.jooq.util.SchemaDefinition
import org.jooq.util.SchemaVersionProvider

class FlywaySchemaVersionProvider : SchemaVersionProvider {
    companion object {
        var primarySchema = ""
    }

    override fun version(schema: SchemaDefinition): String {
        return schema.database.create()
                .select(max(field("version")).`as`("max_version"))
                .from(table(name(primarySchema, "flyway_schema_history")))
                .fetchOne("max_version", String::class.java)
    }

}
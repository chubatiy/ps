package org.ps.enums

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import javax.validation.ValidationException

@JsonDeserialize(using = Currency.Deserializer::class)
@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class Currency {
    EUR,
    USD;

    class Deserializer : JsonDeserializer<Currency>() {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Currency {
            return fromString(p.text)
        }
    }

    companion object {

        fun fromString(text: String?): Currency {
            return values().firstOrNull { it.name.equals(text, true) }
                ?: throw ValidationException("Value $text not supported")
        }
    }
}
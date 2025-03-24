package org.ps.entities.converter

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * Stores a date in UTC time
 * the original timezone data is lost in storage, gets converted to UTC
 * (But ZonedDateTime is easier to understand)
 */
@Converter
open class LocalDateTimeToZonedDateTimeConverter : AttributeConverter<ZonedDateTime, Date> {

    override fun convertToDatabaseColumn(attribute: ZonedDateTime?): Date? {
        return attribute?.toInstant()?.let { Date.from(it) }
    }

    override fun convertToEntityAttribute(dbData: Date?): ZonedDateTime? {
        return dbData?.toInstant()?.atZone(ZoneOffset.UTC)
    }

}
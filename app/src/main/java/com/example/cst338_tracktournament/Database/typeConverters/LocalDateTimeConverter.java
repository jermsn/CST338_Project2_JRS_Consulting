package com.example.cst338_tracktournament.Database.typeConverters;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This is a series of converters. Android room stores only String, Int, and Real
 * Since Dates and Datetimes are none of those, they need to be converted to long
 * for storage and converted back to datetime for display. These functions will
 * do that. These are identical to the converters built within our GymLog homework
 * and additional converters may be built for other datatypes.
 */
public class LocalDateTimeConverter {

    /**
     * This takes a date time and converts it to a long (real)
     * This is passed a LocalDate.now() (typically by the constructor)
     * representing the date the exercise was logged
     * It is converted into a timezone specific time and then a float
     * @param date the datetime to be converted
     * @return a long number suitable for android room
     */
    @TypeConverter
    public long convertDateToLong(LocalDateTime date) {
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    @TypeConverter
    public LocalDateTime convertLongToDate(Long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
    }
}

package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DateTimeUtilTest {

    @Test
    void checkValidDate_validDate_success() {
        assertTrue(DateTimeUtil.isValidDate("01-01-2024 13:59"));
        assertTrue(DateTimeUtil.isValidDate("31-12-2024 23:59"));
        assertTrue(DateTimeUtil.isValidDate("29-02-2024 00:00"));

    }

    @Test
    void checkValidDate_invalidFormat_failure() {
        // dd-mm-yy HH:mm
        assertFalse(DateTimeUtil.isValidDate("01-01-24 00:00"));
        // mm-dd-yyyy HH:mm
        assertFalse(DateTimeUtil.isValidDate("01-31-2024 00:00"));
        // dd-mm HH:mm
        assertFalse(DateTimeUtil.isValidDate("31-01 00:00"));
        // yyyy-mm-dd HH:mm
        assertFalse(DateTimeUtil.isValidDate("2024-01-31 00:00"));
        // dd/mm/yyyy HH:mm
        assertFalse(DateTimeUtil.isValidDate("31/01/2024 00:00"));
        // dd-mm-yyyy HH:mm:ss
        assertFalse(DateTimeUtil.isValidDate("01-01-2020 00:00:00"));
        // dd-mm-yyyy
        assertFalse(DateTimeUtil.isValidDate("01-01-2020"));
    }

    @Test
    void checkValidDate_nonexistentDate_failure() {
        // No leap year in 2023
        assertFalse(DateTimeUtil.isValidDate("29-02-2023 00:00"));
        // No 30-31st of February
        assertFalse(DateTimeUtil.isValidDate("30-02-2024 00:00"));
        assertFalse(DateTimeUtil.isValidDate("31-02-2024 00:00"));
        // Mp 32nd of month
        assertFalse(DateTimeUtil.isValidDate("32-01-2024 00:00"));
        /// Invalid times
        assertFalse(DateTimeUtil.isValidDate("01-01-2024 25:00"));
        assertFalse(DateTimeUtil.isValidDate("01-01-2024 00:61"));
        assertFalse(DateTimeUtil.isValidDate("01-01-2024 24:00"));
    }


    @Test
    void formatDateTime_valid_success() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 1, 13, 59);
        assertEquals("01-01-2020 13:59", DateTimeUtil.formatDateTime(dateTime));
    }

    @Test
    void parseDateTime_valid_success() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 1, 13, 59);
        assertEquals(dateTime, DateTimeUtil.parseDateTime("01-01-2020 13:59"));
    }

    @Test
    void getCurrentTime_currentTime_success() {
        assertEquals(DateTimeUtil.formatDateTime(LocalDateTime.now()), DateTimeUtil.getCurrentTime());
    }
}

package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.JODA_TIME.assertThat;

import static org.joda.time.DateTimeZone.UTC;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * Joda Time assertions example.
 * 
 * @author Joel Costigliola
 */
public class JodaTimeAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void dateTime_assertions_examples() {

    assertThat(new DateTime("2000-01-01")).isEqualTo(new DateTime("2000-01-01")).isNotEqualTo(
        new DateTime("2000-01-15"));
    // same assertions but parameters is String based representation of DateTime
    assertThat(new DateTime("2000-01-01")).isEqualTo("2000-01-01").isNotEqualTo("2000-01-15");

    assertThat(new DateTime("2000-01-01")).isBeforeOrEqualTo(new DateTime("2000-01-01"));
    assertThat(new DateTime("2000-01-01")).isAfterOrEqualTo(new DateTime("2000-01-01"));
    assertThat(new DateTime("2000-01-01")).isBefore(new DateTime("2000-01-02")).isAfter(new DateTime("1999-12-31"));
    // same assertions but parameters is String based representation of DateTime
    assertThat(new DateTime("2000-01-01")).isBefore("2000-01-02").isAfter("1999-12-31");

    assertThat(new DateTime("2000-01-01")).isIn(new DateTime("1999-12-31"), new DateTime("2000-01-01"));
    assertThat(new DateTime("2000-01-01")).isNotIn(new DateTime("1999-12-31"), new DateTime("2000-01-02"));
    // same assertions but parameters is String based representation of DateTime
    assertThat(new DateTime("2000-01-01")).isIn("1999-12-31", "2000-01-01").isNotIn("1999-12-31", "2000-01-02");
  }

  @Test
  public void localDateTime_assertions_examples() {

    assertThat(new LocalDateTime("2000-01-01")).isEqualTo(new LocalDateTime("2000-01-01")).isNotEqualTo(
        new LocalDateTime("2000-01-15"));
    // same assertions but parameters is String based representation of LocalDateTime
    assertThat(new LocalDateTime("2000-01-01")).isEqualTo("2000-01-01").isNotEqualTo("2000-01-15");

    assertThat(new LocalDateTime("2000-01-01")).isBefore(new LocalDateTime("2000-01-02")).isAfter(
        new LocalDateTime("1999-12-31"));
    // same assertions but parameters is String based representation of LocalDateTime
    assertThat(new LocalDateTime("2000-01-01")).isBefore("2000-01-02").isAfter("1999-12-31");

    assertThat(new LocalDateTime("2000-01-01")).isIn(new LocalDateTime("1999-12-31"), new LocalDateTime("2000-01-01"));
    assertThat(new LocalDateTime("2000-01-01")).isNotIn(new LocalDateTime("1999-12-31"),
        new LocalDateTime("2000-01-02"));
    // same assertions but parameters is String based representation of LocalDateTime
    assertThat(new LocalDateTime("2000-01-01")).isIn("1999-12-31", "2000-01-01").isNotIn("1999-12-31", "2000-01-02");
  }

  @Test
  public void mixing_core_and_joda_time_assertions_examples() {

    // assertThat comes from org.fest.assertions.api.JODA_TIME.assertThat static import
    assertThat(new DateTime("2000-01-01")).isEqualTo(new DateTime("2000-01-01"));

    // assertThat comes from org.fest.assertions.api.Assertions.assertThat static import
    assertThat("hello world").startsWith("hello");

    // let's see if ShouldBeAfter error
    try {
      assertThat(new DateTime(10)).isAfter(new DateTime(1000));
    } catch (AssertionError e) {
      logAssertionErrorMessage("isAfter", e);
    }
  }

  @Test
  public void date_time_comparison_with_precision_level_examples() {
    // successfull assertions ignoring ...
    // ... milliseconds
    DateTime dateTime1 = new DateTime(2000, 1, 1, 0, 0, 1, 0, UTC);
    DateTime dateTime2 = new DateTime(2000, 1, 1, 0, 0, 1, 456, UTC);
    assertThat(dateTime1).isEqualToIgnoringMillis(dateTime2);
    // ... seconds
    dateTime1 = new DateTime(2000, 1, 1, 23, 50, 0, 0, UTC);
    dateTime2 = new DateTime(2000, 1, 1, 23, 50, 10, 456, UTC);
    assertThat(dateTime1).isEqualToIgnoringSeconds(dateTime2);
    // ... minutes
    dateTime1 = new DateTime(2000, 1, 1, 23, 50, 0, 0, UTC);
    dateTime2 = new DateTime(2000, 1, 1, 23, 00, 2, 7, UTC);
    assertThat(dateTime1).isEqualToIgnoringMinutes(dateTime2);
    // ... hours
    dateTime1 = new DateTime(2000, 1, 1, 23, 59, 59, 999, UTC);
    dateTime2 = new DateTime(2000, 1, 1, 00, 00, 00, 000, UTC);
    assertThat(dateTime1).isEqualToIgnoringHours(dateTime2);

    // failing assertions even if time difference is 1ms (compared fields differ)
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 1, 0, 0, 1, 0);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 0, 0, 0, 999);
      assertThat(dateTimeA).isEqualToIgnoringMillis(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringMillis", e);
    }
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 1, 23, 50, 00, 000);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 23, 49, 59, 999);
      assertThat(dateTimeA).isEqualToIgnoringSeconds(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringSeconds", e);
    }
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 1, 01, 00, 00, 000);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 00, 59, 59, 999);
      assertThat(dateTimeA).isEqualToIgnoringMinutes(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringMinutes", e);
    }
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 2, 00, 00, 00, 000);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 23, 59, 59, 999);
      assertThat(dateTimeA).isEqualToIgnoringHours(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringHours", e);
    }
  }
  
  @Test
  public void local_date_time_comparison_with_precision_level_examples() {
    // successfull assertions ignoring ...
    // ... milliseconds
    LocalDateTime localDateTime1 = new LocalDateTime(2000, 1, 1, 0, 0, 1, 0);
    LocalDateTime localDateTime2 = new LocalDateTime(2000, 1, 1, 0, 0, 1, 456);
    assertThat(localDateTime1).isEqualToIgnoringMillis(localDateTime2);
    // ... seconds
    localDateTime1 = new LocalDateTime(2000, 1, 1, 23, 50, 0, 0);
    localDateTime2 = new LocalDateTime(2000, 1, 1, 23, 50, 10, 456);
    assertThat(localDateTime1).isEqualToIgnoringSeconds(localDateTime2);
    // ... minutes
    localDateTime1 = new LocalDateTime(2000, 1, 1, 23, 50, 0, 0);
    localDateTime2 = new LocalDateTime(2000, 1, 1, 23, 00, 2, 7);
    assertThat(localDateTime1).isEqualToIgnoringMinutes(localDateTime2);
    // ... hours
    localDateTime1 = new LocalDateTime(2000, 1, 1, 23, 59, 59, 999);
    localDateTime2 = new LocalDateTime(2000, 1, 1, 00, 00, 00, 000);
    assertThat(localDateTime1).isEqualToIgnoringHours(localDateTime2);
    
    // failing assertions even if time difference is 1ms (compared fields differ)
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 1, 0, 0, 1, 0);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 0, 0, 0, 999);
      assertThat(localDateTimeA).isEqualToIgnoringMillis(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringMillis", e);
    }
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 1, 23, 50, 00, 000);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 23, 49, 59, 999);
      assertThat(localDateTimeA).isEqualToIgnoringSeconds(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringSeconds", e);
    }
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 1, 01, 00, 00, 000);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 00, 59, 59, 999);
      assertThat(localDateTimeA).isEqualToIgnoringMinutes(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringMinutes", e);
    }
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 2, 00, 00, 00, 000);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 23, 59, 59, 999);
      assertThat(localDateTimeA).isEqualToIgnoringHours(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringHours", e);
    }
  }

}

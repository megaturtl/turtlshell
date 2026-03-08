package cc.turtl.turtlshell.util.format

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class StringFormatsTest {

    @Nested
    internal inner class CapitalizeFirst {

        @Test
        fun `lowercases and capitalizes a simple word`() {
            assertEquals("Hello", "hello".capitalizeFirst())
        }

        @Test
        fun `lowercases an all-caps word before capitalizing`() {
            assertEquals("Hello", "HELLO".capitalizeFirst())
        }

        @Test
        fun `handles a single character`() {
            assertEquals("A", "a".capitalizeFirst())
        }

        @Test
        fun `handles an empty string`() {
            assertEquals("", "".capitalizeFirst())
        }

        @Test
        fun `lowercases trailing characters`() {
            assertEquals("Hello world", "hELLO wORLD".capitalizeFirst())
        }
    }

    @Nested
    internal inner class FormatPercentage {

        @Test
        fun `formats 1 as 100 percent`() {
            assertEquals("100.00%", formatPercentage(1.0))
        }

        @Test
        fun `formats 0 as 0 percent`() {
            assertEquals("0.00%", formatPercentage(0.0))
        }

        @Test
        fun `formats a half value to two decimal places`() {
            assertEquals("50.00%", formatPercentage(0.5))
        }

        @Test
        fun `rounds to two decimal places`() {
            assertEquals("33.33%", formatPercentage(1.0 / 3.0))
        }

        @Test
        fun `handles values above 1`() {
            assertEquals("150.00%", formatPercentage(1.5))
        }

        @Test
        fun `handles negative values`() {
            assertEquals("-25.00%", formatPercentage(-0.25))
        }
    }

    @Nested
    internal inner class FormatDecimal {

        @Test
        fun `defaults to two decimal places`() {
            assertEquals("3.14", formatDecimal(3.14159))
        }

        @Test
        fun `respects a custom number of places`() {
            assertEquals("3.14159", formatDecimal(3.14159, 5))
        }

        @Test
        fun `formats zero decimal places as integer`() {
            assertEquals("4", formatDecimal(3.7, 0))
        }

        @Test
        fun `pads with zeros when value is exact`() {
            assertEquals("2.00", formatDecimal(2.0))
        }

        @Test
        fun `handles negative values`() {
            assertEquals("-1.50", formatDecimal(-1.5))
        }
    }

    @Nested
    internal inner class FormatDuration {

        @Test
        fun `formats seconds only when under a minute`() {
            assertEquals("30s", formatDuration(30_000))
        }

        @Test
        fun `formats minutes and seconds`() {
            assertEquals("2m 5s", formatDuration(125_000))
        }

        @Test
        fun `formats hours, minutes and seconds`() {
            assertEquals("1h 30m 45s", formatDuration(5_445_000))
        }

        @Test
        fun `omits hours when zero`() {
            assertEquals("5m 0s", formatDuration(300_000))
        }

        @Test
        fun `omits minutes when zero but hours present`() {
            assertEquals("2h 0s", formatDuration(7_200_000))
        }

        @Test
        fun `formats zero milliseconds as zero seconds`() {
            assertEquals("0s", formatDuration(0))
        }
    }

    @Nested
    internal inner class FormatBytes {

        @Test
        fun `returns N_A for negative values`() {
            assertEquals("N/A", formatBytes(-1))
        }

        @Test
        fun `formats zero as bytes`() {
            assertEquals("0 B", formatBytes(0))
        }

        @Test
        fun `formats values under 1 KB as bytes`() {
            assertEquals("512 B", formatBytes(512))
        }

        @Test
        fun `formats exactly 1 KB`() {
            assertEquals("1.0 KB", formatBytes(1024))
        }

        @Test
        fun `formats values in the KB range`() {
            assertEquals("1.5 KB", formatBytes(1536))
        }

        @Test
        fun `formats exactly 1 MB`() {
            assertEquals("1.0 MB", formatBytes(1024 * 1024))
        }

        @Test
        fun `formats values in the MB range`() {
            assertEquals("2.5 MB", formatBytes(1024 * 1024 * 2 + 1024 * 512))
        }

        @Test
        fun `formats exactly 1 GB`() {
            assertEquals("1.00 GB", formatBytes(1024L * 1024 * 1024))
        }

        @Test
        fun `formats values in the GB range to two decimal places`() {
            assertEquals("1.50 GB", formatBytes(1024L * 1024 * 1024 + 1024L * 1024 * 512))
        }

        @Test
        fun `formats 1023 bytes as bytes not KB`() {
            assertEquals("1023 B", formatBytes(1023))
        }
    }

    @Nested
    internal inner class SnakeCaseToTitleCase {

        @Test
        fun `converts a simple snake_case string`() {
            assertEquals("Water Type", snakeCaseToTitleCase("water_type"))
        }

        @Test
        fun `handles a single word with no underscores`() {
            assertEquals("Hello", snakeCaseToTitleCase("hello"))
        }

        @Test
        fun `handles multiple words`() {
            assertEquals("One Two Three", snakeCaseToTitleCase("one_two_three"))
        }

        @Test
        fun `handles leading underscores`() {
            assertEquals("Hello", snakeCaseToTitleCase("_hello"))
        }

        @Test
        fun `handles trailing underscores`() {
            assertEquals("Hello", snakeCaseToTitleCase("hello_"))
        }

        @Test
        fun `handles consecutive underscores`() {
            assertEquals("Hello World", snakeCaseToTitleCase("hello__world"))
        }

        @Test
        fun `handles an empty string`() {
            assertEquals("", snakeCaseToTitleCase(""))
        }

        @Test
        fun `uppercases first letter of each word`() {
            assertEquals("Foo Bar Baz", snakeCaseToTitleCase("FOO_BAR_BAZ"))
        }
    }
}
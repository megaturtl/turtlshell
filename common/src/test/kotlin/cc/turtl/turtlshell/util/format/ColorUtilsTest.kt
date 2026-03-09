package cc.turtl.turtlshell.util.format

import net.minecraft.ChatFormatting
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ColorUtilsTest {

    @Nested
    internal inner class ToArgb {

        @Test
        fun `packs rgb with rounded alpha on happy path`() {
            assertEquals(0x80123456.toInt(), toArgb(0x123456, 0.5f))
        }

        @Test
        fun `clamps opacity below zero to fully transparent`() {
            assertEquals(0x00123456, toArgb(0x123456, -0.25f))
        }

        @Test
        fun `clamps opacity above one to fully opaque`() {
            assertEquals(0xFF123456.toInt(), toArgb(0x123456, 1.25f))
        }

        @Test
        fun `strips incoming alpha bits from rgb`() {
            assertEquals(0xFF112233.toInt(), toArgb(0xAB112233.toInt(), 1f))
        }
    }

    @Nested
    internal inner class GetClosestLegacy {

        @Test
        fun `returns exact match for black`() {
            assertEquals(ChatFormatting.BLACK, getClosestLegacy(0x000000))
        }

        @Test
        fun `returns exact match for gold`() {
            assertEquals(ChatFormatting.GOLD, getClosestLegacy(0xFFAA00))
        }

        @Test
        fun `returns exact match for white`() {
            assertEquals(ChatFormatting.WHITE, getClosestLegacy(0xFFFFFF))
        }

        @Test
        fun `maps light green to GREEN`() {
            assertEquals(ChatFormatting.GREEN, getClosestLegacy(0x27D655))
        }

        @Test
        fun `maps dark blue to DARK_BLUE`() {
            assertEquals(ChatFormatting.DARK_BLUE, getClosestLegacy(0x0211B8))
        }

        @Test
        fun `maps dark red to DARK_RED`() {
            assertEquals(ChatFormatting.DARK_RED, getClosestLegacy(0x851405))
        }

        @Test
        fun `maps light gray to GRAY`() {
            assertEquals(ChatFormatting.GRAY, getClosestLegacy(0xABABAB))
        }
    }

    @Nested
    internal inner class GetGradient {

        @Nested
        internal inner class EdgeCases {

            @Test
            fun `returns white when no colors provided`() {
                assertEquals(0xFFFFFF, getRatioColor(0.5f))
            }

            @Test
            fun `returns sole color for any ratio`() {
                assertEquals(0xABCDEF, getRatioColor(0.3f, 0xABCDEF))
            }
        }

        @Nested
        internal inner class Clamping {

            @Test
            fun `returns first color at ratio zero`() {
                assertEquals(0xFF0000, getRatioColor(0f, 0xFF0000, 0x0000FF))
            }

            @Test
            fun `returns last color at ratio one`() {
                assertEquals(0x0000FF, getRatioColor(1f, 0xFF0000, 0x0000FF))
            }

            @Test
            fun `clamps ratio below zero to first color`() {
                assertEquals(0xFF0000, getRatioColor(-0.1f, 0xFF0000, 0x00FF00, 0x0000FF))
            }

            @Test
            fun `clamps ratio above one to last color`() {
                assertEquals(0x0000FF, getRatioColor(1.1f, 0xFF0000, 0x00FF00, 0x0000FF))
            }
        }

        @Nested
        internal inner class Interpolation {

            @Test
            fun `returns middle stop exactly at segment boundary`() {
                assertEquals(0x00FF00, getRatioColor(0.5f, 0xFF0000, 0x00FF00, 0x0000FF))
            }

            @Test
            fun `interpolates first segment midpoint`() {
                // lerp truncates: (0xFF + 0x00) * 0.5 = 127 = 0x7F, not 0x80
                assertEquals(0x7F7F00, getRatioColor(0.25f, 0xFF0000, 0x00FF00, 0x0000FF))
            }

            @Test
            fun `interpolates second segment midpoint`() {
                assertEquals(0x007F7F, getRatioColor(0.75f, 0xFF0000, 0x00FF00, 0x0000FF))
            }
        }
    }
}
package cc.turtl.turtlshell.api.core.format

import java.awt.Color

/**
 * A collection of commonly used colors.
 */
object ColorLib {
    // --- Basic Colors ---
    val WHITE: Color = Color(0xFFFFFF)
    val BLACK: Color = Color(0x000000)
    val LIGHT_GRAY: Color = Color(0xAAAAAA)
    val DARK_GRAY: Color = Color(0x555555)

    // --- Rainbow Palette ---
    val RED: Color = Color(0xE13538)
    val ORANGE: Color = Color(0xF9844A)
    val YELLOW: Color = Color(0xF9C74F)
    val GREEN: Color = Color(0x41D73B)
    val BLUE: Color = Color(0x2D73B0)
    val PURPLE: Color = Color(0x6C44C3)
    val PINK: Color = Color(0xF46997)

    // --- Extended Palette ---
    val CRIMSON: Color = Color(0xDC143C)
    val CORAL: Color = Color(0xFF7F50)
    val GOLD: Color = Color(0xFFD700)
    val LIME: Color = Color(0x32CD32)
    val INDIGO: Color = Color(0x4B0082)
    val MAGENTA: Color = Color(0xFF00FF)
    val BROWN: Color = Color(0x8B4513)
    val AQUA: Color = Color(0x40E0D0)
    val LAVENDER: Color = Color(0xDEDEFC)
    val MINT: Color = Color(0x98FF98)
    val TEAL: Color = Color(0x008080)

    class Gradient(vararg val colors: Color) {
        fun rgb(): IntArray = colors.map { it.rgb }.toIntArray()
    }

    object Gradients {
        val POSITIVE = Gradient(RED, YELLOW, GREEN)
        val NEGATIVE = Gradient(GREEN, YELLOW, RED)
        val RAINBOW = Gradient(RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK)
    }
}
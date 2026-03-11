package cc.turtl.turtlshell.api.core.util

import net.minecraft.ChatFormatting
import kotlin.math.roundToInt


fun toArgb(rgb: Int, opacity: Float): Int {
    val alpha = (opacity.coerceIn(0f, 1f) * 255).roundToInt()
    return (alpha shl 24) or (rgb and 0x00FFFFFF)
}

/**
 * Finds the closest vanilla [ChatFormatting] color based on perceptual distance.
 */
fun getClosestLegacy(rgb: Int): ChatFormatting {
    val closestIndex = MC_LEGACY_PALETTE.indices.minBy { colorDistance(rgb, MC_LEGACY_PALETTE[it]) }
    return ChatFormatting.getById(closestIndex) ?: ChatFormatting.WHITE
}

/**
 * Calculates a color at a specific point along a gradient.
 *
 * @param ratio The position in the gradient (0.0 to 1.0)
 * @param colors The RGB color stops defining the gradient
 * @return The interpolated RGB int
 */
fun getRatioColor(ratio: Float, vararg colors: Int): Int {
    if (colors.isEmpty()) return 0xFFFFFF
    if (colors.size == 1) return colors[0]

    // Scale the ratio to fit the range of color stops
    // e.g., 3 colors = 2 segments, so ratio is scaled from 0-1 to 0-2
    val scaledRatio = ratio.coerceIn(0f, 1f) * (colors.size - 1)

    // Integer part of scaledRatio = Which segment we're in
    // Clamped so scaledRatio = 2.0 lands in the last segment, not out of bounds
    val segmentLeftIndex = scaledRatio.toInt().coerceAtMost(colors.size - 2)
    val segmentRightIndex = segmentLeftIndex + 1

    val leftColor = colors[segmentLeftIndex]
    val rightColor = colors[segmentRightIndex]

    // Position within the segment
    val ratioWithinSegment = scaledRatio - segmentLeftIndex

    val red = lerp(leftColor.red(), rightColor.red(), ratioWithinSegment)
    val green = lerp(leftColor.green(), rightColor.green(), ratioWithinSegment)
    val blue = lerp(leftColor.blue(), rightColor.blue(), ratioWithinSegment)

    return (red shl 16) or (green shl 8) or blue // pack as 0xRRGGBB
}

private val MC_LEGACY_PALETTE = intArrayOf(
    0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA,
    0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
)

private fun Int.red() = (this shr 16) and 0xFF
private fun Int.green() = (this shr 8) and 0xFF
private fun Int.blue() = this and 0xFF

/**
 * Weighted Euclidean approximation to get perceptual distance (low-cost variant of Redmean)
 */
private fun colorDistance(a: Int, b: Int): Long {
    val redDist = a.red() - b.red()
    val greenDist = a.green() - b.green()
    val blueDist = a.blue() - b.blue()
    return 2L * redDist * redDist + 4L * greenDist * greenDist + 3L * blueDist * blueDist
}


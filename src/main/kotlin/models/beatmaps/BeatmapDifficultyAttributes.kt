package models.beatmaps

import kotlinx.serialization.Serializable

@Serializable
open class BeatmapDifficultyAttributes(
    val starRating: Float? = null,
    val maxCombo: Int? = null,
    val aimDifficulty: Float? = null,
    val aimDifficultSliderCount: Float? = null,
    val speedDifficulty: Float? = null,
    val speedNoteCount: Float? = null,
    val sliderFactor: Float? = null,
    val aimDifficultStrainCount: Float? = null,
    val speedDifficultStrainCount: Float? = null,
    val monoStaminaFactor: Float? = null,
)
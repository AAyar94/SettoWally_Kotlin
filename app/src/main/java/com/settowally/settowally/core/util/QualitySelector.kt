package com.settowally.settowally.core.util

sealed class QualitySelector(val name: String) {
    data object Landscape : QualitySelector("landscape")
    data object Large : QualitySelector("large")
    data object Large2x : QualitySelector("large2x")
    data object Medium : QualitySelector("medium")
    data object Original : QualitySelector("original")
    data object Portrait : QualitySelector("portrait")
    data object Small : QualitySelector("small")
    data object Tiny : QualitySelector("tiny")

    companion object {
        fun fromString(name: String): QualitySelector {
            return when (name) {
                "landscape" -> Landscape
                "large" -> Large
                "large2x" -> Large2x
                "medium" -> Medium
                "original" -> Original
                "portrait" -> Portrait
                "small" -> Small
                "tiny" -> Tiny
                else -> {
                    Medium
                }
            }
        }
    }
}

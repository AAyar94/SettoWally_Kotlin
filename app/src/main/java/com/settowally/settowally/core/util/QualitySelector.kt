package com.settowally.settowally.core.util

sealed class QualitySelector(name: String) {
    class Landscape : QualitySelector("landscape")
    class Large : QualitySelector("large")
    class Large2x : QualitySelector("large2x")
    class Medium : QualitySelector("medium")
    class Original : QualitySelector("original")
    class Portrait : QualitySelector("portrait")
    class Small : QualitySelector("small")
    class Tiny : QualitySelector("tiny")
}
package com.settowally.settowally.domain.model

import androidx.compose.ui.text.font.FontWeight
import com.settowally.settowally.data.model.Src

data class SinglePhotoModel(
    val id:Int,
    val avgColor:String,
    val height:Int,
    val width:Int,
    val photographer:String,
    val linkSource: Src,
)
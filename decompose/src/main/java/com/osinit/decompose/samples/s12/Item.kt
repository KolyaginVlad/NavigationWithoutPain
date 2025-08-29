package com.osinit.decompose.samples.s12

import kotlinx.serialization.Serializable

@Serializable
internal data class Item(
    val stringField: String,
    val intField: Int,
    val nullableIntField: Int?,
)
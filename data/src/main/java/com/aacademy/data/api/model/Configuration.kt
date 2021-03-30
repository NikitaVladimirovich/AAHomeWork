package com.aacademy.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    @SerialName("images")
    val images: Images,
    @SerialName("change_keys")
    val changeKeys: List<String>
)

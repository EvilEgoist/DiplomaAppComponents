package ru.diploma.appcomponents.imageGallery.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SomeError(
    val errors: List<String>
)
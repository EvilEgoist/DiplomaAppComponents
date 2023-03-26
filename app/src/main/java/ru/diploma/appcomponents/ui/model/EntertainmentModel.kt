package ru.diploma.appcomponents.ui.model

data class EntertainmentModel(
    val type: EntertainmentType,
    val time: String,
    val date: String,
    val mainText: String,
    val logoUrl: String,
    val name: String,
    val address: String,
    val description: String,
)

enum class EntertainmentType{
    RESTAURANT,
    BAR,
    CINEMA,
    MUSEUM,
    PARK,
    THEATER,
    OTHER
}
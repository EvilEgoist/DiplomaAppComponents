package ru.diploma.appcomponents.imageGallery.domain.model

data class UnsplashImage(
    val id: String,
    val color: String,
    val urls: Urls,
    val likes: Int,
    val author: Author
)

data class Urls(
    val imageUrl: String
)

data class Author(
    val authorLinks: AuthorLinks,
    val authorName: String
)

data class AuthorLinks(
    val html: String
)

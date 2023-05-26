package ru.diploma.appcomponents.imageGallery.data.models.mapper

import ru.diploma.appcomponents.imageGallery.data.models.*
import ru.diploma.appcomponents.imageGallery.data.models.localonly.SearchHistoryDbModel
import ru.diploma.appcomponents.imageGallery.data.models.localonly.UnsplashRemoteKeysDb
import ru.diploma.appcomponents.imageGallery.domain.model.*

fun AuthorLinksResponse.toDomainModel() = AuthorLinks(
    html = html
)

fun AuthorResponse.toDomainModel() = Author(
    authorLinks = authorLinks.toDomainModel(),
    authorName = authorName
)

fun UrlsResponse.toDomainModel() = Urls(
    imageUrl = imageUrl
)

fun UnsplashImageResponse.toDomainModel() = UnsplashImage(
    id = id,
    color = color,
    urls = urls.toDomainModel(),
    likes = likes,
    author = author.toDomainModel(),
)

fun UnsplashRemoteKeysDb.toDomainModel() = UnsplashRemoteKeys(
    id = id,
    prevPage = prevPage,
    nextPage = nextPage
)

fun SearchHistoryDbModel.toDomainModel() = SearchHistoryModel(
    id = id,
    query = query
)


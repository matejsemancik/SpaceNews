package dev.matsem.spacenews.shared.feature.home.model

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.matsem.spacenews.shared.arch.resource.desc
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.ArticleId

data class ArticleListItem(
    val id: ArticleId,
    val date: StringDesc,
    val title: StringDesc,
    val thumbnailUrl: String,
) {
    companion object {
        fun mocks() = Article.mocks().map { it.toUiListItem() }
    }
}

internal fun Article.toUiListItem() = ArticleListItem(
    id = id,
    date = publishedAt.desc("MMMd HHmm zzz"),
    title = title.desc(),
    thumbnailUrl = imageUrl,
)

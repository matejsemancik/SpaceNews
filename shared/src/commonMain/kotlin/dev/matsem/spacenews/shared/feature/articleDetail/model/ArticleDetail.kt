package dev.matsem.spacenews.shared.feature.articleDetail.model

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.format
import dev.matsem.spacenews.resources.MR
import dev.matsem.spacenews.shared.arch.resource.desc
import dev.matsem.spacenews.shared.data.repo.model.Article

data class ArticleDetail(
    val title: StringDesc,
    val date: StringDesc,
    val imageUrl: String,
    val summary: String,
    val url: String,
    val ctaButtonText: StringDesc,
) {
    companion object {
        fun mocks() = Article.mocks().map { it.toUiDetail() }
    }
}

fun Article.toUiDetail() = ArticleDetail(
    title = title.desc(),
    date = publishedAt.desc("MMMd HHmm zzz"),
    imageUrl = imageUrl,
    summary = summary,
    url = url,
    ctaButtonText = MR.strings.article_detail_cta.format(newsSiteName)
)

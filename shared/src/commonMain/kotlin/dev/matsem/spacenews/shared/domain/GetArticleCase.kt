package dev.matsem.spacenews.shared.domain

import dev.matsem.spacenews.shared.arch.useCase.FlowUseCase
import dev.matsem.spacenews.shared.data.repo.ArticleRepo
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import kotlinx.coroutines.flow.Flow

internal class GetArticleCase(
    private val repo: ArticleRepo,
) : FlowUseCase<ArticleId, Article>() {

    override fun invoke(input: ArticleId): Flow<Article> = repo.getArticle(input)
}

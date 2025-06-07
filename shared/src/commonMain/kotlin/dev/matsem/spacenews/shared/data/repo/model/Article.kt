package dev.matsem.spacenews.shared.data.repo.model

import kotlinx.datetime.Instant

data class Article(
    val id: ArticleId,
    val title: String,
    val authors: List<String>,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: Instant,
) {
    companion object {
        fun mocks(): List<Article> {
            val sampleTitles = listOf(
                "NASA Announces New Mars Mission Discovery",
                "SpaceX Successfully Launches Starship to Orbit",
                "International Space Station Receives New Crew",
                "Astronomers Discover Earth-Like Exoplanet",
                "Hubble Telescope Captures Stunning Galaxy Image",
                "China's Lunar Rover Makes Breakthrough Finding",
                "ESA Plans Ambitious Moon Base Project",
                "Private Space Tourism Reaches New Milestone",
                "Solar Storm Activity Affects Satellite Communications",
                "Revolutionary Spacecraft Propulsion System Tested"
            )

            val sampleAuthors = listOf(
                listOf("Dr. Sarah Johnson", "Dr. Michael Chen"),
                listOf("Emily Rodriguez"),
                listOf("Prof. David Wilson", "Dr. Lisa Zhang", "Dr. James Brown"),
                listOf("Dr. Maria Garcia"),
                listOf("Dr. Robert Taylor", "Dr. Amanda White"),
                listOf("Dr. Kevin Liu"),
                listOf("Dr. Sophie Martin", "Dr. Alex Thompson"),
                listOf("Dr. Rachel Davis"),
                listOf("Dr. Mark Anderson", "Dr. Jennifer Lee"),
                listOf("Dr. Thomas Clark")
            )

            val sampleSummaries = listOf(
                "Scientists at NASA have made a groundbreaking discovery on Mars that could change our understanding of the Red Planet's geological history.",
                "SpaceX's latest Starship mission achieved a successful orbital flight, marking a significant milestone in commercial space exploration.",
                "The International Space Station welcomed a new crew of astronauts from multiple countries for a six-month research mission.",
                "Astronomers using advanced telescopes have identified a potentially habitable exoplanet located in the Goldilocks zone of its star system.",
                "The Hubble Space Telescope has captured breathtaking images of a distant galaxy, revealing new insights into cosmic evolution.",
                "China's lunar rover has discovered unusual mineral compositions that provide new clues about the Moon's formation.",
                "The European Space Agency has unveiled ambitious plans for establishing a permanent human presence on the Moon.",
                "Private companies are making space tourism more accessible, with successful test flights demonstrating safety and reliability.",
                "A powerful solar storm has disrupted satellite communications worldwide, highlighting the importance of space weather monitoring.",
                "Engineers have successfully tested a revolutionary ion propulsion system that could dramatically reduce travel time to Mars."
            )

            return (1..10).map { index ->
                Article(
                    id = ArticleId(index),
                    title = sampleTitles[index - 1],
                    authors = sampleAuthors[index - 1],
                    url = "https://spacenews.com/article-${index}",
                    imageUrl = "https://spacenews.com/images/article-${index}.jpg",
                    summary = sampleSummaries[index - 1],
                    publishedAt = Instant.fromEpochSeconds(1704067200L + (index * 86400L)) // Starting from 2024-01-01, each article one day apart
                )
            }
        }
    }
}

package dev.matsem.spacenews.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

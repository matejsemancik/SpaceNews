package dev.matsem.spacenews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
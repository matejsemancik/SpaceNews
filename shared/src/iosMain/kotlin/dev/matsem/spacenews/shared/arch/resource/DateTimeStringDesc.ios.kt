package dev.matsem.spacenews.shared.arch.resource

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Instant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter

actual class DateTimeStringDesc actual constructor(private val instant: Instant, private val pattern: String) : StringDesc {

    private val formatter by lazy { NSDateFormatter() }

    override fun localized(): String = formatter.run {
        setLocalizedDateFormatFromTemplate(pattern)
        stringFromDate(instant.toNSDate())
    }
}

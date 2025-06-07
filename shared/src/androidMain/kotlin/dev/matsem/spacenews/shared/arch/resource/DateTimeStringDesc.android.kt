package dev.matsem.spacenews.shared.arch.resource

import android.content.Context
import android.icu.text.DateFormat
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.util.Date

actual class DateTimeStringDesc actual constructor(
    private val instant: Instant,
    private val pattern: String,
) : StringDesc {

    override fun toString(context: Context): String {
        return DateFormat
            .getInstanceForSkeleton(pattern, context.resources.configuration.locales[0])
            .format(Date.from(instant.toJavaInstant()))
    }
}

package yehor.tkachuk.mycv.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CVProfile(
    @Json(name = "name") val name: String = "",
    @Json(name = "position") val position: String = "",
    @Json(name = "about") val about: String = "",
    @Json(name = "education") val eduList: List<EducationItem> = emptyList(),
    @Json(name = "languages") val languages: List<Language> = emptyList(),
    @Json(name = "projects") val projectList: List<Project> = emptyList()
)

@JsonClass(generateAdapter = true)
data class EducationItem(
    val name: String = "",
    val degree: String = "",
    val faculty: String = "",
    val speciality: String = ""
)

@JsonClass(generateAdapter = true)
data class Language(
    val name: String = "",
    val code: String = "",
    val level: String = ""
) {
    fun getFlagImageUrl(): String {
        return "https://countryflagsapi.com/png/$code"
    }
}

@JsonClass(generateAdapter = true)
data class Project(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "title") val title: String = "",
    @Json(name = "desc_short") val descriptionShort: String = "",
    @Json(name = "position") val position: String = "",
    @Json(name = "desc_full") val description: String = "",
    @Json(name = "date_start") val startDate: String = "",
    @Json(name = "date_end") val endDate: String = "",
    @Json(name = "google_play_link") val googlePlayLink: String = "",
    @Json(name = "technologies") val technologies: String = "",
    @Json(name = "responsibility") val responsibility: String = ""
)
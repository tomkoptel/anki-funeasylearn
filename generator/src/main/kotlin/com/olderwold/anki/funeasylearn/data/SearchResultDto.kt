package com.olderwold.anki.funeasylearn.data

import com.google.gson.annotations.SerializedName

data class SearchResultDto(
    @SerializedName("data")
    val content: List<Data?>? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("search_id")
    val searchId: String? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null
) {
    data class Data(
        @SerializedName("aspect")
        val aspect: Double? = null,
        @SerializedName("assets")
        val assets: Assets? = null,
        @SerializedName("contributor")
        val contributor: Contributor? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("has_model_release")
        val hasModelRelease: Boolean? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("image_type")
        val imageType: String? = null,
        @SerializedName("media_type")
        val mediaType: String? = null
    ) {
        data class Assets(
            @SerializedName("huge_thumb")
            val hugeThumb: HugeThumb? = null,
            @SerializedName("large_thumb")
            val largeThumb: LargeThumb? = null,
            @SerializedName("preview")
            val preview: Preview? = null,
            @SerializedName("preview_1000")
            val preview1000: Preview1000? = null,
            @SerializedName("preview_1500")
            val preview1500: Preview1500? = null,
            @SerializedName("small_thumb")
            val smallThumb: SmallThumb? = null
        ) {
            data class HugeThumb(
                @SerializedName("height")
                val height: Int? = null,
                @SerializedName("url")
                val url: String? = null,
                @SerializedName("width")
                val width: Int? = null
            )

            data class LargeThumb(
                @SerializedName("height")
                val height: Int? = null,
                @SerializedName("url")
                val url: String? = null,
                @SerializedName("width")
                val width: Int? = null
            )

            data class Preview(
                @SerializedName("height")
                val height: Int? = null,
                @SerializedName("url")
                val url: String? = null,
                @SerializedName("width")
                val width: Int? = null
            )

            data class Preview1000(
                @SerializedName("height")
                val height: Int? = null,
                @SerializedName("url")
                val url: String? = null,
                @SerializedName("width")
                val width: Int? = null
            )

            data class Preview1500(
                @SerializedName("height")
                val height: Int? = null,
                @SerializedName("url")
                val url: String? = null,
                @SerializedName("width")
                val width: Int? = null
            )

            data class SmallThumb(
                @SerializedName("height")
                val height: Int? = null,
                @SerializedName("url")
                val url: String? = null,
                @SerializedName("width")
                val width: Int? = null
            )
        }

        data class Contributor(
            @SerializedName("id")
            val id: String? = null
        )
    }
}

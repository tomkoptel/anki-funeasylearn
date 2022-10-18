package com.olderwold.anki.funeasylearn

import com.google.gson.annotations.SerializedName

class DataSet : ArrayList<DataSet.DataSetItem>() {
    data class DataSetItem(
        @SerializedName("createdAt") val createdAt: CreatedAt? = null,
        @SerializedName("data") val `data`: Data? = null,
        @SerializedName("device") val device: String? = null,
        @SerializedName("exercise") val exercise: String? = null,
        @SerializedName("_id") val id: Id? = null,
        @SerializedName("label") val label: String? = null,
        @SerializedName("lang") val lang: String? = null,
        @SerializedName("params") val params: Params? = null,
        @SerializedName("ts") val ts: Double? = null,
        @SerializedName("type") val type: String? = null,
        @SerializedName("updatedAt") val updatedAt: UpdatedAt? = null,
        @SerializedName("user") val user: String? = null,
        @SerializedName("__v") val v: Double? = null
    ) {
        data class CreatedAt(
            @SerializedName("\$date") val date: String? = null
        )

        data class Data(
            @SerializedName("eda") val eda: List<List<Double>>? = null,
            @SerializedName("feedback") val feedback: String? = null,
            @SerializedName("scores") val scores: List<Any?>? = null
        )

        data class Id(
            @SerializedName("\$oid") val oid: String? = null
        )

        data class Params(
            @SerializedName("duration") val duration: Double? = null,
            @SerializedName("l_decr") val lDecr: Double? = null,
            @SerializedName("l_incr") val lIncr: Double? = null,
            @SerializedName("poDouble_mult") val poDoubleMult: Double? = null,
            @SerializedName("r_decr") val rDecr: Double? = null,
            @SerializedName("r_incr") val rIncr: Double? = null
        )

        data class UpdatedAt(
            @SerializedName("\$date") val date: String? = null
        )
    }
}

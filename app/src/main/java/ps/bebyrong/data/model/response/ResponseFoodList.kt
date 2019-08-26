package ps.bebyrong.data.model.response

data class ResponseFoodList(
        val data: List<DataItem>? = null,
        val diagnostic: Diagnostic? = null,
        val meta: Meta? = null,
        val links: Links? = null
) {
    data class DataItem(
            val dataKelompoktani: List<DataKelompoktaniItem>? = null,
            val nama: String? = "",
            val kelompokTani: String? = "",
            val id: String? = "",
            val gambar: String? = ""
    )

    data class DataKelompoktaniItem(
            val id: String? = "",
            val gambar: String? = ""
    )

}

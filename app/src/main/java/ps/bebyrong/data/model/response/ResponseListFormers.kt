package ps.bebyrong.data.model.response

data class ResponseListFormers(
        val data: List<DataItem>? = null,
        val diagnostic: Diagnostic? = null,
        val meta: Meta? = null,
        val links: Links? = null
) {
    data class DataItem(
            val nama: String? = "",
            val tanggalPanen: String? = "",
            val id: String? = "",
            val stok: String? = "",
            val gambar: String? = ""
    )
}

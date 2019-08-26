package ps.bebyrong.data.model.response

data class ResponseNews(
        val data: List<DataItem>? = null,
        val diagnostic: Diagnostic? = null,
        val meta: Meta? = null,
        val links: Links? = null
) {
    data class DataItem(
            val jumlahKomentar: String? = "",
            val waktu: String? = "",
            val kategori: String? = "",
            val id: String? = "",
            val judul: String? = "",
            val gambar: String? = ""
    )
}

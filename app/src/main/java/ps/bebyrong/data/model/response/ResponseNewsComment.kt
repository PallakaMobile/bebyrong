package ps.bebyrong.data.model.response

data class ResponseNewsComment(
        val data: List<DataItem>? = null,
        val meta: Meta? = null,
        val links: Links? = null,
        val diagnostic: Diagnostic? = null
) {
    data class DataItem(
            val nama: String? = null,
            val waktu: String? = null,
            val id: Int? = null,
            val idBerita: Int? = null,
            val gambar: String? = null,
            val isi: String? = null
    )
}

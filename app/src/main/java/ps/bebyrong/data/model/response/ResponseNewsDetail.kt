package ps.bebyrong.data.model.response

data class ResponseNewsDetail(
        val data: Data? = null,
        val diagnostic: Diagnostic? = null
) {
    data class Data(
            val waktu: String? = null,
            val id: Int? = null,
            val judul: String? = null,
            val gambar: String? = null,
            val isi: String? = null,
            val jmlShare: String? = null
    )
}

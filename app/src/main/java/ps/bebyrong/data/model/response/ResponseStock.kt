package ps.bebyrong.data.model.response

data class ResponseStock(
        val data: List<DataItem>? = null,
        val diagnostic: Diagnostic? = null,
        val meta: Meta? = null,
        val links: Links? = null
) {
    data class DataItem(
            val item: List<Any?>? = null,
            val nama: String? = null,
            val id: Int? = null,
            val gambar: String? = null,
            val alamat: String? = null
    )
}

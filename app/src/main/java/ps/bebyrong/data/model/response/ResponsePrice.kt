package ps.bebyrong.data.model.response

data class ResponsePrice(
        val data: List<DataItem>? = null,
        val diagnostic: Diagnostic? = null,
        val meta: Meta? = null,
        val links: Links? = null
) {
    data class DataItem(
            val nama: String? = null,
            val harga: String? = null,
            val id: Int? = null,
            val pasar: List<PasarItem>? = null,
            val gambar: String? = null
    )

    data class PasarItem(
            val idPasar: Int? = null,
            val nama: String? = null,
            val idPangan: Int? = null
    )

}

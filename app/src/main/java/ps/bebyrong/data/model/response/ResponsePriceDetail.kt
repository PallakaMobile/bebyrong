package ps.bebyrong.data.model.response

data class ResponsePriceDetail(
        val data: Data? = null
) {
    data class Data(
            val nama: String? = null,
            val harga: String? = null,
            val id: Int? = null,
            val listPasar: List<ListPasarItem>? = null,
            val gambar: String? = null
    )

    data class ListPasarItem(
            val kordinat: String? = null,
            val nama: String? = null,
            val harga: String? = null,
            val foto: String? = null,
            val satuan: String? = null,
            val id: Int? = null,
            val idPangan: Int? = null,
            val alamat: String? = null
    )
}

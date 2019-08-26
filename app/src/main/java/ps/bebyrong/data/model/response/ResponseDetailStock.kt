package ps.bebyrong.data.model.response

data class ResponseDetailStock(
        val diagnostic: Diagnostic? = null,
        val data: Data? = null
) {
    data class Data(
            val item: List<ItemItem>? = null,
            val nama: String? = null,
            val id: Int? = null,
            val gambar: String? = null,
            val alamat: String? = null
    )

    data class ItemItem(
            val stokMasuk: Int? = null,
            val itemPangan: String? = null,
            val id: Int? = null,
            val stokKeluar: Int? = null,
            val sisaStok: Int? = null
    )
}

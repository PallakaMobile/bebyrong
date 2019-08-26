package ps.bebyrong.data.model.response

data class ResponseFormersProduct(
        val data: List<DataItem>? = null
) {

    data class DataItem(
            val produk: String? = null,
            val tanggalPanen: String? = null,
            val id: Int? = null,
            val stok: String? = null,
            val gambar: String? = null,
            val status: String? = null
    )
}

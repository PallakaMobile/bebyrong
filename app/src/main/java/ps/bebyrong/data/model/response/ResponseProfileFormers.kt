package ps.bebyrong.data.model.response

data class ResponseProfileFormers(
        val data: Data? = null
) {
    data class Data(
            val nama: String? = null,
            val textPanen: String? = null,
            val namaKetua: String? = null,
            val id: Int? = null,
            val gambar: String? = null,
            val alamat: String? = null,
            val lokasi:String?=null,
            val telp:String?=null
    )
}

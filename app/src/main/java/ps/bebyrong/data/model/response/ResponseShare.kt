package ps.bebyrong.data.model.response

data class ResponseShare(
        val data: Data? = null
) {
    data class Data(
            val link: String? = null
    )
}

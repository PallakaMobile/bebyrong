package ps.bebyrong.data.model.response

data class ResponseYear(
        val diagnostic: Diagnostic? = null,
        val data: List<DataItem>? = null
) {
    data class DataItem(
            val tahun: String? = null
    ) {
        override fun toString(): String {
            return tahun.toString()
        }
    }
}

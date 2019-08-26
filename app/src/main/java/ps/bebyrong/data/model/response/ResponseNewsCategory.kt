package ps.bebyrong.data.model.response

data class ResponseNewsCategory(
	val data: List<DataItem?>? = null
){
	data class DataItem(
			val nama: String? = null,
			val id: Int? = null,
			val slug: Any? = null
	)

}

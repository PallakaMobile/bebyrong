package ps.bebyrong.data.source.rest

import io.reactivex.Flowable
import ps.bebyrong.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

//TODO Refactor this API interface
interface BebyrongAPI {

    @GET("list/pangan")
    fun getListPangan(@QueryMap queryMap: LinkedHashMap<String, String>): Flowable<Response<ResponseFoodList>>

    @GET("list/kelompok-tani")
    fun getListKelompokTani(@QueryMap queryMap: LinkedHashMap<String, String>)
            : Flowable<Response<ResponseListFormers>>

    @GET("profile/{id}")
    fun getProfileFormers(@Path("id") category: String, @Query("idPangan") idPangan: String)
            : Flowable<Response<ResponseProfileFormers>>

    @GET("profile/{id}/list-produk")
    fun getFormersProduct(@Path("id") idFormers: String): Flowable<Response<ResponseFormersProduct>>

    @GET("list/kategori-berita")
    fun getKategoriBerita(): Flowable<Response<ResponseNewsCategory>>

    @GET("list/berita")
    fun getBerita(@QueryMap queryMap: LinkedHashMap<String, String>)
            : Flowable<Response<ResponseNews>>

    @GET("share/berita")
    fun getShareLink(@Header("id") idBerita: String): Flowable<Response<ResponseShare>>

    @GET("berita/{id}/komentar")
    fun getNewsComment(@Path("id") id: String, @Query("page") page: String): Flowable<Response<ResponseNewsComment>>

    @GET("berita/{id}")
    fun getDetailBerita(@Path("id") id: String): Flowable<Response<ResponseNewsDetail>>

    @POST("berita/{id}/komentar")
    fun postComment(@Path("id") id: String, @HeaderMap headerMap: LinkedHashMap<String, String>): Flowable<Response<Diagnostic>>

    @GET("list/harga-pangan")
    fun getHargaPangan(@QueryMap queryMap: LinkedHashMap<String, String>): Flowable<Response<ResponsePrice>>

    @GET("detail/harga-pangan/{id}")
    fun getHargaPanganDetail(@Path("id") id: String): Flowable<Response<ResponsePriceDetail>>

    @GET("list/pasar")
    fun getListPasar(@Query("jenis") jenis: String, @Query("s") search: String): Flowable<Response<ResponseStock>>

    @GET("list/stok-pasar")
    fun getDetailStock(@QueryMap queryMap: LinkedHashMap<String, String>): Flowable<Response<ResponseDetailStock>>

    @GET("list/tahun")
    fun getListYear(): Flowable<Response<ResponseYear>>
}

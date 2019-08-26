package ps.bebyrong.ui.fragment.news.category.detail

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.e
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel
import ps.bebyrong.R
import ps.bebyrong.base.BaseFragment
import ps.bebyrong.data.model.response.ResponseNewsComment
import ps.bebyrong.databinding.FragmentNewsDetailBinding


/**
 **********************************************
 * Created by ukie on 4/18/19 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2019 | All Right Reserved
 */
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {
    private val viewModel by viewModel<NewsDetailViewModel>()

    private var currentPage = 1
    private var lastPage = 0
    private var isLoading = false


    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var listComment: MutableList<ResponseNewsComment.DataItem> = mutableListOf()

    private var isLogin = false
    private val SIGN_IN = 69

    private var adapter = NewsDetailAdapter()

    private var idNews = ""

    override fun getLayoutResource(): Int = R.layout.fragment_news_detail

    override fun myCodeHere() {
        dataBinding.lifecycleOwner = this
        activity.supportActionBar?.title = getString(R.string.detail_news)
        dataBinding.viewModel = viewModel
        idNews = arguments?.getString("idBerita") ?: "0"
        viewModel.getDetailNews(idNews)

        auth = FirebaseAuth.getInstance()

        dataBinding.rvComment.setHasFixedSize(true)
        dataBinding.rvComment.layoutManager = LinearLayoutManager(activity)
        dataBinding.rvComment.adapter = adapter

        //get all comment
        getComment(currentPage, true)

        dataBinding.swipeLayout.setOnRefreshListener {
            currentPage = 1
            getComment(currentPage, true)
        }

        dataBinding.swipeLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.color2),
                ContextCompat.getColor(activity, R.color.color3),
                ContextCompat.getColor(activity, R.color.color4)
        )

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        dataBinding.ivSend.setOnClickListener {
            if (dataBinding.etComment.text.toString().isNotEmpty()) {
                if (isLogin) {
                    //post comment when success login
                    viewModel.postComment(idNews,
                            linkedMapOf(
                                    "nama" to auth.currentUser?.displayName.toString(),
                                    "email" to auth.currentUser?.email.toString(),
                                    "imageUrl" to auth.currentUser?.photoUrl.toString(),
                                    "komentar" to dataBinding.etComment.text.toString()
                            )).observe(this, Observer { diagnostic ->
                        Toast.makeText(activity, diagnostic.message, Toast.LENGTH_SHORT).show()
                        getComment(currentPage, false)
                    })
                } else {
                    signIn()
                }
            }
        }

        //pagination in nested scrollview
        dataBinding.rvComment.isNestedScrollingEnabled = false
        dataBinding.svRoot.viewTreeObserver.addOnScrollChangedListener {
            val view = dataBinding.svRoot.getChildAt(dataBinding.svRoot.childCount - 1) as View
            val diff = view.bottom - (dataBinding.svRoot.height + dataBinding.svRoot.scrollY)

            if (diff == 0) {
                if (!isLoading && currentPage < lastPage) {
                    showLoading()
                    currentPage = currentPage.plus(1)
                    getComment(currentPage, false)
                }
            }
        }

        dataBinding.tvShare.setOnClickListener {
            viewModel.getShareLink(idNews).observe(this, Observer {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val shareBody = it.link
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(shareIntent, "Bagikan ke : "))
            })
        }
    }

    private fun getComment(page: Int, isFirst: Boolean) {
        viewModel.getNewsComment(idNews, page.toString(), isFirst).observe(this, Observer {
            listComment.addAll(it.data ?: throw Error("Error"))
            adapter.updateNewsDetailAdapter(listComment)
            dataBinding.tvCommentCount.text = "${it.meta?.total} Komentar"
            dataBinding.etComment.setText("")
            lastPage = it.meta?.last_page ?: 0
            hideLoading()
            dataBinding.swipeLayout.isRefreshing = false
        })
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        isLogin = auth.currentUser != null
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                e { "Google sign in failed" }
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        e { "firebaseAuthWithGoogle: ${acct.id}" }

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        e { "signInWithCredential:success" }
                        val user = auth.currentUser

                        //post comment when success login
                        viewModel.postComment(idNews,
                                linkedMapOf(
                                        "nama" to user?.displayName.toString(),
                                        "email" to user?.email.toString(),
                                        "imageUrl" to user?.photoUrl.toString(),
                                        "komentar" to dataBinding.etComment.text.toString()
                                )).observe(this, Observer { diagnostic ->
                            Toast.makeText(activity, diagnostic.message, Toast.LENGTH_SHORT).show()
                            getComment(currentPage, false)
                        })

                        e { "user $user" }
                    } else {
                        // If sign in fails, display a message to the user.
                        e { "signInWithCredential:failure ${task.exception}" }
                    }

                }
    }


    private fun showLoading() {
        isLoading = true
    }

    private fun hideLoading() {
        isLoading = false
    }


    override fun onResume() {
        super.onResume()
        dataBinding.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        dataBinding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }
}
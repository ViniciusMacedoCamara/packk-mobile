package com.app.packkbook.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.packkbook.Adapters.CommentAdapter
import com.app.packkbook.Models.Comment
import com.app.packkbook.Requests.GeneralRequests
import com.app.packkbook.databinding.ActivityCommentsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class CommentsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommentsBinding
    private var comments = ArrayList<Comment>()
    private val adapter = CommentAdapter(comments)
    var postId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getIntExtra("postId", 1)

        val recyclerViewComments = binding.recyclerViewComments

        recyclerViewComments.layoutManager = LinearLayoutManager(this)

        recyclerViewComments.adapter = adapter

        loadComments(postId!!, adapter)

        val addComment = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){

            val result = it.data
            val commentJSON = JSONObject(result!!.getStringExtra("comment")!!)
            val comment = Comment.fromJson(commentJSON)

            comment.postId = postId

            comments.add(comment)

            GlobalScope.launch(Dispatchers.IO) {
                GeneralRequests.postComment(comment)
            }

            storeCommentInStorage(comment)

            adapter.updateReceiptsList(comments)

            binding.recyclerViewComments.scrollToPosition(adapter.itemCount - 1)

        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddCommentsActivity::class.java)
            addComment.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        //loadComments(postId!!, adapter)
    }

    private fun loadComments(postId: Int, adapter: CommentAdapter){

        GlobalScope.launch(Dispatchers.IO) {

            GeneralRequests.getComments(postId, comments.toMutableList()) {

                runOnUiThread {
                    comments = it as ArrayList<Comment>
                    val storedComments = getCommentsFromStorage(postId)

                    if (storedComments.isNotEmpty()){
                        for(value in storedComments){
                            comments.add(value)
                        }
                    }
                    adapter.updateReceiptsList(comments)
                }

            }
        }

    }

    private fun storeCommentInStorage(comment: Comment) {

        val sharedPreferences = getSharedPreferences("comments:${comment.postId}", MODE_PRIVATE)

        val commentsFromStorage = sharedPreferences.getString("comments", null)

        if (commentsFromStorage != null) {

            val myEdit = sharedPreferences.edit()
            myEdit.putString("comments", commentsFromStorage + comment.toJson().toString() + ",")

            myEdit.apply()

        } else {

            val myEdit = sharedPreferences.edit()
            myEdit.putString("comments", "[" + comment.toJson().toString() + ",")

            myEdit.apply()

        }
    }

    private fun getCommentsFromStorage(postId: Int) : ArrayList<Comment> {

        val sharedPreferences = getSharedPreferences("comments:${postId}", MODE_PRIVATE)

        val comments = ArrayList<Comment>()

        val storedComments = sharedPreferences.getString("comments", null)

        if(storedComments != null){

            // ] added to be possible to convert as a JSONARRAY
            val commentsFromStorage = JSONArray("$storedComments]")

            for (index in 0 until commentsFromStorage.length() - 1){

                val comment = commentsFromStorage.get(index) as JSONObject

                comments.add(Comment.fromJson(comment))

            }

        }

        return comments

    }
}
package com.app.packkbook.Requests

import com.app.packkbook.Models.Comment
import com.app.packkbook.Models.Post
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object GeneralRequests {

    fun getPosts(posts: MutableList<Post>, callBack: (List<Post>) -> Unit){

        val URL = "https://jsonplaceholder.typicode.com/posts"

        val request = Request.Builder().url(URL).get().build()
        val client = OkHttpClient.Builder().build()

        client.newCall(request).execute().use { response ->

            val json = JSONArray(response.body!!.string())

            for (index in 0 until json.length()){

                val jsonArticle = json.get(index) as JSONObject

                val post = Post.fromJson(jsonArticle)

                posts.add(post)

            }

            callBack(posts.toList())

        }

    }

    fun getComments(postId: Int, comments: MutableList<Comment>, callBack: (List<Comment>) -> Unit){

        val URL = "https://jsonplaceholder.typicode.com/posts/${postId}/comments"

        val request = Request.Builder().url(URL).get().build()
        val client = OkHttpClient.Builder().build()

        client.newCall(request).execute().use { response ->

            val json = JSONArray(response.body!!.string())

            for (index in 0 until json.length()){

                val jsonArticle = json.get(index) as JSONObject

                val comment = Comment.fromJson(jsonArticle)

                comments.add(comment)

            }

            callBack(comments.toList())

        }

    }

    fun postComment(comment: Comment) : Boolean {

        val URL = "https://jsonplaceholder.typicode.com/posts/${comment.postId}/comments"

        val requestBody = comment.toJson().toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url(URL).post(requestBody).build()
        val client = OkHttpClient.Builder().build()

        client.newCall(request).execute().use {

            return it.message == "OK"

        }

    }

}
package com.app.packkbook.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.packkbook.Adapters.PostAdapter
import com.app.packkbook.Models.Post
import com.app.packkbook.Requests.GeneralRequests
import com.app.packkbook.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var posts = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerViewPosts = binding.recyclerViewPosts

        recyclerViewPosts.layoutManager = LinearLayoutManager(this)

        val adapter = PostAdapter(posts)

        recyclerViewPosts.adapter = adapter

        loadPosts(adapter)

        adapter.onItemClick = { post ->

            val intent = Intent(applicationContext, CommentsActivity::class.java)
            intent.putExtra("postId", post.id)
            startActivity(intent)

        }
    }

    private fun loadPosts(adapter: PostAdapter){

        GlobalScope.launch(Dispatchers.IO) {
            GeneralRequests.getPosts(posts.toMutableList()) {

                runOnUiThread {
                    posts = it as ArrayList<Post>
                    adapter.updateReceiptsList(it)
                }

            }
        }

    }

}
package com.app.packkbook.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.packkbook.Models.Comment
import com.app.packkbook.R
import com.app.packkbook.databinding.ActivityAddCommentsBinding
import com.app.packkbook.databinding.ActivityCommentsBinding

class AddCommentsActivity : AppCompatActivity() {

    companion object{
        const val RESULT_CODE = 100
    }

    lateinit var binding: ActivityAddCommentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddComment.setOnClickListener{

            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val body = binding.editTextComment.text.toString()

            val comment =  Comment(1, 1, name, email, body)

            setResult(
                RESULT_CODE,
                intent.putExtra("comment", comment.toJson().toString())
            )
            finish()
        }
    }
}
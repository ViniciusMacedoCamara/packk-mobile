package com.app.packkbook.Models

import org.json.JSONObject

class Comment {

    var id: Int? = null
    var postId: Int? = null
    var name: String? = null
    var email: String? = null
    var body: String? = null

    constructor()

    constructor(id: Int? ,userId: Int?,name: String?, email: String?, body: String?){

        this.id = id
        this.postId = userId
        this.name = name
        this.email = email
        this.body = body

    }

    fun toJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("postId", postId)
        jsonObject.put("name", name)
        jsonObject.put("email", email)
        jsonObject.put("body", body)
        return jsonObject
    }

    companion object{

        fun fromJson(jsonArticle: JSONObject): Comment {
            val post = Comment()
            post.id = jsonArticle.getInt("id")
            post.postId = jsonArticle.getInt("postId")
            post.name = jsonArticle.getString("name")
            post.email = jsonArticle.getString("email")
            post.body = jsonArticle.getString("body")
            return post
        }

    }

}
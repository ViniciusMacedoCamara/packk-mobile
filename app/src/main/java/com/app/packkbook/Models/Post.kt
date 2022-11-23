package com.app.packkbook.Models

import org.json.JSONObject

class Post {

    var id: Int? = null
    var userId: Int? = null
    var title: String? = null
    var body: String? = null

    constructor()

    constructor(id: Int? ,userId: Int?,title: String?, body: String?){

        this.id = id
        this.userId = userId
        this.title = title
        this.body = body

    }

    fun toJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("userId", userId)
        jsonObject.put("title", title)
        jsonObject.put("body", body)
        return jsonObject
    }

    companion object{

        fun fromJson(jsonArticle: JSONObject): Post {
            val post = Post()
            post.id = jsonArticle.getInt("id")
            post.userId = jsonArticle.getInt("userId")
            post.title = jsonArticle.getString("title")
            post.body = jsonArticle.getString("body")
            return post
        }

    }

}
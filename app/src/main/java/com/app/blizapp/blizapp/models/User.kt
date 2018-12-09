package com.app.blizapp.blizapp.models

data class User(private val userId: String, private val password: String,
                private val name: String, private val lastName: String,
                private val email: String){


    fun getUserId(): String{
        return this.userId
    }
    fun getPassword(): String{
        return this.password
    }
    fun getName(): String{
        return this.name
    }
    fun getLastName(): String{
        return this.lastName
    }
    fun getMail():String{
        return this.email
    }
}
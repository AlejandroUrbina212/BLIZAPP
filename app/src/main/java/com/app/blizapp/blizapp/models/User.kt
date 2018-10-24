package com.app.blizapp.blizapp.models

import java.util.*

data class User(private val userId: String, private var userName: String, private var password: String,
                private var name: String, private var lastName: String,
                private var phone: String, private var email: String, private val gender: Int){


    fun getUserId(): String{
        return this.userId
    }
    fun getUserName(): String{
        return this.userName
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
    fun getTelephone():String{
        return this.phone
    }
    fun getMail():String{
        return this.email
    }
    fun getGender():Int{
        return this.gender
    }




}
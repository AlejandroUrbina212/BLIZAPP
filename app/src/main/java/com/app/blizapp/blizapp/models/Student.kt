package com.app.blizapp.blizapp.models

class Student(private val userId: String ="", private val carne: Int = 0,
              private val name: String = "", private val lastName: String = "",
              private val email: String = "", private var password: String = "",
              private val career: String = "", private var year: Int = 0,
              private var totalHours: Int = 0, private var currentHours: Int = 0) {
    fun getUserId(): String {
        return this.userId
    }

    fun getCarne(): Int {
        return this.carne
    }

    fun getName(): String {
        return this.name
    }

    fun getLastName(): String {
        return this.lastName
    }

    fun getMail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getCareer(): String {
        return this.career
    }
    fun getYear(): Int{
        return this.year
    }
    fun getTotalHours(): Int{
        return this.totalHours
    }
    fun getCurrentHours(): Int{
        return this.currentHours
    }


}
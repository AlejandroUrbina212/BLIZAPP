package com.app.blizapp.blizapp.models

import java.util.*

data class Record (private var name: String, private var lastName: String, private val carne: Int,
                   private val hoursOfService: Int, private val faculty: String, private val department:  String,
                   private val dateOfService: Date, private val savedBy: String, private val savedOn: Date){


    fun getName(): String{
        return this.name
    }

    fun getLastname(): String{
        return this.lastName
    }
    fun getCarne(): Int{
        return this.carne
    }

    fun getHoursOfService(): Int{
        return this.hoursOfService
    }

    fun getfaculty(): String{
        return this.faculty
    }
    fun getDateOfService():Date{
        return  this.dateOfService
    }
    fun getSavedBy(): String{
        return  this.savedBy
    }
    fun getDepartment(): String{
        return this.department
    }
    fun getSavedOn(): Date  {
        return  this.savedOn
    }
}
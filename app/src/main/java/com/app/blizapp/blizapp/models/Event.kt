package com.app.blizapp.blizapp.models

import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

class Event (private val eventId:String, private val userId: String,
             private val eventName: String, private val eventDescription: String,
             private val place: String, private val date: Date,
             private var startHour: Time, private var endHour: Time,
             private val duration: Int, private var targetYear: Int,
             private var targetCareer: String, private val capacity: Int,
             private val assistantStudents: ArrayList<String>){


    fun getEventId():String{
        return this.eventId
    }
    fun getUserId(): String{
        return this.userId
    }
    fun getEventname(): String{
        return this.eventName
    }
    fun getEventDescription(): String{
        return this.eventDescription
    }
    fun getPlace(): String{
        return this.place
    }
    fun getDate(): Date {
        return this.date
    }
    fun getInitialHour(): Time {
        return this.startHour
    }
    fun getEndHour(): Time{
        return this.endHour
    }
    fun getDuration(): Int{
        return this.duration
    }
    fun getTargetYear(): Int{
        return this.targetYear
    }
    fun getTargetCareer(): String{
        return this.targetCareer
    }
    fun getCapacity(): Int{
        return this.capacity
    }
    fun getAssistantStudents(): ArrayList<String>{
        return this.assistantStudents
    }




}
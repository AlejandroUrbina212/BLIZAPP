package com.app.blizapp.blizapp.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.collections.ArrayList

data class Event (
        private val eventId:String ="",
        private val userId: String = "",
        private val eventName: String = "",
        private val eventDescription: String ="",
        private val place: String = "",

        @ServerTimestamp
        private val date: Date? = null,

        @ServerTimestamp
        private val startHour: Date? = null,
        @ServerTimestamp
        private val endHour: Date? = null,
        private val duration: Int = 0,
        private var targetYear: Int = 0,
        private var targetCareer: String = "",
        private val capacity: Int = 0,
        private val assistantStudents: ArrayList<String> = arrayListOf(""),
        private val state: Int =0){


    fun getEventId():String{
        return this.eventId
    }
    fun getUserId(): String{
        return this.userId
    }
    fun getEventName(): String{
        return this.eventName
    }
    fun getEventDescription(): String{
        return this.eventDescription
    }
    fun getPlace(): String{
        return this.place
    }
    fun getDate(): Date? {
        return this.date
    }
    fun getStartHour(): Date? {
        return this.startHour
    }
    fun getEndHour(): Date? {
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
    fun getState(): Int{
        return this.state
    }
}
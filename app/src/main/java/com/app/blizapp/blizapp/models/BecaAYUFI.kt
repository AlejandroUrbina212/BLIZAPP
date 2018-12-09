package com.app.blizapp.blizapp.models

class BecaAYUFI(private val idBeca: String, private val StudentId: String,
                private val hoursToDo:Int, private var hoursMade: Int) {
    fun getIdBeca():String{
        return this.idBeca
    }
    fun getStudentId():String{
        return this.StudentId
    }
    fun getHoursToDo(): Int{
        return this.hoursToDo
    }
    fun getHoursMade(): Int{
        return this.hoursMade
    }
}
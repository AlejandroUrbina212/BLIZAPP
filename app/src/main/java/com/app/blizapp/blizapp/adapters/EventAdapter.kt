package com.app.blizapp.blizapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.blizapp.blizapp.R
import com.app.blizapp.blizapp.models.Event
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter (options: FirestoreRecyclerOptions<Event>,
                    val listener: (Event) -> Unit

) : FirestoreRecyclerAdapter<Event, EventAdapter.EventViewHolder>(options){

    override fun onBindViewHolder(productViewHolder: EventViewHolder, position: Int, request: Event) {
        productViewHolder.bindItems(request)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }



    inner class EventViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(event: Event) = with(itemView){
            val eventNameTextView =findViewById<TextView>(R.id.eventNameTxt)
            val eventDateTextView  = findViewById<TextView>(R.id.eventDateTxt)
            val eventTimeTextView  = findViewById<TextView>(R.id.eventTimeTxt)
            val eventPlaceTextView = findViewById<TextView>(R.id.eventPlaceTxt)
            val eventStateTxt = findViewById<TextView>(R.id.eventStateTxt)

            eventNameTextView.text = event.getEventName()
            val sdf = SimpleDateFormat("dd/M/yyyy")
            eventDateTextView.text = sdf.format(event.getDate())
            val shf = SimpleDateFormat("KK:mm a")
            val initialHour = shf.format(event.getStartHour())
            val endHour = shf.format(event.getEndHour())
            eventTimeTextView.text = "$initialHour -- $endHour"
            eventPlaceTextView.text = event.getPlace()

            when {
                event.getState() == 0 ->{
                    //Evento Colgado
                    eventStateTxt.text = "Evento colgado"
                    currentStateImageView.setImageResource(R.drawable.ic_evento_colgado)

                }
                event.getState() == 1 ->{
                    //Evento Lleno
                    eventStateTxt.text = "Cupo lleno"
                    currentStateImageView.setImageResource(R.drawable.ic_evento_lleno)
                }
                event.getState() == 2 -> {
                    //Evento Finalizado
                    eventStateTxt.text = "Evento Finalizado"
                    currentStateImageView.setImageResource(R.drawable.ic_evento_finalizado)
                }
            }
            setOnClickListener { listener(event) }
        }

    }


}
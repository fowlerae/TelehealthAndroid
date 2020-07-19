package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.symptom_card_view.view.*

class SymptomViewHolder(itemView: View, private val symptomAdapter: SymptomAdapter) : RecyclerView.ViewHolder(itemView) {
    private var cardView: CardView = itemView.symptom_card_view
    private val nameTextView: TextView? =
        itemView.findViewById(edu.rosehulman.fowlerae.telehealth.R.id.symptom_name_text_view)
    private val painTextView: TextView? =
        itemView.findViewById(edu.rosehulman.fowlerae.telehealth.R.id.symptom_pain)

    init {
        itemView.setOnClickListener {
            symptomAdapter.selectSymptom(adapterPosition)
        }
    }

    fun bind(symptom: Symptom) {
        if (nameTextView != null) {
            nameTextView.text = symptom.name
        }
        if (painTextView != null) {
            painTextView.text = symptom.rating.toString()
        }
    }


}
package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.add_symptom_card_view.view.*

class AddSymptomViewHolder(itemView: View, private val symptomAdapter: AddSymptomAdapter) :
    RecyclerView.ViewHolder(itemView) {

    private var cardView: CardView = itemView.add_symptom_card_view
    private val nameTextView: TextView? = itemView.findViewById(R.id.add_symptom_name)

    init {
        itemView.setOnClickListener {
            symptomAdapter.selectSymptom(adapterPosition)
        }
    }

    fun bind(symptom: Symptom) {
        if (nameTextView != null) {
            nameTextView.text = symptom.name
        }
    }
}
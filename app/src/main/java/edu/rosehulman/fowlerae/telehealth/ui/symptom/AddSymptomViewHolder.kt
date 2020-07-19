package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.add_symptom_card_view.view.*

class AddSymptomViewHolder(itemView: View, private val symptomAdapter: AddSymptomAdapter) :
    RecyclerView.ViewHolder(itemView) {

    private var cardView: CardView = itemView.add_symptom_card_view
    private val nameTextView: TextView? = itemView.findViewById(R.id.add_symptom_name)
    private val closeImageView: ImageView =
        itemView.findViewById(R.id.add_symptom_delete_symptom_image_view)

    init {
        itemView.setOnClickListener {
            symptomAdapter.selectSymptom(adapterPosition)
        }

        closeImageView.setOnClickListener {
            symptomAdapter.delete(adapterPosition)
        }
    }

    fun bind(symptom: Symptom) {
        if (nameTextView != null) {
            nameTextView.text = symptom.name
        }
    }
}
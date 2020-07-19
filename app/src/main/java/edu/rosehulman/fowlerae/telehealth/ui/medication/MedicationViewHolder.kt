package edu.rosehulman.fowlerae.telehealth.ui.medication

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.medication_card_view.view.*


class MedicationViewHolder(itemView: View, private val medicationAdapter: MedicationAdapter): RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.medication_name)

    private var cardView: CardView

    init {
        itemView.setOnClickListener {
            medicationAdapter.showAddEditDialog(adapterPosition)

        }
        cardView = itemView.medication_card_view
    }

    fun bind(med: Medication) {
        nameTextView.text = med.name
    }
}
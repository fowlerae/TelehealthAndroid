package edu.rosehulman.fowlerae.telehealth.ui.settings

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import edu.rosehulman.fowlerae.telehealth.R
import edu.rosehulman.fowlerae.telehealth.ui.settings.Setting
import edu.rosehulman.fowlerae.telehealth.ui.settings.SettingsAdapter
import kotlinx.android.synthetic.main.medication_card_view.view.*


class SettingsViewHolder(itemView: View, private val settingsAdapter: SettingsAdapter): RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.setting_name)

    private var cardView: CardView

    init {
//        itemView.setOnClickListener {
//            listAdapter.selectMovieQuote(adapterPosition)
//
//        }
//        itemView.setOnLongClickListener {
//            listAdapter.showAddEditDialog(adapterPosition)
//            true
//        }
        cardView = itemView.medication_card_view
    }

    fun bind(setting: Setting) {
        nameTextView.text = setting.name
    }
}
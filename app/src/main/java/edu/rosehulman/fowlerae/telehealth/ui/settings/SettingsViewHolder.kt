package edu.rosehulman.fowlerae.telehealth.ui.settings

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.setting_card_view.view.*


class SettingsViewHolder(itemView: View, private val settingsAdapter: SettingsAdapter): RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.setting_name)
    private val imageView: ImageView = itemView.findViewById(R.id.setting_image)
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
        cardView = itemView.setting_card_view
    }

    fun bind(setting: Setting) {
        nameTextView.text = setting.name

    }

    fun getIcon(setting: Setting) {
        when (setting.name) {
            "Privacy Policy" -> imageView.setImageResource(R.drawable.ic_security)
        }
    }
}
package edu.rosehulman.fowlerae.telehealth.ui.home

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.badge_card_view.view.*


class BadgeViewHolder(itemView: View, private val badgeAdapter: BadgeAdapter): RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)

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
        cardView = itemView.badge_card_view
    }

    fun bind(badge: Badge) {
        nameTextView.text = badge.name
    }
}
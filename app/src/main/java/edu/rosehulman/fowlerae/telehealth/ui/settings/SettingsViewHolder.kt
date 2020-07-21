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
    private var cardView: CardView = itemView.setting_card_view


    fun bind(setting: Setting) {
        nameTextView.text = setting.name
        getIcon(setting)
    }

    fun getIcon(setting: Setting) {
        when (setting.name) {
            "Privacy Policy" -> imageView.setImageResource(R.drawable.ic_security)
            "Notifications" -> imageView.setImageResource(R.drawable.ic_notifications)
            "Terms of Use" -> imageView.setImageResource(R.drawable.ic_terms)
            "Sign Out" -> imageView.setImageResource(R.drawable.ic_signout)
            "Doctor's Office" -> imageView.setImageResource(R.drawable.ic_doctor)
        }
    }
}
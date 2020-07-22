package edu.rosehulman.fowlerae.telehealth.ui.settings

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R

class SettingsAdapter(val context: Context, val listener: OnSettingSelectedListener) :
    RecyclerView.Adapter<SettingsViewHolder>() {
    private val settings = ArrayList<Setting>()
    private val settingsRef = FirebaseFirestore
        .getInstance()
        .collection("settings")
    private lateinit var listenerRegistration: ListenerRegistration

    init {
        Log.d(Constants.TAG, "settingRef: ${settingsRef.get()}")
        Log.d(Constants.TAG, "Size: ${settings.size}")
        //  add(Medication("please im sad"))

    }

    fun addSnapshotListener() {
        listenerRegistration = settingsRef
            .orderBy(Setting.CREATED_KEY, Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "listen error", e)
                } else {
                    processSnapshotChanges(querySnapshot!!)
                }
            }
    }

    private fun processSnapshotChanges(querySnapshot: QuerySnapshot) {
        // Snapshots has documents and documentChanges which are flagged by type,
        // so we can handle C,U,D differently.
        for (documentChange in querySnapshot.documentChanges) {
            val s = Setting.fromSnapshot(documentChange.document)
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    Log.d(Constants.TAG, "Adding $s")
                    settings.add(0, s)
                    notifyItemInserted(0)
                    Log.d(Constants.TAG,"Size: ${settings.size}")
                }
                DocumentChange.Type.REMOVED -> {
                    Log.d(Constants.TAG, "Removing $s")
                    val index = settings.indexOfFirst { it.id == s.id }
                    settings.removeAt(index)
                    notifyItemRemoved(index)
                }
                DocumentChange.Type.MODIFIED -> {
                    Log.d(Constants.TAG, "Modifying $s")
                    val index = settings.indexOfFirst { it.id == s.id }
                    settings[index] = s
                    notifyItemChanged(index)
                }
            }
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, index: Int): SettingsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.setting_card_view, parent, false)
        return SettingsViewHolder(view, this)
    }

    override fun onBindViewHolder(
        listViewHolder: SettingsViewHolder,
        index: Int
    ) {
        listViewHolder.bind(settings[index])
    }

    override fun getItemCount() = settings.size

    fun add(setting: Setting) {
        settings.add(0,setting)
        settingsRef.add(setting)
    }

    private fun edit(setting: String, position: Int) {
        this.settings[position].name = setting
        settingsRef.document(this.settings[position].id).set(this.settings[position])
    }

    private fun delete(position: Int) {
        settingsRef.document(settings[position].id).delete()
    }

    interface OnSettingSelectedListener {
        fun onSettingSelected()
    }


}
package edu.rosehulman.fowlerae.telehealth.ui.home

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.*
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import edu.rosehulman.fowlerae.telehealth.ui.medication.Medication

class BadgeAdapter(val context: Context) : RecyclerView.Adapter<BadgeViewHolder>() {
    private val badges = ArrayList<Badge>()
    private val badgeRef = FirebaseFirestore
        .getInstance()
        .collection("users")
        .document("UGSe2Si5KAsB0sQb9Gf7")
        .collection("badges")
    private lateinit var listenerRegistration: ListenerRegistration
    init {
        Log.d(Constants.TAG, "Size: ${badges.size}")
        notifyDataSetChanged()
    }

    fun addSnapshotListener() {
        listenerRegistration = badgeRef
            .orderBy(Medication.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
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
            val project = Badge.fromSnapshot(documentChange.document)
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    Log.d(Constants.TAG, "Adding $project")
                    badges.add(0, project)
                    notifyItemInserted(0)
                }
                DocumentChange.Type.REMOVED -> {
                    Log.d(Constants.TAG, "Removing $project")
                    val index = badges.indexOfFirst { it.id == project.id }
                    badges.removeAt(index)
                    notifyItemRemoved(index)
                }
                DocumentChange.Type.MODIFIED -> {
                    Log.d(Constants.TAG, "Modifying $project")
                    val index = badges.indexOfFirst { it.id == project.id }
                    badges[index] = project
                    notifyItemChanged(index)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): BadgeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.badge_card_view, parent, false)
        return BadgeViewHolder(view, this)
    }

    override fun onBindViewHolder(
        listViewHolder: BadgeViewHolder,
        index: Int
    ) {
        listViewHolder.bind(badges[index])
    }

    override fun getItemCount() = badges.size

    fun add(badge: Badge) {
        badgeRef.add(badge)
    }

    private fun edit(badges: String, position: Int) {
        this.badges[position].name = badges
        badgeRef.document(this.badges[position].id).set(this.badges[position])
    }

    private fun delete(position: Int) {
        badgeRef.document(badges[position].id).delete()
    }


}
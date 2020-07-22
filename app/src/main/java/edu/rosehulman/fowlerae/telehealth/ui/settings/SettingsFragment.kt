package edu.rosehulman.fowlerae.telehealth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.EducationalFragment
import edu.rosehulman.fowlerae.telehealth.R

class SettingsFragment : Fragment(), SettingsAdapter.OnSettingSelectedListener {

    private lateinit var adapter: SettingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val recyclerView =
            root.findViewById<RecyclerView>(R.id.setting_recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = context?.let { SettingsAdapter(it, this) }!!
        recyclerView.adapter = adapter
        //   recyclerView.setHasFixedSize(true)
        adapter.addSnapshotListener()
        return root
    }

    override fun onSettingSelected() {
        val fragment: EducationalFragment = EducationalFragment()
        val fragmentManager: FragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("educational")
            .commit()
    }


}
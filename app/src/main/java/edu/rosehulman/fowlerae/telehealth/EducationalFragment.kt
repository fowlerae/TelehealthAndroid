package edu.rosehulman.fowlerae.telehealth

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class EducationalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_educational, container, false)


        val t1 = root.findViewById(R.id.link1) as TextView
        t1.movementMethod = LinkMovementMethod.getInstance()

        val t2 = root.findViewById(R.id.link2) as TextView
        t2.movementMethod = LinkMovementMethod.getInstance()

        val t3 = root.findViewById(R.id.link3) as TextView
        t3.movementMethod = LinkMovementMethod.getInstance()

        val t4 = root.findViewById(R.id.link4) as TextView
        t4.movementMethod = LinkMovementMethod.getInstance()

        val t5 = root.findViewById(R.id.link5) as TextView
        t5.movementMethod = LinkMovementMethod.getInstance()

        return root
    }
}
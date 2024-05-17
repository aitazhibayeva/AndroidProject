package com.example.moneytrack.views.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moneytrack.R
import com.example.moneytrack.utils.preferences.SharedPreferencesManager

class SettingsFragment : Fragment() {

    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profileUsername: TextView
    private lateinit var profilePassword: TextView

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        profileName = view.findViewById(R.id.profileName)
        profileEmail = view.findViewById(R.id.profileEmail)
        profileUsername = view.findViewById(R.id.profileUsername)
        profilePassword = view.findViewById(R.id.profilePassword)
        val manager = SharedPreferencesManager(requireContext())
        userViewModel.fetchUserData(manager.login ?: "", manager.password ?: "")
        observeUserData()

        return view
    }

    private fun observeUserData() {
        userViewModel.userData.observe(viewLifecycleOwner) { user ->
            Log.e("TAG", "User: $user")
            profileName.text = user.name
            profileEmail.text = user.email
            profileUsername.text = user.username
            profilePassword.text = user.password
        }
    }

}

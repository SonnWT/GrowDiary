package com.example.growdiary.loginregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.growdiary.R

class LoginFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.login_btn).setOnClickListener(){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        view.findViewById<LinearLayout>(R.id.register_link).setOnClickListener(){
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}
package com.example.crm_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val findNavController = Navigation.findNavController(this, R.id.fragment)
        val appBarConfiguration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
//        bottomNavigationView.labelVisibilityMode = 1
        NavigationUI.setupActionBarWithNavController(this, findNavController, appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNavigationView, findNavController)

    }


}

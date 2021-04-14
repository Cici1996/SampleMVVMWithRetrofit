package com.example.samplemvvmwithretrofit.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mToggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setSupportActionBar(toolbar_main)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        mToggle.isDrawerIndicatorEnabled = true;
        mToggle.syncState()

        binding.drawerLayout.addDrawerListener(mToggle)
        binding.navigationView.setNavigationItemSelectedListener(this)
        onNavigationItemSelected(binding.navigationView.menu.getItem(0))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {
            R.id.gosokberhadiahmenu -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_frame, GosoFragment()).commit()
                toolbar_main.title = "Basic Recyleview"
            }
            R.id.datadirimenu -> {
                toolbar_main.title = "Basic Form"
                supportFragmentManager.beginTransaction().replace(R.id.content_frame, FormUserFragment()).commit()
            }
            R.id.transaksimenu -> {
                toolbar_main.title = "Basic Recyleview With Paging"
                supportFragmentManager.beginTransaction().replace(R.id.content_frame, RecyleviewPagingFragment()).commit()
            }
            R.id.notifikasimenu -> {
                toolbar_main.title = "Sample OTP"
                supportFragmentManager.beginTransaction().replace(R.id.content_frame, OtpViewFragment()).commit()
            }
            R.id.keluarmenu -> {
                Toast.makeText(this, "Newsletter", Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        val id = item.itemId

        if (id == R.id.notification_settings) {
            Toast.makeText(this, "Notifikasi Item Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item);
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
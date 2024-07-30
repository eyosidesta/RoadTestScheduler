package com.example.roadtestscheduler

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible

import com.example.roadtestscheduler.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupDrawer()
        setupProvinceSpinner()

        binding.selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        binding.submitButton.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.submitButton.isEnabled = false
            // Simulate submission delay
            binding.submitButton.postDelayed({
                binding.progressBar.isVisible = false
                binding.submitButton.isEnabled = true
                Toast.makeText(this, "Your driver license road test has been scheduled for $selectedDate", Toast.LENGTH_LONG).show()
            }, 2000)
        }
        binding.showFragmentButton.setOnClickListener {
            SampleDialogFragment().show(supportFragmentManager, "SampleDialog")
        }
    }

    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_schedule -> {
                // Handle schedule action
            }
            R.id.nav_see_exam_date -> {
                val intent = Intent(this, SetExamDateActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_see_license -> {
                val intent = Intent(this, ViewLicenseActivity::class.java)
                startActivity(intent)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupProvinceSpinner() {
        val provinces = arrayOf("Nova Scotia", "New Foundland and Labrador", "New Brunswick", "Prince Edward Island", "Quebec", "Ontario", "Manitoba", "Saskatchewan", "Alberta", "British Columbia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.provinceSpinner.adapter = adapter

        binding.provinceSpinner.setSelection(5) // Default select Ontario

        binding.provinceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                setupCitySpinner(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun setupCitySpinner(provincePosition: Int) {
        val cityMap = mapOf(
            0 to arrayOf("Halifax", "Sydney", "Truro", "Yarmouth"),
            1 to arrayOf("St. John's", "Corner Brook", "Gander", "Happy Valley-Goose Bay"),
            2 to arrayOf("Moncton", "Fredericton", "Saint John", "Dieppe"),
            3 to arrayOf("Charlottetown", "Summerside", "Stratford", "Cornwall"),
            4 to arrayOf("Montreal", "Quebec City", "Laval", "Gatineau"),
            5 to arrayOf("Toronto", "Thunder Bay", "London", "Mississauga"),
            6 to arrayOf("Winnipeg", "Brandon", "Steinbach", "Thompson"),
            7 to arrayOf("Regina", "Saskatoon", "Moose Jaw", "Prince Albert"),
            8 to arrayOf("Calgary", "Edmonton", "Red Deer", "Lethbridge"),
            9 to arrayOf("Vancouver", "West Minister", "Surrey", "Richmond")
        )
        val cities = cityMap[provincePosition] ?: arrayOf("Other cities")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.adapter = adapter

        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                setupPostalCodeSpinner(provincePosition, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun setupPostalCodeSpinner(provincePosition: Int, cityPosition: Int) {
        val postalCodeMap = mapOf(
            0 to arrayOf("B3J 1S9", "B1S 2P5", "B2N 5C1", "B5A 2P8"),
            1 to arrayOf("A1C 5M2", "A2H 6J8", "A1V 1W6", "A0P 1C0"),
            2 to arrayOf("E1C 1G2", "E3B 1B7", "E2L 1S5", "E1A 1M1"),
            3 to arrayOf("C1A 1K1", "C1N 4J8", "C1B 2V2", "C0A 1H0"),
            4 to arrayOf("H3B 1A2", "G1R 1N9", "H7N 0A1", "J8X 3Y8"),
            5 to arrayOf("M5H 2N2", "P7B 6S8", "N6A 1E3", "L5B 2C9"),
            6 to arrayOf("R3C 4T3", "R7A 7J3", "R5G 1T4", "R8N 1S3"),
            7 to arrayOf("S4P 2Z3", "S7K 1P2", "S6H 1P4", "S6V 4Z1"),
            8 to arrayOf("T2P 1X2", "T5J 0P1", "T4N 1G7", "T1J 1P5"),
            9 to arrayOf("V6B 1G1", "V3M 1J1", "V3T 1W5", "V6X 1Y3")
        )
        val postalCodes = postalCodeMap[provincePosition] ?: arrayOf("Other postal codes")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, postalCodes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.postalCodeSpinner.adapter = adapter
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            binding.selectedDateText.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }
}

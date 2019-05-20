package com.arkadiusz.surma.cookieclicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        optionsButton.setOnClickListener {
            Log.d("info", "Trying to open options activity")
            val optionIntent = Intent(this, OptionsActicity::class.java)
            startActivity(optionIntent)
        }

        exitButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
        }
    }
}

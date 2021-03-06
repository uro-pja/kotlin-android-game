package com.arkadiusz.surma.cookieclicker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.arkadiusz.surma.cookieclicker.R
import com.arkadiusz.surma.cookieclicker.model.Game
import com.arkadiusz.surma.cookieclicker.model.GameRepository
import com.arkadiusz.surma.cookieclicker.model.GameStore
import kotlinx.android.synthetic.main.activity_menu.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MenuActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val gameStore: GameStore by instance()
    private val gameRepository: GameRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        continueButton.visibility = View.INVISIBLE

        newGameButton.setOnClickListener {
            if (gameStore.has()) {
                val game = gameStore.retrieve()
                game.finish()


                gameRepository.add(gameStore.retrieve())
                gameStore.flush()
            }

            val game = Game()
            gameStore.store(game)

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        rankingButton.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }

        optionsButton.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)

            Log.d("info", "Bye")

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        continueButton.visibility = View.INVISIBLE
        if (gameStore.has()) {
            continueButton.visibility = View.VISIBLE
        }

        continueButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}

package com.arkadiusz.surma.cookieclicker.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arkadiusz.surma.cookieclicker.R

import kotlinx.android.synthetic.main.game_activity.*
import kotlinx.android.synthetic.main.game_content.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkadiusz.surma.cookieclicker.infrastructure.OnItemClickListener
import com.arkadiusz.surma.cookieclicker.infrastructure.addOnItemClickListener
import com.arkadiusz.surma.cookieclicker.model.*
import com.arkadiusz.surma.cookieclicker.model.exceptions.InsufficientFundsException
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class GameActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val gameStore: GameStore by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        refreshPointsInView()
        registerUpgradeAdapter()

        try {
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post(object : Runnable {
                override fun run() {
                    val pointsIncreased = gameStore.retrieve().increaseCounter()
                    button_cookie.animate().rotationBy(pointsIncreased.toFloat()).setDuration(1 * 100).interpolator =
                        LinearInterpolator()
                    refreshPointsInView()

                    mainHandler.postDelayed(this, 1000)
                }
            })
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }

        button_cookie.setOnClickListener { view ->
            val pointsIncreased = gameStore.retrieve().increasePoints()
            view.animate().rotationBy(pointsIncreased.toFloat()).setDuration(1 * 100).interpolator = LinearInterpolator()

            refreshPointsInView()
        }

        fab.setOnClickListener {
            changeUpgradesViewVisibility()
            refreshPointsInView()
        }
    }

    private fun changeUpgradesViewVisibility() {
        if (upgradeView.visibility == View.INVISIBLE)
            upgradeView.visibility = View.VISIBLE
        else
            upgradeView.visibility = View.INVISIBLE
    }

    private fun registerAdapter(upgradesView: RecyclerView): UpgradeAdapter {
        val adapter = UpgradeAdapter(UpgradeFactory.create(), gameStore.retrieve())
        upgradesView.adapter = adapter
        return adapter
    }

    @SuppressLint("SetTextI18n")
    private fun refreshPointsInView() {
        val game = gameStore.retrieve()
        yourPoints.text = game.getPoints().toString() + " points"
        pointsIncrementor.text = game.getPointsPerSecondMultiplier().toString() + "/second"
    }

    @SuppressLint("WrongConstant")
    private fun registerUpgradeAdapter() {
        val upgradesView = findViewById<RecyclerView>(R.id.upgradeView)
        upgradesView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = registerAdapter(upgradesView)
        upgradesView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                try {
                    gameStore.retrieve().buyUpgrade(adapter.getUpgradeForType(position))

                    Snackbar.make(view, "Upgrade Bought", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

                    registerAdapter(upgradesView)
                } catch (e: InsufficientFundsException) {
                    Snackbar.make(view, "Insufficient funds", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
        })
    }
}

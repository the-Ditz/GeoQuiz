package com.tutorial.bnr.ditzlern.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    var HAS_CHEATED = "has_cheated"
    var cheated = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        cheated = savedInstanceState?.getBoolean(HAS_CHEATED, false) ?: false

        cheatButton.setOnClickListener{
            answerTextView.text = intent.getBooleanExtra(CheatActivity.CORRECT_ANSWER, true).toString()
            cheated = true
            setAnswerShownResult(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outPersistentState?.putBoolean(HAS_CHEATED, cheated)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        val CORRECT_ANSWER = "correct_answer"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(CORRECT_ANSWER, answerIsTrue)
            }
        }

        const val EXTRA_ANSWER_SHOWN = "com.tutorial.bnr.ditzlern.geoquiz.answer_shown"
    }
}

package com.tutorial.bnr.ditzlern.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tutorial.bnr.ditzlern.geoquiz.CheatActivity.Companion.EXTRA_ANSWER_SHOWN
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity() : AppCompatActivity() {

    val TAG = "QuizActivity"
    private val KEY_INDEX = "index"
    private val CURRENT_QUESTION_ANSWER = "current_question_answer"

    private val questions = listOf(
            Question(R.string.question_intro, false),
            Question(R.string.question_oceans, true),
            Question(R.string.question_africa, true),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true))

    private var index = 0
    private var hasCheated = false

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called.")
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called.")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, index)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called.")
        setContentView(R.layout.activity_quiz)

        if (questions == null || questions.isEmpty()) {
            return
        }

        index = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        questionString.setText(questions[index].questionStringResId)

        setupButtons()
    }

    private fun setupButtons() {
        setupTrueButton()
        setupFalseButton()
        setupNextButton()
        setupPrevButton()
        setupCheatButton()
    }

    private fun setupTrueButton() {
        buttonTrue.setOnClickListener { view: View ->
            if (index < questions.size) {
                correctAnswer(questions[index].answer)
            }
        }
    }

    private fun setupFalseButton() {
        buttonFalse.setOnClickListener { view: View ->
            if (index < questions.size) {
                correctAnswer(!questions[index].answer)
            }
        }
    }

    private fun setupNextButton() {
        buttonNext.setOnClickListener { view: View ->
            index++
            if (index < questions.size) {
                questionString.setText(questions[index].questionStringResId)
            } else {
                showToast("No more questions.")
            }
        }
    }

    private fun setupPrevButton() {
        buttonPrev.setOnClickListener { view: View ->
            index--
            if (index >= 0) {
                questionString.setText(questions[index].questionStringResId)
            } else {
                showToast("Reached Start.")
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupCheatButton() {
        buttonCheat.setOnClickListener {view: View ->

            val intent = CheatActivity.newIntent(this, questions[index].answer)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options = ActivityOptions
                        .makeClipRevealAnimation(view, 0, 0, view.width, view.height)
                startActivityForResult(intent, 1234, options.toBundle())
            } else {
                startActivityForResult(intent, 1234)
            }
        }
//            startActivityForResult(intent, 1234)
    }

    private fun correctAnswer(correctAnswer: Boolean) {
        if (hasCheated) {
            showToast("Cheating is wrong!")
        }
        else if (correctAnswer) {
            showToast("Correct!")
        } else {
            showToast("Wrong!")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT).show()
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return }
        if (requestCode == 1234) {
            hasCheated = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}

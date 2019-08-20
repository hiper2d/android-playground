package com.hiper2d.androidplayground

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.hiper2d.androidplayground.dto.Question

import kotlinx.android.synthetic.main.activity_main.*

private val QUESTIONS = listOf(
    Question(R.string.question_1, true),
    Question(R.string.question_2, true),
    Question(R.string.question_3, true),
    Question(R.string.question_4, true),
    Question(R.string.question_5, true),
    Question(R.string.question_6, true),
    Question(R.string.question_7, true)
)

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var bYes: Button
    private lateinit var bNo: Button
    private lateinit var bNext: Button
    private lateinit var bPrev: Button
    private lateinit var tvQuestion: TextView

    private var currentQuestionIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle) called")

        currentQuestionIndex = if (currentQuestionIndex == -1 && savedInstanceState?.getInt(KEY_INDEX) != null) {
            savedInstanceState.getInt(KEY_INDEX)
        } else {
            0
        }

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        bNext = findViewById(R.id.button_next)
        bPrev = findViewById(R.id.button_prev)
        bYes = findViewById(R.id.button_yes)
        bNo = findViewById(R.id.button_no)
        tvQuestion = findViewById(R.id.text_question)

        tvQuestion.setText(QUESTIONS[currentQuestionIndex].textResId)
        bYes.setOnClickListener { checkAnswer(true) }
        bNo.setOnClickListener { checkAnswer(false) }
        bNext.setOnClickListener { showNextQuestion() }
        bPrev.setOnClickListener { showPrevQuestion() }

        bPrev.isEnabled = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState(Bundle) called")
        outState.putInt(KEY_INDEX, currentQuestionIndex)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent() called $event")
        return super.onTouchEvent(event)
    }

    private fun showPrevQuestion() {
        if (currentQuestionIndex > 1) {
            tvQuestion.setText(QUESTIONS[--currentQuestionIndex].textResId)
            enableAll()
        } else {
            bPrev.isEnabled = false
        }
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex < QUESTIONS.lastIndex) {
            tvQuestion.setText(QUESTIONS[++currentQuestionIndex].textResId)
            enableAll()
        } else {
            showToast(R.string.answer_congrats)
            listOf(bNext, bYes, bNo).forEach { it.isEnabled = false }
        }
    }

    private fun showToast(textId: Int) {
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(answer: Boolean) {
        if (answer == QUESTIONS[currentQuestionIndex].answer) {
            showToast(R.string.answer_correct)
            showNextQuestion()
        } else {
            showToast(R.string.answer_incorrect)
        }
    }

    private fun enableAll() {
        listOf(bPrev, bNext, bYes, bNo).forEach { it.isEnabled = true }
    }
}

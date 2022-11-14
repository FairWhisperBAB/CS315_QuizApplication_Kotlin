package com.example.cs315_quizapplication_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.cs315_quizapplication_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        // country, capital, choice1, choice2, choice3
        mutableListOf("China", "Beijing", "Jakarta", "Manila", "Stockholm"),
        mutableListOf("India", "New Delhi", "Beijing", "Bangkok", "Seoul"),
        mutableListOf("Indonesia", "Jakarta", "Manila", "New Delhi", "Kuala Lumpur"),
        mutableListOf("Japan", "Tokyo", "Bangkok", "Taipei", "Jakarta"),
        mutableListOf("Thailand", "Bangkok", "Berlin", "Havana", "Kingston"),
        mutableListOf("Brazil", "Brasilia", "Havana", "Bangkok", "Copenhagen"),
        mutableListOf("Canada", "Ottawa", "Bern", "Copenhagen", "Jakarta"),
        mutableListOf("Cuba", "Havana", "Bern", "London", "Mexico City"),
        mutableListOf("Mexico", "Mexico City", "Ottawa", "Berlin", "Santiago"),
        mutableListOf("United States", "Washington D.C.", "San Jose", "Buenos Aires", "Kuala Lumpur"),
        mutableListOf("France", "Paris", "Ottawa", "Copenhagen", "Tokyo"),
        mutableListOf("Germany", "Berlin", "Copenhagen", "Bangkok", "Santiago"),
        mutableListOf("Italy", "Rome", "London", "Paris", "Athens"),
        mutableListOf("Spain", "Madrid", "Mexico City", "Jakarta", "Havana"),
        mutableListOf("United Kingdom", "London", "Rome", "Paris", "Singapore")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Shuffle quiz.
        quizData.shuffle()

        showNextQuiz()
    }

    fun showNextQuiz()
    {
        //Update countlabel
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        //Pick one quiz set.
        val quiz = quizData[0]

        //Set question and right answer
        binding.questionLabel.text = quiz[0]
        rightAnswer = quiz[1]

        //Remove "Country" from quiz
        quiz.removeAt(0)

        //Shuffle answers and choices.
        quiz.shuffle()

        //Set choices.
        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        //Remove this quiz from QuizData.
        quizData.removeAt(0)
    }

    fun checkAnswer(view: View)
    {
        //Get pushed button
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if (btnText == rightAnswer)
        {
            //Correct
            alertTitle = "Correct!"
            rightAnswerCount++
        }
        else
        {
            //Wrong
            alertTitle = "Wrong..."
        }

        //Create Dialog.
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("Answer: $rightAnswer")
            .setPositiveButton("OK") {dialogInterface, i ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }

    fun checkQuizCount()
    {
        if (quizCount == QUIZ_COUNT)
        {
            //Show Results
            val intent = Intent(this@MainActivity, ResultActivity:: class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        }
        else
        {
            quizCount++
            showNextQuiz()
        }
    }
}
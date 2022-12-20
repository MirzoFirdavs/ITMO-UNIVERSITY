package com.github.mirzofirdavs.calctest

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var displayText: TextView
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var clipData: ClipData
    private lateinit var expressionParser: ExpressionParser

    private var text: String = ""
    private var isNew: Boolean = true

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("key", text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        text = savedInstanceState.getString("key")!!
        displayText.text = text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        expressionParser = ExpressionParser()
        displayText = findViewById(R.id.textResult)
    }

    fun clickClear(view: View) {
        when (view.id) {
            R.id.buttonClear -> {
                text = ""
                displayText.text = "0"
                isNew = true
            }
            R.id.buttonDelete -> {
                text = text.dropLast(1)
                displayText.text = text

                if (text.isEmpty()) {
                    displayText.text = "0"
                    isNew = true
                }
            }
        }
    }

    fun clickEvaluate(view: View) {
        try {
            text = expressionParser.calculate(text)
        } catch (element: ParsingException) {
            Toast
                .makeText(applicationContext, element.message, Toast.LENGTH_SHORT)
                .show()
        }

        displayText.text = text
        isNew = true
    }

    fun clickCopy(view: View) {
        clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)

        Toast
            .makeText(this, "R.string.copy_clip", Toast.LENGTH_SHORT)
            .show()
    }

    fun typeExpression(view: View) {
        if (isNew) {
            displayText.text = ""
            isNew = false
        }

        when (view.id) {
            R.id.buttonZero -> {
                text += "0"
                displayText.text = text
            }
            R.id.buttonOne -> {
                text += "1"
                displayText.text = text
            }
            R.id.buttonTwo -> {
                text += "2"
                displayText.text = text
            }
            R.id.buttonThree -> {
                text += "3"
                displayText.text = text
            }
            R.id.buttonFour -> {
                text += "4"
                displayText.text = text
            }
            R.id.buttonFive -> {
                text += "5"
                displayText.text = text
            }
            R.id.buttonSix -> {
                text += "6"
                displayText.text = text
            }
            R.id.buttonSeven -> {
                text += "7"
                displayText.text = text
            }
            R.id.buttonEight -> {
                text += "8"
                displayText.text = text
            }
            R.id.buttonNine -> {
                text += "9"
                displayText.text = text
            }
            R.id.buttonDot -> {
                text += "."
                displayText.text = text
            }
            R.id.buttonAdd -> {
                text += "+"
                displayText.text = text
            }
            R.id.buttonSubtract -> {
                text += "-"
                displayText.text = text
            }
            R.id.buttonMultiply -> {
                text += "*"
                displayText.text = text
            }
            R.id.buttonDivide -> {
                text += "/"
                displayText.text = text
            }
        }
    }
}
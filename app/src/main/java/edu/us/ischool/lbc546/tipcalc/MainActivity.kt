package edu.us.ischool.lbc546.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.text.TextWatcher
import android.text.Editable
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val charge = findViewById<EditText>(R.id.charge)
        val tip = findViewById<Button>(R.id.tip)
        tip.isEnabled = false

        fun chargeStringToDouble(amount: String): Double {
            val remove = Regex("[\$,.]")
            val cleanString: String = remove.replace(amount, "")
            return cleanString.toDouble() / 100
        }

        // Answer by Guilherme Oliveira @
        // https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext
        charge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().equals("")) {
                    charge.removeTextChangedListener(this)
                    val parsed = chargeStringToDouble(charge.text.toString())
                    val formatted: String = NumberFormat.getCurrencyInstance().format(parsed)
                    charge.setText(formatted)
                    charge.setSelection(formatted.length)

                    charge.addTextChangedListener(this)
                }
                tip.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        tip.setOnClickListener {
            val currCharge = chargeStringToDouble(charge.text.toString())
            val tipToast: Toast = Toast.makeText(applicationContext, "$" + "%.2f".format(currCharge * 15 / 100), Toast.LENGTH_LONG)
            tipToast.show()
        }
    }
}

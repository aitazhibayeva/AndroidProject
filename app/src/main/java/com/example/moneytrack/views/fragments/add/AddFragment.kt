package com.example.moneytrack.views.fragments.add

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.moneytrack.utils.Database.AppDatabase
import com.example.moneytrack.utils.Database.Transaction
import com.example.moneytrack.R
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date





class AddFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            AddFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private lateinit var incomeOrExpense: LinearLayout
    private lateinit var expense: TextView
    private lateinit var income: TextView

    private lateinit var paymentType: LinearLayout
    private lateinit var cash: TextView
    private lateinit var card: TextView

    private lateinit var spinnerDate: DatePicker

    private lateinit var totalText: TextView
    private lateinit var solutionText: TextView

    private lateinit var button0: MaterialButton
    private lateinit var button1: MaterialButton
    private lateinit var button2: MaterialButton
    private lateinit var button3: MaterialButton
    private lateinit var button4: MaterialButton
    private lateinit var button5: MaterialButton
    private lateinit var button6: MaterialButton
    private lateinit var button7: MaterialButton
    private lateinit var button8: MaterialButton
    private lateinit var button9: MaterialButton

    private lateinit var buttonDelete: MaterialButton
    private lateinit var buttonMulti: MaterialButton
    private lateinit var buttonMinus: MaterialButton
    private lateinit var buttonPlus: MaterialButton
    private lateinit var buttonDivide: MaterialButton
    private lateinit var buttonDot: MaterialButton

    private lateinit var buttonCategory1: TextView
    private lateinit var buttonCategory2: TextView
    private lateinit var buttonCategory3: TextView
    private lateinit var buttonCategory4: TextView
    private lateinit var buttonCategory5: TextView
    private lateinit var buttonCategory6: TextView
    private lateinit var buttonCategory7: TextView
    private lateinit var buttonSave: MaterialButton

    private var dataToCalculate = ""
    private var total = 0.0
    private var selectedIncomeOrExpense: String = ""
    private var selectedCashOrCard: String = ""
    private var selectedCategory: String = ""
    private var selectedDate: String = ""

    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        bindViews(view)
        setNumberButtonListeners()
        setOperationButtonListeners()
        setIncomeExpenseListeners()
        setCashOrCardListener()
        setCategoryListener()
        setDateListener()

        buttonSave.setOnClickListener {
            val transaction = Transaction(
                type = selectedIncomeOrExpense,
                paymentMethod = selectedCashOrCard,
                date = selectedDate,
                amount = total.toFloat(),
                category = selectedCategory
            )
            viewModel.saveRecord(transaction)
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            totalText.text = result
            total = result.toDouble()
            Log.e("TAG", "result: $result")
        }

        return view
    }

    private fun bindViews(view: View) {
        incomeOrExpense = view.findViewById(R.id.incomeExpense_layout)
        income = view.findViewById(R.id.incomeBtn)
        expense = view.findViewById(R.id.expenseBtn)

        paymentType = view.findViewById(R.id.payment_layout)
        card = view.findViewById(R.id.cardBtn)
        cash = view.findViewById(R.id.cashBtn)

        spinnerDate = view.findViewById(R.id.spinnerDate)

        totalText = view.findViewById(R.id.total)

        solutionText = view.findViewById(R.id.solution)

        button0 = view.findViewById(R.id.button_0)
        button1 = view.findViewById(R.id.button_1)
        button2 = view.findViewById(R.id.button_2)
        button3 = view.findViewById(R.id.button_3)
        button4 = view.findViewById(R.id.button_4)
        button5 = view.findViewById(R.id.button_5)
        button6 = view.findViewById(R.id.button_6)
        button7 = view.findViewById(R.id.button_7)
        button8 = view.findViewById(R.id.button_8)
        button9 = view.findViewById(R.id.button_9)

        buttonDot = view.findViewById(R.id.button_dot)
        buttonPlus = view.findViewById(R.id.button_plus)
        buttonMinus = view.findViewById(R.id.button_minus)
        buttonMulti = view.findViewById(R.id.button_multi)
        buttonDivide = view.findViewById(R.id.button_divide)
        buttonDelete = view.findViewById(R.id.button_delete)

        buttonCategory1 = view.findViewById(R.id.button_categ_1)
        buttonCategory2 = view.findViewById(R.id.button_categ_2)
        buttonCategory3 = view.findViewById(R.id.button_categ_3)
        buttonCategory4 = view.findViewById(R.id.button_categ_4)
        buttonCategory5 = view.findViewById(R.id.button_categ_5)
        buttonCategory6 = view.findViewById(R.id.button_categ_6)
        buttonCategory7 = view.findViewById(R.id.button_categ_7)

        buttonSave = view.findViewById(R.id.button_save)
    }

    private fun setNumberButtonListeners() {
        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { onClickNumber(index) }
        }
    }

    private fun setOperationButtonListeners() {
        buttonDot.setOnClickListener { onClickDot() }
        buttonPlus.setOnClickListener { onClickOperator('+') }
        buttonMinus.setOnClickListener { onClickOperator('-') }
        buttonMulti.setOnClickListener { onClickOperator('*') }
        buttonDivide.setOnClickListener { onClickOperator('/') }
        buttonDelete.setOnClickListener { onClickDelete() }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setIncomeExpenseListeners() {
        income.setOnClickListener {
            income.background = requireContext().getDrawable(R.drawable.income_selector)
            expense.background = requireContext().getDrawable(R.drawable.default_selector)
            expense.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            income.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            selectedIncomeOrExpense = "INCOME"
            totalText.text = selectedIncomeOrExpense
            totalText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        expense.setOnClickListener {
            income.background = requireContext().getDrawable(R.drawable.default_selector)
            expense.background = requireContext().getDrawable(R.drawable.expense_selector)
            income.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            expense.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            selectedIncomeOrExpense = "EXPENSE"
            totalText.text = selectedIncomeOrExpense
            totalText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setCashOrCardListener() {
        card.setOnClickListener {
            card.background = requireContext().getDrawable(R.drawable.income_selector)
            cash.background = requireContext().getDrawable(R.drawable.default_selector)
            cash.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            card.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            selectedCashOrCard = "CARD"
        }

        cash.setOnClickListener {
            cash.background = requireContext().getDrawable(R.drawable.income_selector)
            card.background = requireContext().getDrawable(R.drawable.default_selector)
            card.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            cash.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            selectedCashOrCard = "CASH"
        }
    }

    private fun setCategoryListener() {
        buttonCategory1.setOnClickListener {selectedCategory = "Food"}
        buttonCategory2.setOnClickListener {selectedCategory = "Transport"}
        buttonCategory3.setOnClickListener {selectedCategory = "Entertainment"}
        buttonCategory4.setOnClickListener {selectedCategory = "Clothes"}
        buttonCategory5.setOnClickListener {selectedCategory = "Sports"}
        buttonCategory6.setOnClickListener {selectedCategory = "Home"}
        buttonCategory7.setOnClickListener {selectedCategory = "Savings"}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDateListener(){
        spinnerDate.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDate = calendar.time.toString()
        }
    }

    private fun onClickNumber(number: Int) {
        dataToCalculate += number.toString()
        updateSolutionText()
        viewModel.calculateResult(dataToCalculate)
    }

    private fun onClickDot() {
        if (!dataToCalculate.contains(".")) {
            dataToCalculate += "."
            updateSolutionText()
        }
    }

    private fun onClickOperator(op: Char) {
        if (dataToCalculate.isNotEmpty()) {
            dataToCalculate += op
            updateSolutionText()
        }
    }

    private fun onClickDelete() {
        if (dataToCalculate.isNotEmpty()) {
            Log.e("TAG", "clear: $dataToCalculate ${dataToCalculate.length}")
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length - 1)
            Log.e("TAG", "after : $dataToCalculate ${dataToCalculate.length}")

            updateSolutionText()
            viewModel.calculateResult(dataToCalculate)
        }
    }

    private fun updateSolutionText() {
        solutionText.text = dataToCalculate
    }

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
        return dateFormat.format(date)
    }

    fun formatDateByMonth(date: Date): String {
        val dateFormat = SimpleDateFormat("MMMM, yyyy")
        return dateFormat.format(date)
    }
}

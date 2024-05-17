package com.example.moneytrack.views.fragments.search.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytrack.databinding.TransactionListBinding
import com.example.moneytrack.utils.Database.Transaction

class ListAdapter: RecyclerView.Adapter<ListAdapter.TransactionViewHolder>() {

    private var transactionList: ArrayList<Transaction> = arrayListOf()

    fun setItems(transactions: List<Transaction>) {
        val diffUtil = TransactionDiffUtil(transactionList, transactions)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        transactionList = transactions as ArrayList<Transaction>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            TransactionListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    inner class TransactionViewHolder(private val binding: TransactionListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            with(binding) {
                transactionCategory.text = transaction.category
                transactionType.text = transaction.type
                price.text = transaction.amount.toInt().toString()
            }
        }

    }



}
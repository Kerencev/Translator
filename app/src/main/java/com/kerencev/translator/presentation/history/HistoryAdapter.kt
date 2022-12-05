package com.kerencev.translator.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kerencev.domain.model.DetailsModel
import com.kerencev.translator.databinding.FragmentSearchRecyclerviewItemBinding

class HistoryAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<DetailsModel, HistoryAdapter.HistoryViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSearchRecyclerviewItemBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data = getItem(position))
    }

    inner class HistoryViewHolder(private val binding: FragmentSearchRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailsModel) {
            with(binding) {
                headerTextviewRecyclerItem.text = data.word
                descriptionTextviewRecyclerItem.text =
                    data.translates.substring(0, data.translates.indexOf('\n'))
                root.setOnClickListener {
                    onItemClickListener.onClick(data)
                }
            }
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<DetailsModel>() {

        override fun areItemsTheSame(oldItem: DetailsModel, newItem: DetailsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailsModel, newItem: DetailsModel): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onClick(data: DetailsModel)
    }
}
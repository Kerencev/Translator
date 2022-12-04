package com.kerencev.translator.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.databinding.FragmentSearchRecyclerviewItemBinding

class SearchAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : ListAdapter<DataModel, SearchAdapter.RecyclerItemViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSearchRecyclerviewItemBinding.inflate(inflater, parent, false)
        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecyclerItemViewHolder(private val binding: FragmentSearchRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(binding) {
                    headerTextviewRecyclerItem.text = data.text
                    descriptionTextviewRecyclerItem.text =
                        data.meanings?.get(0)?.translation?.translation
                    itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
                }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

    class ItemCallback : DiffUtil.ItemCallback<DataModel>() {

        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }
    }
}
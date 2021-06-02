package com.example.socketweatherdemo.list

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socketweatherdemo.list.ListAdapter.ViewHolder
import com.example.socketweatherdemo.R
import com.example.socketweatherdemo.inflate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val mutableSharedFlow =
        MutableSharedFlow<ListResponse>(extraBufferCapacity = 1)
    private var items: List<ListResponse> = emptyList()
    fun clicksFlow(): Flow<ListResponse> = mutableSharedFlow

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = parent.inflate(R.layout.controller_list_item)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(items: List<ListResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.text_view_title)
        private val bodyTextView: TextView = view.findViewById(R.id.text_view_body)
        private val idTextView: TextView = view.findViewById(R.id.text_view_id)
        private var item: ListResponse? = null

        init {
            view.setOnClickListener { mutableSharedFlow.tryEmit(requireNotNull(item)) }
        }

        fun bind(response: ListResponse) {
            item = response
            titleTextView.text = response.title
            bodyTextView.text = response.body
            idTextView.text = response.id.toString()
        }
    }
}

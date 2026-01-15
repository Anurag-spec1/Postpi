package com.anurag.postpi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.anurag.postpi.databinding.ItemHeaderBinding
import com.anurag.postpi.dataclass.ParamItem

class KeyValueAdapter(
    private val items: MutableList<ParamItem>
) : RecyclerView.Adapter<KeyValueAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        with(holder.binding) {

            checkEnable.setOnCheckedChangeListener(null)
            checkEnable.isChecked = item.enabled
            etKey.setText(item.key)
            etValue.setText(item.value)

            checkEnable.setOnCheckedChangeListener {
                    _, isChecked -> item.enabled = isChecked
            }

            etKey.addTextChangedListener {
                item.key = it.toString()
            }

            etValue.addTextChangedListener {
                item.value = it.toString()
            }

            btnDelete.setOnClickListener {
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    items.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }
    }

    override fun getItemCount() = items.size

    fun addItem() {
        items.add(ParamItem())
        notifyItemInserted(items.size - 1)
    }

    fun getEnabledItems(): List<ParamItem> =
        items.filter { it.enabled }
}

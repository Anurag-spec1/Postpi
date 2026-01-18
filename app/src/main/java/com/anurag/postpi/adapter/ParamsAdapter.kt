package com.anurag.postpi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.anurag.postpi.databinding.ItemParamBinding
import com.anurag.postpi.dataclass.ParamItem

class ParamsAdapter(
    private val params: MutableList<ParamItem>
) : RecyclerView.Adapter<ParamsAdapter.ParamViewHolder>() {

    inner class ParamViewHolder(
        val binding: ItemParamBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamViewHolder {
        val binding = ItemParamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParamViewHolder, position: Int) {
        val item = params[position]

        with(holder.binding) {

            checkEnable.setOnCheckedChangeListener(null)

            checkEnable.isChecked = item.enabled
            etKey.setText(item.key)
            etValue.setText(item.value)
            tvType.text = item.type

            checkEnable.setOnCheckedChangeListener { _, isChecked ->
                item.enabled = isChecked
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
                    params.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }
    }

    override fun getItemCount(): Int = params.size

    fun addParam(item: ParamItem) {
        params.add(item)
        notifyItemInserted(params.size - 1)
    }

    fun getEnabledParams(): List<ParamItem> {
        return params.filter { it.enabled }
    }
}

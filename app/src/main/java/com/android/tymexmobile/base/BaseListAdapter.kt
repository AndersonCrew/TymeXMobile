package com.android.tymexmobile.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.tymexmobile.utils.InflateFragmentAlias
import com.android.tymexmobile.utils.safeClick

abstract class BaseListAdapter<T : Any,VB: ViewBinding>(
    private val mInflate: InflateFragmentAlias<VB>,
    itemsSame: (T, T) -> Boolean,
    contentsSame: (T, T) -> Boolean
) : ListAdapter<T, BaseListAdapter<T, VB>.BaseViewHolder>(object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(old: T, new: T): Boolean = itemsSame(old, new)
    override fun areContentsTheSame(old: T, new: T): Boolean = contentsSame(old, new)

}) {

    protected var onPositionClickListener: ((Int) -> Unit)? = null
    protected var onItemClickListener: ((T) -> Unit)? = null

    protected abstract fun bindViewHolder(binding: VB, model: T, position: Int, viewType: Int)

    open fun onItemClick(callback: (T) -> Unit){
        this.onItemClickListener = callback
    }


    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = mInflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    open inner class BaseViewHolder(var binding: VB, var viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

        open fun bind(model: T, position: Int) {
            itemView.safeClick{
                val pos = bindingAdapterPosition
                if (pos > RecyclerView.NO_POSITION) {
                    onPositionClickListener?.invoke(pos)
                    onItemClickListener?.invoke(model)
                }
            }
            bindViewHolder(binding, model, position, viewType)
        }
    }
}
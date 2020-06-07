package com.javiersc.resources.core.extensions.base.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty1

/**
 * Usage:
 * private val adapter by baseListAdapter(UserItemBinding::inflate, User::userId) { user: User ->
 * 	   userItemBinding.textViewName.text = user.username
 * 	   userItemBinding.imageView.load(user.avatarUrl)
 * }
 * @param [binding] is the item binding generated via ViewBinding or DataBinding
 * @param [property] is the Item property used by DiffUtil to generate automatic animations, if it
 * is not indicated or is null, DiffUtil will search for a property called id
 * @param [onBind] is the lambda which include the item object
 */
class BaseListAdapter<ITEM : Any, VB : ViewBinding>(
    private val binding: (LayoutInflater, ViewGroup, Boolean) -> VB,
    property: KProperty1<ITEM, *>? = null,
    private val onBind: (VB.(ITEM) -> Unit),
) : ListAdapter<ITEM, BaseListAdapter<ITEM, VB>.BaseViewHolder>(
    BaseDiff<ITEM>(property)
) {

    private var onClick: ((ITEM) -> Unit)? = null
    fun onClick(listener: (ITEM) -> Unit) {
        onClick = { item -> listener(item) }
    }

    private var onClickPosition: ((ITEM, position: Int) -> Unit)? = null
    fun onClick(listener: (ITEM, position: Int) -> Unit) {
        onClickPosition = { item, position -> listener(item, position) }
    }

    private var onLongClick: ((ITEM) -> Unit)? = null
    fun onLongClick(listener: (ITEM) -> Unit) {
        onLongClick = { item -> listener(item) }
    }

    private var onLongClickPosition: ((ITEM, position: Int) -> Unit)? = null
    fun onLongClick(listener: (ITEM, position: Int) -> Unit) {
        onLongClickPosition = { item, position -> listener(item, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(binding.invoke(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        return viewHolder.bind(getItem(position))
    }

    inner class BaseViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ITEM) {
            with(binding) {
                root.setOnClickListener {
                    onClick?.invoke(item)
                    onClickPosition?.invoke(item, adapterPosition)
                }
                root.setOnLongClickListener {
                    onLongClick?.invoke(item).run { true }
                    onLongClickPosition?.invoke(item, adapterPosition).run { true }
                }
                onBind.invoke(binding, item)
            }
        }
    }
}

open class BaseDiff<T : Any>(private val property: KProperty1<T, *>?) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(old: T, new: T): Boolean {
        val oldProperty = old::class.members.firstOrNull { it == property }
        val newProperty = new::class.members.firstOrNull { it == property }
        return if (oldProperty != null && newProperty != null) oldProperty == newProperty else false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(old: T, new: T): Boolean = old == new
}

inline fun <reified ITEM : Any, VB : ViewBinding> listAdapter(
    noinline binding: (LayoutInflater, ViewGroup, Boolean) -> VB,
    identifier: KProperty1<ITEM, *>? = null,
    noinline onBind: VB.(ITEM) -> Unit,
): Lazy<BaseListAdapter<ITEM, VB>> {
    return lazy { BaseListAdapter(binding, identifier, onBind) }
}

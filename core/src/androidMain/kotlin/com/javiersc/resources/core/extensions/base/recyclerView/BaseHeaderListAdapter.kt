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
 * private val adapter by headerListAdapter(
 *     bindingHeader = ItemHeaderBinding::inflate,
 *     bindingRow = ItemRowBinding::inflate,
 *     headerProperty = Header::id,
 *     rowProperty = Row::id,
 *     onBindHeader = { header: Header ->
 *         headerTitle.text = header.data
 *     },
 *     onBindRow = { row: Row ->
 *         rowTitle.text = row.data
 *     }
 * )
 * @param [bindingHeader] is the header item binding generated via ViewBinding or DataBinding
 * @param [bindingRow] is the row item binding generated via ViewBinding or DataBinding
 * @param [headerProperty] is the header item property used by DiffUtil to generate automatic
 * animations, if it is not indicated or it is null, DiffUtil will search for a property called id
 * @param [rowProperty] is the row item property used by DiffUtil to generate automatic
 * animations, if it is not indicated or it is null, DiffUtil returns false
 * @param [onBindHeader] is the lambda which include the header item object
 * @param [onBindRow] is the lambda which include the row item object
 */
class BaseHeaderListAdapter<H : Any, R : Any, VBH : ViewBinding, VBR : ViewBinding>(
    private val bindingHeader: (LayoutInflater, ViewGroup, Boolean) -> VBH,
    private val bindingRow: (LayoutInflater, ViewGroup, Boolean) -> VBR,
    headerProperty: KProperty1<H, *>?,
    rowProperty: KProperty1<R, *>?,
    private val onBindHeader: (VBH.(H) -> Unit),
    private val onBindRow: (VBR.(R) -> Unit),
) : ListAdapter<Item<H, R>, BaseHeaderListAdapter<H, R, VBH, VBR>.BaseViewHolder>(
    BaseHeaderDiff<H, R>(headerProperty, rowProperty)
) {

    private var onClick: ((Item<H, R>) -> Unit)? = null
    fun onClick(listener: (Item<H, R>) -> Unit) {
        onClick = { item -> listener(item) }
    }

    private var onClickPosition: ((Item<H, R>, position: Int) -> Unit)? = null
    fun onClick(listener: (Item<H, R>, position: Int) -> Unit) {
        onClickPosition = { item, position -> listener(item, position) }
    }

    private var onLongClick: ((Item<H, R>) -> Unit)? = null
    fun onLongClick(listener: (Item<H, R>) -> Unit) {
        onLongClick = { item -> listener(item) }
    }

    private var onLongClickPosition: ((Item<H, R>, position: Int) -> Unit)? = null
    fun onLongClick(listener: (Item<H, R>, position: Int) -> Unit) {
        onLongClickPosition = { item, position -> listener(item, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            bindingHeader.invoke(LayoutInflater.from(parent.context), parent, false),
            bindingRow.invoke(LayoutInflater.from(parent.context), parent, false),
            ViewType.values().find { it.value == viewType } ?: throw NotImplementedError()
        )
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        return viewHolder.bind(getItem(position))
    }

    inner class BaseViewHolder(
        private val bindingHeader: VBH,
        private val bindingRow: VBR,
        val type: ViewType,
    ) : RecyclerView.ViewHolder(if (type == ViewType.Header) bindingHeader.root else bindingRow.root) {
        fun bind(item: Item<H, R>) {
            when (type) {
                ViewType.Header -> with(bindingHeader) {
                    root.setOnClickListener {
                        onClick?.invoke(item)
                        onClickPosition?.invoke(item, adapterPosition)
                    }
                    root.setOnLongClickListener {
                        onLongClick?.invoke(item).run { true }
                        onLongClickPosition?.invoke(item, adapterPosition).run { true }
                    }
                    onBindHeader.invoke(bindingHeader, (item as Item.Header<H>).item)
                }
                ViewType.Row -> with(bindingRow) {
                    root.setOnClickListener {
                        onClick?.invoke(item)
                        onClickPosition?.invoke(item, adapterPosition)
                    }
                    root.setOnLongClickListener {
                        onLongClick?.invoke(item).run { true }
                        onLongClickPosition?.invoke(item, adapterPosition).run { true }
                    }
                    onBindRow.invoke(bindingRow, (item as Item.Row<R>).item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Item.Header<*> -> ViewType.Header.value
            is Item.Row<*> -> ViewType.Row.value
            else -> throw NotImplementedError(
                "It is only implemented Header and Row, so this view type is unknown"
            )
        }
    }

    enum class ViewType(val value: Int) { Header(0), Row(1) }
}

open class BaseHeaderDiff<H : Any, R : Any>(
    private val headerProperty: KProperty1<H, *>?,
    private val rowProperty: KProperty1<R, *>?,
) : DiffUtil.ItemCallback<Item<H, R>>() {

    override fun areItemsTheSame(old: Item<H, R>, new: Item<H, R>): Boolean = when {
        old is Item.Header<H> && new is Item.Header<H> -> {
            val oldProperty = old.item::class.members.firstOrNull { property -> property == headerProperty }
            val newProperty = new.item::class.members.firstOrNull { property -> property == headerProperty }
            if (oldProperty != null && newProperty != null) oldProperty == newProperty else false
        }
        old is Item.Row<R> && new is Item.Row<R> -> {
            val oldProperty = old.item::class.members.firstOrNull { property -> property == rowProperty }
            val newProperty = new.item::class.members.firstOrNull { property -> property == rowProperty }
            if (oldProperty != null && newProperty != null) oldProperty == newProperty else false
        }
        else -> false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(old: Item<H, R>, new: Item<H, R>): Boolean = old == new
}

sealed class Item<out H, out R> {
    data class Header<out H>(val item: H) : Item<H, Nothing>()
    data class Row<out R>(val item: R) : Item<Nothing, R>()
}

inline fun <reified H : Any, reified R : Any, VBH : ViewBinding, VBR : ViewBinding> headerListAdapter(
    noinline bindingHeader: (LayoutInflater, ViewGroup, Boolean) -> VBH,
    noinline bindingRow: (LayoutInflater, ViewGroup, Boolean) -> VBR,
    headerIdentifier: KProperty1<H, *>? = null,
    rowIdentifier: KProperty1<R, *>? = null,
    noinline onBindHeader: VBH.(H) -> Unit,
    noinline onBindRow: VBR.(R) -> Unit,
): Lazy<BaseHeaderListAdapter<H, R, VBH, VBR>> {
    return lazy {
        BaseHeaderListAdapter(
            bindingHeader,
            bindingRow,
            headerIdentifier,
            rowIdentifier,
            onBindHeader,
            onBindRow
        )
    }
}

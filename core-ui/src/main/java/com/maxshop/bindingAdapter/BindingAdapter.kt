package com.maxshop.bindingAdapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.R
import com.maxshop.adapter.DataBindingRecyclerAdapter
import com.maxshop.model.RecyclerItem
import com.squareup.picasso.Picasso

@BindingAdapter("android:src")
fun setImage(view: AppCompatImageView, imageUrl: String?) {
    Picasso.with(view.context)
        .load(imageUrl)
        .placeholder(R.drawable.loading_anim)
        .fit()
        .centerCrop()
        .into(view)
}

@BindingAdapter("onError")
fun setImage(view: AppCompatImageView, boolean: Boolean) {
    if (boolean) Picasso.with(view.context)
        .load(R.drawable.ic_error)
        .fit()
        .into(view)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun setItems(view: RecyclerView, items: List<RecyclerItem>?) {
    var adapter = view.adapter as? DataBindingRecyclerAdapter
    if (adapter == null) {
        adapter = DataBindingRecyclerAdapter()
        view.adapter = adapter
    }
    adapter.submitList(items)
}

@BindingAdapter("visibleIfNotNull")
internal fun setVisibilityIfNotNull(view: View, data: Any?) {
    view.visibility = if (data != null) View.VISIBLE else View.INVISIBLE
}

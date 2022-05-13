package com.maxshop.bindingAdapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.example.core_ui.R
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

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

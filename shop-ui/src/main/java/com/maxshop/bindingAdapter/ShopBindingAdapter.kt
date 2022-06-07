package com.maxshop.bindingAdapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.maxshop.model.TypeSort
import com.maxshop.shop_ui.R

@BindingAdapter("isActiveSort")
internal fun setActiveSortTextView(view: TextView, state: Boolean) {
    if (state) {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorSecondaryText))
    } else {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorBackground))
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorText))
    }
}

@BindingAdapter("android:text")
internal fun setSortType(view: AppCompatTextView, type: TypeSort?) {
    when (type) {
        TypeSort.Popular ->
            view.text = view.context.getString(R.string.sort_type_popular)
        TypeSort.PriceLowestToHigh ->
            view.text = view.context.getString(R.string.sort_type_price_lowest_to_high)
        TypeSort.PriceHighestToLow ->
            view.text = view.context.getString(R.string.sort_type_price_highest_to_low)
    }
}

@BindingAdapter("visibleIfNotNull")
internal fun setVisibilityIfNotNull(view: View, data: Any?) {
    view.visibility = if (data != null) View.VISIBLE else View.INVISIBLE
}

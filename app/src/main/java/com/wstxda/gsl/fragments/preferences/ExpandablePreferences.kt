package com.wstxda.gsl.fragments.preferences

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.preference.PreferenceGroup
import androidx.preference.PreferenceViewHolder
import com.wstxda.gsl.R

class ExpandablePreferences @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : PreferenceGroup(context, attrs) {

    private var expanded = false
    private var indicatorView: ImageView? = null

    init {
        isPersistent = false
        layoutResource = R.layout.preference_material_expandable
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        indicatorView = holder.findViewById(R.id.expanded_indicator) as? ImageView
        updateIndicator()

        holder.itemView.setOnClickListener {
            toggleExpanded()
        }
    }

    private fun toggleExpanded() {
        expanded = !expanded
        updateChildrenVisibility()
        updateIndicator()
    }

    private fun updateChildrenVisibility() {
        for (i in 0 until preferenceCount) {
            getPreference(i).isVisible = expanded
        }
    }

    private fun updateIndicator() {
        indicatorView?.setImageResource(
            if (expanded) R.drawable.ic_arrow_up
            else R.drawable.ic_arrow_down
        )
    }

    override fun onAttached() {
        super.onAttached()
        updateChildrenVisibility()
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable("super", super.onSaveInstanceState())
            putBoolean("expanded", expanded)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            expanded = state.getBoolean("expanded", false)
            val superState = if (Build.VERSION.SDK_INT >= 33) {
                state.getParcelable("super", Parcelable::class.java)
            } else {
                @Suppress("DEPRECATION") state.getParcelable("super")
            }
            super.onRestoreInstanceState(superState)
            updateChildrenVisibility()
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}
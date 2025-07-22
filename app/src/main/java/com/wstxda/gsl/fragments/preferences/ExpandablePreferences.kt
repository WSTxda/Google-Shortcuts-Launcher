package com.wstxda.gsl.fragments.preferences

import android.content.Context
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

    companion object {
        private const val STATE_SUPER = "super_state"
        private const val STATE_EXPANDED_PREFIX = "expanded_"
    }

    private var isExpanded = false
    var onExpansionChanged: ((Boolean) -> Unit)? = null

    init {
        isPersistent = false
        layoutResource = R.layout.preference_material_expandable
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        val indicator = holder.findViewById(R.id.expanded_indicator) as? ImageView

        indicator?.rotation = if (isExpanded) 180f else 0f

        holder.itemView.setOnClickListener {
            toggleExpanded()

            indicator?.animate()?.rotation(if (isExpanded) 180f else 0f)?.setDuration(300)?.start()

            notifyChanged()
        }
    }

    private fun toggleExpanded() {
        isExpanded = !isExpanded
        updateChildrenVisibility()
        onExpansionChanged?.invoke(isExpanded)
    }

    private fun updateChildrenVisibility() {
        for (i in 0 until preferenceCount) {
            getPreference(i).isVisible = isExpanded
        }
    }

    override fun onAttached() {
        super.onAttached()
        updateChildrenVisibility()
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(STATE_SUPER, super.onSaveInstanceState())
            putBoolean(STATE_EXPANDED_PREFIX + key, isExpanded)
        }
    }

    @Suppress("DEPRECATION")
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            isExpanded = state.getBoolean(STATE_EXPANDED_PREFIX + key, false)
            val superState = state.getParcelable<Parcelable>(STATE_SUPER)
            super.onRestoreInstanceState(superState)
            updateChildrenVisibility()
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}
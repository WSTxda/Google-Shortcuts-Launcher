package com.wstxda.gsl.fragments.preferences

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.preference.PreferenceGroup
import androidx.preference.PreferenceViewHolder
import com.wstxda.gsl.R
import androidx.core.content.withStyledAttributes

class ExpandablePreferences @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : PreferenceGroup(context, attrs) {

    var onExpansionChanged: ((Boolean) -> Unit)? = null

    private var isExpanded: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                updateChildrenVisibility()
                onExpansionChanged?.invoke(value)
            }
        }

    init {
        isPersistent = false
        layoutResource = R.layout.preference_material_expandable

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.ExpandablePreferences, 0, 0) {
                isExpanded = getBoolean(R.styleable.ExpandablePreferences_isExpanded, false)
            }
        }
    }

    override fun onAttached() {
        super.onAttached()
        updateChildrenVisibility()
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val indicator = holder.findViewById(R.id.expanded_indicator) as? ImageView ?: return
        indicator.rotation = if (isExpanded) 180f else 0f

        holder.itemView.setOnClickListener {
            indicator.animate().rotation(if (isExpanded) 0f else 180f).setDuration(400).start()

            isExpanded = !isExpanded
        }
    }

    private fun updateChildrenVisibility() {
        for (i in 0 until preferenceCount) {
            getPreference(i).isVisible = isExpanded
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val myState = SavedState(superState)
        myState.isExpanded = this.isExpanded
        return myState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        isExpanded = state.isExpanded
    }

    private class SavedState : BaseSavedState {
        var isExpanded: Boolean = false

        constructor(superState: Parcelable?) : super(superState)

        private constructor(source: Parcel) : super(source) {
            isExpanded = source.readInt() == 1
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(if (isExpanded) 1 else 0)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState = SavedState(parcel)
            override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
        }
    }
}
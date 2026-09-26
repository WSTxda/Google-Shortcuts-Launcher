package com.wstxda.gsl.preference

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.google.android.material.slider.Slider
import com.wstxda.gsl.R
import com.wstxda.gsl.utils.Constants
import kotlin.math.roundToInt

class ShortcutLaunchDelayPreference @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Preference(context, attrs) {

    private var delayMillis = 0

    init {
        layoutResource = R.layout.preference_material_slider
        isSelectable = false
        summaryProvider = SummaryProvider<ShortcutLaunchDelayPreference> {
            it.formatDelay(it.delayMillis)
        }
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? = a.getString(index)

    override fun onSetInitialValue(defaultValue: Any?) {
        val storedValue = getPersistedString(defaultValue as? String)?.toLongOrNull() ?: 0L
        val boundedValue = storedValue.coerceIn(
            0L, Constants.SHORTCUT_LAUNCH_DELAY_MAX_MS.toLong()
        ).toInt()
        val step = Constants.SHORTCUT_LAUNCH_DELAY_STEP_MS
        delayMillis = (boundedValue / step) * step
        persistString(delayMillis.toString())
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val slider = holder.findViewById(R.id.preference_slider) as Slider
        val valueView = holder.findViewById(android.R.id.summary) as TextView

        slider.clearOnChangeListeners()
        slider.valueFrom = 0f
        slider.valueTo = Constants.SHORTCUT_LAUNCH_DELAY_MAX_MS.toFloat()
        slider.stepSize = Constants.SHORTCUT_LAUNCH_DELAY_STEP_MS.toFloat()
        slider.value = delayMillis.toFloat()
        slider.isEnabled = isEnabled
        slider.contentDescription = title
        slider.setLabelFormatter { formatDelay(it.roundToInt()) }

        val spacing = context.resources.getDimensionPixelSize(
            R.dimen.preference_slider_horizontal_spacing
        )
        val trackInset = slider.trackSidePadding

        slider.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            marginStart = spacing - trackInset
            marginEnd = spacing - trackInset
        }

        slider.addOnChangeListener { view, value, fromUser ->
            if (!fromUser) return@addOnChangeListener
            val newValue = value.roundToInt()
            if (newValue == delayMillis) return@addOnChangeListener

            if (!callChangeListener(newValue.toString())) {
                view.value = delayMillis.toFloat()
                return@addOnChangeListener
            }

            delayMillis = newValue
            persistString(newValue.toString())
            valueView.text = summary
        }
    }

    private fun formatDelay(value: Int): String = if (value == 0) {
        context.getString(R.string.shortcut_launch_delay_none)
    } else {
        context.getString(R.string.shortcut_launch_delay_value, value)
    }
}
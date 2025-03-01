package com.wstxda.gsl.fragments.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.wstxda.gsl.R
import com.wstxda.gsl.services.UpdaterService

class UpdaterPreference @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Preference(context, attrs, defStyleAttr) {

    init {
        layoutResource = R.layout.preference_material_updater
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        holder.itemView.isClickable = false
        holder.itemView.isFocusable = false

        holder.findViewById(android.R.id.summary)?.let {
            it as android.widget.TextView
            it.text = getVersionName(context)
        }

        val button = holder.findViewById(R.id.check_updates) as? Button
        button?.setOnClickListener {
            UpdaterService.checkForUpdates(context, holder.itemView)
        }
    }

    private fun getVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "N/A"
        } catch (_: Exception) {
            "N/A"
        }
    }
}
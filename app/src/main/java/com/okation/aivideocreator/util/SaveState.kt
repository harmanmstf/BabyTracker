package com.okation.aivideocreator.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

class SaveState(
    private val firstInput: TextView,
    private val secondInput: TextView,
    private val saveButton: Button,
) {

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            updateSaveButtonVisibility()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private fun updateSaveButtonVisibility() {
        val firstInputText = firstInput.text.toString()
        val secondInputText = secondInput.text.toString()

        val isFieldsNotEmpty = firstInputText.isNotEmpty() && secondInputText.isNotEmpty()
        saveButton.isVisible = isFieldsNotEmpty
    }
}

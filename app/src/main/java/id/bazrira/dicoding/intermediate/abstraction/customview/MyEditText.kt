package id.bazrira.dicoding.intermediate.abstraction.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.bazrira.dicoding.intermediate.R

class MyEditText : TextInputLayout {

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    init()
  }

  private fun init() {
    val editText = TextInputEditText(context)
    editText.hint = "Password"
    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

    editText.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        error =
          if (!p0.isNullOrEmpty() && p0.length < 6) context.getString(R.string.enter_a_minimum_of_six_characters) else null
      }

      override fun afterTextChanged(p0: Editable?) {}

    })

    addView(editText)
  }
}
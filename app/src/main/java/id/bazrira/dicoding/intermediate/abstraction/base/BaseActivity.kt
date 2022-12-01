package id.bazrira.dicoding.intermediate.abstraction.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import id.bazrira.dicoding.intermediate.abstraction.utils.StatusBar.transparentStatusBar
import id.bazrira.dicoding.intermediate.databinding.CustomDialogBinding

abstract class BaseActivity<B : ViewBinding>(val viewBinder: (LayoutInflater) -> B) :
  AppCompatActivity() {
  protected lateinit var binding: B
  protected lateinit var alertDialog: AlertDialog
  protected lateinit var bindingDialog: CustomDialogBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = viewBinder(layoutInflater)
    setContentView(binding.root)

    binding.oncreate(savedInstanceState)

    transparentStatusBar(window)

    observerViewModel()
  }

  protected abstract fun B.oncreate(savedInstanceState: Bundle?)

  protected abstract fun observerViewModel()

  protected fun setCustomDialog(actionClick: OnClickListener = OnClickListener { alertDialog.dismiss() }) {
    bindingDialog = CustomDialogBinding.inflate(layoutInflater)

    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setView(bindingDialog.root)
    alertDialogBuilder.setCancelable(false)

    bindingDialog.buttonOk.setOnClickListener(actionClick)

    alertDialog = alertDialogBuilder.create()
  }

  protected fun showProgressDialog() {
    bindingDialog.textViewStatus.visibility = View.GONE
    bindingDialog.textViewDescription.visibility = View.GONE
    bindingDialog.buttonOk.visibility = View.GONE
    bindingDialog.progressBar.visibility = View.VISIBLE

    alertDialog.show()
  }

  protected fun showMessageDialog(title: String, message: String?) {
    bindingDialog.textViewStatus.text = title
    bindingDialog.textViewStatus.visibility = View.VISIBLE

    bindingDialog.textViewDescription.text = message
    bindingDialog.textViewDescription.visibility = View.VISIBLE

    bindingDialog.buttonOk.visibility = View.VISIBLE
    bindingDialog.progressBar.visibility = View.GONE
  }

  protected fun hideDialog() {
    alertDialog.dismiss()
  }
}
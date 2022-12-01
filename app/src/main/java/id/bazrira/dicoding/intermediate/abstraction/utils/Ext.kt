package id.bazrira.dicoding.intermediate.abstraction.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(drawable: Drawable?) {
  Glide.with(context)
    .load(drawable)
    .circleCrop()
    .apply(RequestOptions())
    .into(this)
}

fun ImageView.load(string: String?) {
  Glide.with(context)
    .load(string)
    .fitCenter()
    .apply(RequestOptions())
    .into(this)
}

fun ImageView.load(bitmap: Bitmap?) {
  Glide.with(context)
    .load(bitmap)
    .fitCenter()
    .apply(RequestOptions())
    .into(this)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (t: T) -> Unit) {
  liveData?.observe(this) { it?.let { t -> action(t) } }
}

fun Context.showToast(message: String?) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
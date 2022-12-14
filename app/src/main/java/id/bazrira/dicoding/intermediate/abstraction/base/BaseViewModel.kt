package id.bazrira.dicoding.intermediate.abstraction.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  fun subscribe(it: Disposable) {
    compositeDisposable.remove(it)
    compositeDisposable.add(it)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
package id.bazrira.dicoding.intermediate.di.module.features

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelFactory
import id.bazrira.dicoding.intermediate.di.scope.FeatureScope

@Module
abstract class ViewModelModule {

  @FeatureScope
  @Binds
  internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
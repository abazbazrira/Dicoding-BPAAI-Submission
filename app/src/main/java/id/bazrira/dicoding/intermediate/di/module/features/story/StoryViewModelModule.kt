package id.bazrira.dicoding.intermediate.di.module.features.story

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelKey
import id.bazrira.dicoding.intermediate.di.module.features.ViewModelModule
import id.bazrira.dicoding.intermediate.features.story.formstory.FormStoryViewModel
import id.bazrira.dicoding.intermediate.features.story.liststory.ListStoryViewModel

@Module(
  includes = [
    ViewModelModule::class
  ]
)
abstract class StoryViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(ListStoryViewModel::class)
  internal abstract fun bindListStoryViewModel(viewModel: ListStoryViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(FormStoryViewModel::class)
  internal abstract fun bindFormStoryViewModel(viewModel: FormStoryViewModel): ViewModel
}
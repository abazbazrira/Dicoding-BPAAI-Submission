package id.bazrira.dicoding.intermediate.di.component.features.story

import dagger.Component
import id.bazrira.dicoding.intermediate.di.component.RepoComponent
import id.bazrira.dicoding.intermediate.di.module.features.story.StoryModule
import id.bazrira.dicoding.intermediate.di.module.features.story.StoryViewModelModule
import id.bazrira.dicoding.intermediate.di.scope.FeatureScope
import id.bazrira.dicoding.intermediate.features.story.formstory.FormStoryActivity
import id.bazrira.dicoding.intermediate.features.story.liststory.ListStoryActivity

@FeatureScope
@Component(
  dependencies = [RepoComponent::class],
  modules = [
    StoryModule::class,
    StoryViewModelModule::class,
  ]
)
interface StoryComponent {
  fun inject(target: ListStoryActivity)
  fun inject(target: FormStoryActivity)
}
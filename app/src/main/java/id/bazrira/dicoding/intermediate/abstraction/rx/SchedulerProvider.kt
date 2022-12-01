package id.bazrira.dicoding.intermediate.abstraction.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
  fun io(): Scheduler
  fun ui(): Scheduler
}
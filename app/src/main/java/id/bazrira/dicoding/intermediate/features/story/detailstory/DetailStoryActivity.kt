package id.bazrira.dicoding.intermediate.features.story.detailstory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.bazrira.dicoding.intermediate.R
import id.bazrira.dicoding.intermediate.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

  private lateinit var binding: ActivityDetailStoryBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityDetailStoryBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}
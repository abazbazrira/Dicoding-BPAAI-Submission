package id.bazrira.dicoding.intermediate.data.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_data")
data class StoryDataEntity(
  @PrimaryKey(autoGenerate = true)
  @NonNull
  val id: Int,
)
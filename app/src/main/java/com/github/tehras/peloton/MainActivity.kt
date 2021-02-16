package com.github.tehras.peloton

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle

class MainActivity : AppCompatActivity() {

  /**
   * We're using androidx instead of Koin here because [NavigationViewModel] requires
   * [SavedStateHandle] which is not possible with Koin.
   **/
  private val navigationViewModel by viewModels<NavigationViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent { PelotonApp(navigationViewModel) }
  }

  override fun onBackPressed() {
    if (!navigationViewModel.onBack()) {
      super.onBackPressed()
    }
  }
}
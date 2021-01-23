package com.github.tehras.peloton.followers.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onCommit
import androidx.compose.ui.res.stringResource
import com.github.tehras.peloton.R
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.followers.list.FollowersState.Loading
import com.github.tehras.peloton.overview.Overview
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.shared.LoadingScreenNotFull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.getViewModel

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun FollowingListScreen(
  userId: String,
  navigateTo: (Screen) -> Unit,
  followersListViewModel: FollowersListViewModel = getViewModel(),
  stateUpdated: () -> Unit
) {

  val state: State<FollowersState> = followersListViewModel.followersState
    .collectAsState(initial = Loading)

  onCommit(userId) {
    followersListViewModel.fetchFollowing(userId = userId)
  }

  when (val data = state.value) {
    Loading -> LoadingScreenNotFull()
    is FollowersState.Success -> {
      stateUpdated()
      ListOfFollowers(
        data = data,
        title = stringResource(id = R.string.following_titles)
      ) { newUserId ->
        navigateTo(Overview(userId = newUserId))
      }
    }
  }
}
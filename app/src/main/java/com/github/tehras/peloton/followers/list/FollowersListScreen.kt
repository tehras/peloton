package com.github.tehras.peloton.followers.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.github.tehras.peloton.R
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.followers.list.FollowersState.Loading
import com.github.tehras.peloton.overview.Overview
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.getViewModel

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun FollowersListScreen(
    userId: String,
    navigateTo: (Screen) -> Unit
) {
    val followersListViewModel: FollowersListViewModel = getViewModel()

    val state: State<FollowersState> = followersListViewModel.followersState
        .collectAsState(initial = Loading)

    when (val data = state.value) {
        Loading -> {
            followersListViewModel.fetchFollowers(userId = userId)
            LoadingScreen()
        }
        is FollowersState.Success -> ListOfFollowers(
            data = data,
            title = stringResource(id = R.string.followers_title)
        ) { newUserId ->
            navigateTo(Overview(userId = newUserId))
        }
    }
}
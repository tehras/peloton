package com.github.tehras.peloton.followers.list

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.Follower
import com.github.tehras.peloton.followers.list.FollowersState.Loading
import com.github.tehras.peloton.home.Avatar
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.inject

@ExperimentalLazyDsl
@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun FollowersListScreen() {
    val followersListViewModel: FollowersListViewModel by inject()

    val state: State<FollowersState> = followersListViewModel.followersState
        .collectAsState(initial = Loading)

    when (val data = state.value) {
        Loading -> {
            followersListViewModel.fetchFollowers()
            LoadingScreen()
        }
        is FollowersState.Success -> ListOfFollowers(data)
    }
}

@ExperimentalLazyDsl
@Composable
fun ListOfFollowers(data: FollowersState.Success) {
    LazyColumnFor(items = data.followers) { item ->
        FollowerView(item)
        Divider()
    }
}

@Composable
fun FollowerView(follower: Follower) {
    ConstraintLayout(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Create references.
        val (avatar, name, location) = createRefs()

        Avatar(
            url = follower.image_url,
            modifier = Modifier.constrainAs(avatar) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = follower.username,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(avatar.end)
                    bottom.linkTo(location.top)
                    if (follower.location.isEmpty()) {
                        bottom.linkTo(parent.bottom)
                    }
                }
        )

        Text(
            text = follower.location,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(location) {
                    top.linkTo(name.bottom)
                    start.linkTo(name.start)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(name.bottom)
                }
        )
    }
}

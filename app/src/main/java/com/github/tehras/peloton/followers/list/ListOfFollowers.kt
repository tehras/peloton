package com.github.tehras.peloton.followers.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.tehras.data.data.Follower
import com.github.tehras.peloton.home.Avatar

@Composable
fun ListOfFollowers(
  data: FollowersState.Success,
  title: String,
  followerSelected: (String) -> Unit
) {
  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.h6,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(vertical = 8.dp)
    )
    LazyColumn {
      items(items = data.followers) { item ->
        FollowerView(item) {
          followerSelected(item.id)
        }
        Divider()
      }
    }
  }
}

@Composable
fun FollowerView(follower: Follower, followerClicked: () -> Unit) {
  ConstraintLayout(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth()
      .clickable(onClick = followerClicked)
  ) {
    // Create references.
    val (avatar, name, location) = createRefs()

    Avatar(
      url = follower.image_url,
      contentDescription = "Image for $name",
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
          top.linkTo(name.bottom)
        }
    )
  }
}

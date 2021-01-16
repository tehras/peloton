package com.github.tehras.peloton.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.User

@Composable
fun HeaderArea(
    data: User,
    followersClicked: () -> Unit,
    followingClicked: () -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(4.dp)
            .background(color = MaterialTheme.colors.background)
            .padding(8.dp)
    ) {
        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            // Create references.
            val (avatar, name, location, tags, followers) = createRefs()

            Avatar(
                url = data.image_url,
                modifier = Modifier.constrainAs(avatar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(tags.bottom)
                    start.linkTo(parent.start)
                }
            )

            Text(
                text = data.username,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = data.location,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(location) {
                        top.linkTo(name.bottom)
                        start.linkTo(name.start)
                        end.linkTo(name.end)
                    }
            )

            Tag(
                tags = data.tags_info,
                modifier = Modifier
                    .constrainAs(tags) {
                        top.linkTo(location.bottom)
                        start.linkTo(location.start)
                        end.linkTo(location.end)
                    }
                    .padding(vertical = 4.dp)
            )

            Followers(
                user = data,
                modifier = Modifier
                    .constrainAs(followers) {
                        top.linkTo(tags.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                followersClicked = followersClicked,
                followingClicked = followingClicked
            )
        }
    }
}
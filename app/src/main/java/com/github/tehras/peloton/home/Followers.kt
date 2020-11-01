package com.github.tehras.peloton.home

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.User
import com.github.tehras.peloton.R

@Composable
fun Followers(
    user: User,
    modifier: Modifier,
    followersClicked: () -> Unit,
    followingClicked: () -> Unit
) {
    Column(modifier = modifier.padding(top = 8.dp)) {
        Divider()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Text(
                modifier = Modifier
                    .clickable(onClick = followersClicked)
                    .padding(top = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.body2,
                text = stringResource(id = R.string.followers, user.total_followers)
            )
            Text(
                modifier = Modifier
                    .clickable(onClick = followingClicked)
                    .padding(top = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.body2,
                text = stringResource(id = R.string.following, user.total_following)
            )
        }
    }
}
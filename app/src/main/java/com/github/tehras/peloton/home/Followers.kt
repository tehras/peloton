package com.github.tehras.peloton.home

import androidx.compose.foundation.Text
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
    modifier: Modifier
) {
    Column(modifier = modifier.padding(top = 8.dp)) {
        Divider()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.body2,
                text = stringResource(id = R.string.followers, user.total_followers)
            )
            Text(
                style = MaterialTheme.typography.body2,
                text = stringResource(id = R.string.following, user.total_following)
            )
        }
    }
}
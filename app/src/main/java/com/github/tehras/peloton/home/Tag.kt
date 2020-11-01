package com.github.tehras.peloton.home

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.User

@Composable
fun Tag(
    tags: User.TagInfo?,
    modifier: Modifier
) {
    if (tags == null) {
        Surface(modifier = modifier) {

        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .drawShadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                    clip = false
                )
                .background(
                    color = MaterialTheme.colors.background,
                    shape = CircleShape
                )
                .padding(top = 2.dp, bottom = 2.dp, start = 6.dp)
        ) {
            Text(
                text = tags.primary_name,
                style = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(end = 4.dp)
            )

            if (tags.total_joined > 1) {
                Text(
                    text = "+${tags.total_joined - 1}",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}
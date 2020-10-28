package com.github.tehras.peloton.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.github.tehras.data.data.User
import com.github.tehras.peloton.common.CoilImage

@Composable
fun HeaderArea(data: User) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(4.dp)
            .background(color = Color.White)
            .padding(8.dp)
    ) {
        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            // Create references.
            val (avatar, name, location, tags) = createRefs()

            Avatar(
                url = data.image_url,
                modifier = Modifier.constrainAs(avatar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
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
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun Avatar(
    url: String,
    modifier: Modifier
) {
    CoilImage(
        data = url,
        modifier = modifier
            .size(64.dp)
            .border(
                border = BorderStroke(4.dp, color = Color.White),
                shape = CircleShape
            )
            .drawShadow(
                elevation = 2.dp,
                shape = CircleShape,
                clip = false
            )
            .padding(2.dp)
    ) {
        transformations(CircleCropTransformation())
    }
}

@Composable
fun Tag(
    tags: User.TagInfo,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .drawShadow(
                elevation = 2.dp,
                shape = CircleShape,
                clip = false
            )
            .background(
                color = Color.White,
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
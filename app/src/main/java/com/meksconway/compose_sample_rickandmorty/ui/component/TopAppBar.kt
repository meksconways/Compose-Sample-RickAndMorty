package com.meksconway.compose_sample_rickandmorty.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meksconway.compose_sample_rickandmorty.R
import com.meksconway.compose_sample_rickandmorty.ui.theme.smokeWhite
import com.meksconway.compose_sample_rickandmorty.ui.theme.teal200
import com.meksconway.compose_sample_rickandmorty.ui.theme.typography

@Composable
fun TopAppBar() {

    Card(elevation = 5.dp,
    shape =  RoundedCornerShape(8.dp).copy(
        topLeft = CornerSize(0.dp),
        topRight = CornerSize(0.dp),
    )) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.surface.copy(alpha = 0.8f)
                    } else {
                        smokeWhite
                    }
                )
                .padding(16.dp)

        ) {

            Text(
                text = stringResource(id = R.string.appbar_title),
                modifier = Modifier.align(Alignment.CenterStart),
                style = typography.h5.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }




}
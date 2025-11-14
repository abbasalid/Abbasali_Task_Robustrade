package com.example.abbasali_task_robustrade.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.abbasali_task_robustrade.R
import com.example.abbasali_task_robustrade.presentation.theme.NewsApp

@Composable
fun ErrorMessage(
    error: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(NewsApp.spacing.large),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NewsApp.colors.errorLight)
                .padding(NewsApp.spacing.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(R.string.default_error_icon),
                tint = NewsApp.colors.error
            )

            Spacer(modifier = Modifier.width(NewsApp.spacing.large))

            Text(
                text = error,
                modifier = Modifier.weight(1f),
                style = NewsApp.typography.bodyMedium
            )

            IconButton(
                onClick = { onDismiss.invoke() },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_error),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Error_Preview() {
    ErrorMessage(
        error = "Something went wrong",
        onDismiss = {},
    )
}
package com.enriqueajin.newsapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.R

@Composable
fun Error(modifier: Modifier = Modifier, errorMessage: UiText, onRetryAction: (() -> Unit)? = null) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier.align(Alignment.Center).padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.ic_error),
                tint = Color.Red,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = errorMessage.asString(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(15.dp))
            onRetryAction?.let {
                Button(onClick = onRetryAction) {
                    Text(
                        text = stringResource(R.string.error_retry_button),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    Error(
        errorMessage = UiText.DynamicString("An error occurred. Please check your internet connection and try again."),
        onRetryAction = {}
    )
}
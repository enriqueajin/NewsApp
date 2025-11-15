package com.enriqueajin.newsapp.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(state: Lifecycle.State = Lifecycle.State.STARTED, block: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect {
                block(it)
            }
        }
    }
}

inline fun <T> MutableStateFlow<T>.updateState(transform: (T) -> T) {
    value = transform(value)
}

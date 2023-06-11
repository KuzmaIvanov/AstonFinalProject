package com.example.astonfinalproject.data.network

sealed class NetworkUiState {
    class Success<T>(val data: T): NetworkUiState()
    class Error(val message: Throwable): NetworkUiState()
    object Empty: NetworkUiState()
}
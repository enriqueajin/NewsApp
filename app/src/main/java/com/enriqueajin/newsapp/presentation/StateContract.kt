package com.enriqueajin.newsapp.presentation

interface StateContract {
    val loading: Boolean
    val error: String
}
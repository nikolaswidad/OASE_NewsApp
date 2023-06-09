package com.nikolaswidad.oasesimpleapp.ui

sealed class UiResult<out R> private constructor() {
    data class Success<out T>(val data: T) : UiResult<T>()
    data class Error(val error: String) : UiResult<Nothing>()
    object Loading : UiResult<Nothing>()
}
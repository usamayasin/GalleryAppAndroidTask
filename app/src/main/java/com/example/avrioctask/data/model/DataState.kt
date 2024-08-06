package com.example.avrioctask.data.model


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

sealed class DataState<T>(
    val data: T? = null,
    val error: CustomMessages = CustomMessages.SomethingWentWrong("Something Went Wrong")
) {
    class Success<T>(data: T?) : DataState<T>(data)
    class Error<T>(customMessages: CustomMessages) : DataState<T>(error = customMessages)
    sealed class CustomMessages(open val message: String = "") {
        class EmptyData(override val message: String = "No Data Found") : CustomMessages(message)
        data class SomethingWentWrong(val error: String) : CustomMessages(message = error)
    }
}

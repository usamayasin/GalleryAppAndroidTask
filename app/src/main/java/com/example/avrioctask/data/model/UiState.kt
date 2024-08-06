package com.example.avrioctask.data.model

sealed class UIState

object LoadingState : UIState()
object ContentState : UIState()
class ErrorState(val message: String) : UIState()
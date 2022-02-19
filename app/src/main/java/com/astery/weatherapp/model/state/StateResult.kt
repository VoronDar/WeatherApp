package com.astery.weatherapp.model.state

/** generic based state from all data */
sealed class StateResult
class StateError(private val error:ResultError):StateResult()
object StateLoading : StateResult()
class StateCompleted<T>(private val result:T, private val isFromCache:Boolean):StateResult()
/** completed, but got nothing when it is possible.
 * You need to use it for example, when you got no matching cities
 * But when you got nothing trying to get a weather, it is an error, and you should use StateError*/
object StateGotNothing:StateResult()

/** possible reasons to get an error from repository */
enum class ResultError{
    InvalidInternetConnection,
    UnexpectedBug
}
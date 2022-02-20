package com.astery.weatherapp.model.state

/** generic based state from all data */
sealed class StateResult<T>
class StateError<T>(val error: ResultError) : StateResult<T>()
class StateLoading<T> : StateResult<T>()
class StateCompleted<T>(val result: T, val isFromCache: Boolean) : StateResult<T>()

/** completed, but got nothing when it is possible.
 * You need to use it for example, when you got no matching cities
 * But when you got nothing trying to get a weather, it is an error, and you should use StateError*/
class StateGotNothing<T> : StateResult<T>()

/** When Loading didn't started */
class StateIdle<T> : StateResult<T>()

/** possible reasons to get an error from repository */
enum class ResultError {
    InvalidInternetConnection,
    UnexpectedBug,
    PermissionDenied,
    InvalidCity
}
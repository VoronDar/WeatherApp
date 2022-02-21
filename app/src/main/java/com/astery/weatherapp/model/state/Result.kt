package com.astery.weatherapp.model.state

/** generic based state from all data */
sealed class Result<T>
class Error<T>(val error: ResultError) : Result<T>(){
    /** possible reasons to get an error from repository */
    enum class ResultError {
        InvalidInternetConnection,
        UnexpectedBug,
        PermissionDenied,
        InvalidCity
    }
}
class Loading<T> : Result<T>()
class Completed<T>(val result: T, val isFromCache: Boolean) : Result<T>()

/** completed, but got nothing when it is possible.
 * You need to use it for example, when you got no matching cities
 * But when you got nothing trying to get a weather, it is an error, and you should use StateError*/
class GotNothing<T> : Result<T>()

/** When Loading didn't started */
class Idle<T> : Result<T>()

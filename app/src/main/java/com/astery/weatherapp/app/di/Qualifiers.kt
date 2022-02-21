package com.astery.weatherapp.app.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Fake


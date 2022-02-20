package com.astery.weatherapp.storage.remote.retrofit.cities

import com.astery.weatherapp.model.pogo.City
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("locations/v1/cities/geoposition/search")
    suspend fun getCityByLocation(
        @Query("apikey") apiKey: String, @Query("language") language: String,
        @Query("toplevel") topInfo: Boolean,
        @Query("q") latitude: String,
    ): City
}


/*
ciy search

  {
    "Version": 1,
    "Key": "1696340",
    "Type": "City",
    "Rank": 85,
    "LocalizedName": "Новый",
    "EnglishName": "Novyy",
    "PrimaryPostalCode": "",
    "Region": {
      "ID": "ASI",
      "LocalizedName": "Азия",
      "EnglishName": "Asia"
    },
    "Country": {
      "ID": "RU",
      "LocalizedName": "Россия",
      "EnglishName": "Russia"
    },
    "AdministrativeArea": {
      "ID": "ARK",
      "LocalizedName": "Архангельск",
      "EnglishName": "Arkhangel'sk",
      "Level": 1,
      "LocalizedType": "Область",
      "EnglishType": "Oblast",
      "CountryID": "RU"
    },
    "TimeZone": {
      "Code": "MSK",
      "Name": "Europe/Moscow",
      "GmtOffset": 3,
      "IsDaylightSaving": false,
      "NextOffsetChange": null
    },
    "GeoPosition": {
      "Latitude": 61.048,
      "Longitude": 40.888,
      "Elevation": {
        "Metric": {
          "Value": 185,
          "Unit": "m",
          "UnitType": 5
        },
        "Imperial": {
          "Value": 606,
          "Unit": "ft",
          "UnitType": 0
        }
      }
    },
    "IsAlias": false,
    "SupplementalAdminAreas": [
      {
        "Level": 2,
        "LocalizedName": "Konoshsky",
        "EnglishName": "Konoshsky"
      }
    ],
    "DataSets": [
      "AirQualityCurrentConditions",
      "AirQualityForecasts",
      "Alerts",
      "DailyPollenForecast",
      "ForecastConfidence",
      "FutureRadar",
      "MinuteCast"
    ]
  },
  {
    "Version": 1,
    "Key": "2430883",
    "Type": "City",
    "Rank": 85,
    "LocalizedName": "Новый",
    "EnglishName": "Novyy",
    "PrimaryPostalCode": "",
    "Region": {
      "ID": "ASI",
      "LocalizedName": "Азия",
      "EnglishName": "Asia"
    },
    "Country": {
      "ID": "RU",
      "LocalizedName": "Россия",
      "EnglishName": "Russia"
    },
    "AdministrativeArea": {
      "ID": "ALT",
      "LocalizedName": "Алтай",
      "EnglishName": "Altay",
      "Level": 1,
      "LocalizedType": "Край",
      "EnglishType": "Kray",
      "CountryID": "RU"
    },
    "TimeZone": {
      "Code": "MSK+4",
      "Name": "Asia/Barnaul",
      "GmtOffset": 7,
      "IsDaylightSaving": false,
      "NextOffsetChange": null
    },
    "GeoPosition": {
      "Latitude": 53.385,
      "Longitude": 83.999,
      "Elevation": {
        "Metric": {
          "Value": 151,
          "Unit": "m",
          "UnitType": 5
        },
        "Imperial": {
          "Value": 495,
          "Unit": "ft",
          "UnitType": 0
        }
      }
    },
    "IsAlias": false,
    "SupplementalAdminAreas": [
      {
        "Level": 2,
        "LocalizedName": "Pervomaysky",
        "EnglishName": "Pervomaysky"
      }
    ],
    "DataSets": [
      "AirQualityCurrentConditions",
      "AirQualityForecasts",
      "Alerts",
      "ForecastConfidence",
      "FutureRadar",
      "MinuteCast"
    ]
  },
  {
    "Version": 1,
    "Key": "2451775",
    "Type": "City",
    "Rank": 85,
    "LocalizedName": "Новый",
    "EnglishName": "Novyy",
    "PrimaryPostalCode": "",
    "Region": {
      "ID": "ASI",
      "LocalizedName": "Азия",
      "EnglishName": "Asia"
    },
    "Country": {
      "ID": "RU",
      "LocalizedName": "Россия",
      "EnglishName": "Russia"
    },
    "AdministrativeArea": {
      "ID": "ALT",
      "LocalizedName": "Алтай",
      "EnglishName": "Altay",
      "Level": 1,
      "LocalizedType": "Край",
      "EnglishType": "Kray",
      "CountryID": "RU"
    },
    "TimeZone": {
      "Code": "MSK+4",
      "Name": "Asia/Barnaul",
      "GmtOffset": 7,
      "IsDaylightSaving": false,
      "NextOffsetChange": null
    },
    "GeoPosition": {
      "Latitude": 53.174,
      "Longitude": 83.52,
      "Elevation": {
        "Metric": {
          "Value": 215,
          "Unit": "m",
          "UnitType": 5
        },
        "Imperial": {
          "Value": 705,
          "Unit": "ft",
          "UnitType": 0
        }
      }
    },
    "IsAlias": false,
    "SupplementalAdminAreas": [
      {
        "Level": 2,
        "LocalizedName": "Kalmansky",
        "EnglishName": "Kalmansky"
      }
    ],
    "DataSets": [
      "AirQualityCurrentConditions",
      "AirQualityForecasts",
      "Alerts",
      "ForecastConfidence",
      "FutureRadar",
      "MinuteCast"
    ]
  },
  {
    "Version": 1,
    "Key": "2451095",
    "Type": "City",
    "Rank": 85,
    "LocalizedName": "Новый",
    "EnglishName": "Novyy",
    "PrimaryPostalCode": "",
    "Region": {
      "ID": "ASI",
      "LocalizedName": "Азия",
      "EnglishName": "Asia"
    },
    "Country": {
      "ID": "RU",
      "LocalizedName": "Россия",
      "EnglishName": "Russia"
    },
    "AdministrativeArea": {
      "ID": "AD",
      "LocalizedName": "Адыгея",
      "EnglishName": "Adygeya",
      "Level": 1,
      "LocalizedType": "Республика",
      "EnglishType": "Republic",
      "CountryID": "RU"
    },
    "TimeZone": {
      "Code": "MSK",
      "Name": "Europe/Moscow",
      "GmtOffset": 3,
      "IsDaylightSaving": false,
      "NextOffsetChange": null
    },
    "GeoPosition": {
      "Latitude": 45.007,
      "Longitude": 38.98,
      "Elevation": {
        "Metric": {
          "Value": 26,
          "Unit": "m",
          "UnitType": 5
        },
        "Imperial": {
          "Value": 85,
          "Unit": "ft",
          "UnitType": 0
        }
      }
    },
    "IsAlias": false,
    "SupplementalAdminAreas": [
      {
        "Level": 2,
        "LocalizedName": "Takhtamukaysky",
        "EnglishName": "Takhtamukaysky"
      }
    ],
    "DataSets": [
      "AirQualityCurrentConditions",
      "AirQualityForecasts",
      "Alerts",
      "DailyPollenForecast",
      "ForecastConfidence",
      "FutureRadar",
      "MinuteCast"
    ]
  },
 */
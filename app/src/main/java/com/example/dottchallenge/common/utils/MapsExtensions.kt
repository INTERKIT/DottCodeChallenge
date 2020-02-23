package com.example.dottchallenge.common.utils

import android.location.Location
import com.example.dottchallenge.map.model.PlaceLocation
import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng() = LatLng(latitude, longitude)

fun PlaceLocation.toLatLng() = LatLng(lat, lng)
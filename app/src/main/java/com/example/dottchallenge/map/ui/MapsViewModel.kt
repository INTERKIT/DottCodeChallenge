package com.example.dottchallenge.map.ui

import androidx.lifecycle.MutableLiveData
import com.example.dottchallenge.common.base.BaseViewModel
import com.example.dottchallenge.map.domain.MapsInteractor
import com.example.dottchallenge.map.model.Venue
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val TIMEOUT_IN_MILLIS = 250L

class MapsViewModel(
    private val mapsInteractor: MapsInteractor
) : BaseViewModel() {

    val venuesLiveData = MutableLiveData<List<Venue>>()
    val errorLiveData = MutableLiveData<Nothing>()

    private var searchDisposable: Disposable? = null

    fun search(location: LatLng, bounds: LatLngBounds? = null) {
        searchDisposable?.dispose()
        searchDisposable = mapsInteractor.search(location, bounds)
            .delay(TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    Timber.d("Venues fetching completed")
                },
                onError = {
                    errorLiveData.value = null
                    Timber.e(it, "Error searching venues by location: $location")
                }
            )
            .also { it.disposeOnCleared() }
    }

    fun observeVenues() {
        mapsInteractor.getVenuesFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { venuesLiveData.postValue(it) },
                onError = { Timber.wtf(it, "Unexpected error occurred while observing venues") }
            )
            .disposeOnCleared()
    }
}
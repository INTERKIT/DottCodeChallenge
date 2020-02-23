package com.example.dottchallenge.details

import androidx.lifecycle.MutableLiveData
import com.example.dottchallenge.common.SingleLiveEvent
import com.example.dottchallenge.common.base.BaseViewModel
import com.example.dottchallenge.map.domain.MapsInteractor
import com.example.dottchallenge.map.model.Venue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class VenueViewModel(
    private val mapsInteractor: MapsInteractor
) : BaseViewModel() {

    val venueLiveData = MutableLiveData<Venue>()
    val errorLiveData = SingleLiveEvent<Nothing>()

    fun loadVenueById(venueId: String) {
        mapsInteractor.findById(venueId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { venueLiveData.postValue(it) },
                onError = {
                    Timber.e(it, "Error getting venue with id $venueId")
                    errorLiveData.value = null
                }
            )
            .disposeOnCleared()
    }
}
package com.example.dottchallenge.maps

import com.example.dottchallenge.DefaultRunner
import com.example.dottchallenge.VenuesData
import com.example.dottchallenge.map.domain.MapsInteractor
import com.example.dottchallenge.map.repository.MapsLocalRepository
import com.example.dottchallenge.map.repository.MapsRemoteRepository
import com.google.android.gms.maps.model.LatLng
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any

@RunWith(DefaultRunner::class)
class MapsInteractorTest {

    private lateinit var mapsLocalRepository: MapsLocalRepository
    private lateinit var mapsRemoteRepository: MapsRemoteRepository
    private lateinit var mapsInteractor: MapsInteractor

    @Before
    fun setUp() {
        mapsLocalRepository = mockk()
        mapsRemoteRepository = mockk()
        mapsInteractor = MapsInteractor(mapsRemoteRepository, mapsLocalRepository)
    }

    @Test
    fun `search - success`() {
        val location = LatLng(1.0, 2.0)
        every { mapsRemoteRepository.search(location) } returns Single.just(stubVenuesList())
        every { mapsLocalRepository.setVenues(any()) } returns Completable.complete()

        val subscription = mapsInteractor.search(location, any()).test()

        subscription
            .assertComplete()
            .dispose()

        verifyOrder {
            mapsRemoteRepository.search(any())
            mapsLocalRepository.setVenues(any())
        }

        confirmVerified(mapsRemoteRepository)
        confirmVerified(mapsLocalRepository)
    }

    @Test
    fun `search - remote error`() {
        val location = LatLng(1.0, 2.0)
        val error = Exception()

        every { mapsRemoteRepository.search(location) } returns Single.error(error)

        val subscription = mapsInteractor.search(location, any()).test()

        subscription
            .assertError(error)
            .dispose()

        verify(exactly = 0) {
            mapsLocalRepository.setVenues(any())
        }
    }

    @Test
    fun `find by id - success`() {
        every { mapsLocalRepository.getVenuesFlowable() } returns Flowable.just(stubVenuesList())

        val subsription = mapsInteractor.findById("1").test()

        subsription.assertValue { it.id == "1" }.dispose()

        verify(exactly = 1) {
            mapsLocalRepository.getVenuesFlowable()
        }
    }

    @Test
    fun `find by id - not found`() {
        every { mapsLocalRepository.getVenuesFlowable() } returns Flowable.just(emptyList())

        val suscription = mapsInteractor.findById("1").test()

        suscription.assertError { it is NullPointerException }
    }

    private fun stubVenuesList() = VenuesData.venues
}
package com.example.dottchallenge.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dottchallenge.R
import com.example.dottchallenge.common.utils.observeNonNull
import com.example.dottchallenge.common.utils.popBackStack
import com.example.dottchallenge.map.model.Venue
import kotlinx.android.synthetic.main.fragment_details.cityTextView
import kotlinx.android.synthetic.main.fragment_details.nameTextView
import kotlinx.android.synthetic.main.fragment_details.postalCodeTextView
import kotlinx.android.synthetic.main.fragment_details.stateTextView
import kotlinx.android.synthetic.main.fragment_details.toolbar
import kotlinx.android.synthetic.main.fragment_details.urlTextView
import org.koin.androidx.viewmodel.ext.android.viewModel

class VenueDetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_VENUE_ID = "EXTRA_VENUE_ID"

        fun create(venueId: String): VenueDetailsFragment {
            val fragment = VenueDetailsFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_VENUE_ID, venueId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val venueViewModel: VenueViewModel by viewModel()

    private val venueId: String by lazy { arguments?.getString(EXTRA_VENUE_ID).orEmpty() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        with(venueViewModel) {
            venueLiveData.observeNonNull(viewLifecycleOwner) { venue ->
                renderDetails(venue)
            }

            errorLiveData.observe(viewLifecycleOwner, Observer {
                showErrorDialog()
            })

            loadVenueById(venueId)
        }
    }

    private fun renderDetails(venue: Venue) {
        nameTextView.text = venue.name
        postalCodeTextView.text = venue.location.postalCode
        cityTextView.text = venue.location.city
        stateTextView.text = venue.location.state
        urlTextView.text = venue.url
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.action_ok) { _, _ -> popBackStack() }
            .setTitle(getString(R.string.details_error))
            .create()
            .show()
    }
}
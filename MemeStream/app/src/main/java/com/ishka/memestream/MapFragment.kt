package com.ishka.memestream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ishka.memestream.api.RetrofitClient
import com.ishka.memestream.databinding.FragmentMapBinding
import com.ishka.memestream.model.Meme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        loadMemesOnMap()
    }

    private fun loadMemesOnMap() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val memes = RetrofitClient.instance.getMemes()
                memes.forEach { meme ->
                    val lat = meme.latitude
                    val lng = meme.longitude
                    if (lat != null && lng != null) {
                        val marker = LatLng(lat, lng)
                        map.addMarker(
                            MarkerOptions()
                                .position(marker)
                                .title(meme.caption)
                        )
                    }
                }
                // Optional: move camera to first meme or default location
                memes.firstOrNull()?.let {
                    val lat = it.latitude ?: 0.0
                    val lng = it.longitude ?: 0.0
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 10f))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

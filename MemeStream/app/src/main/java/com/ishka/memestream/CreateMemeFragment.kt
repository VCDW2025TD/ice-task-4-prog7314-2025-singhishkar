package com.ishka.memestream

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ishka.memestream.api.RetrofitClient
import com.ishka.memestream.databinding.FragmentCreateMemeBinding
import com.ishka.memestream.model.MemePostRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class CreateMemeFragment : Fragment() {

    private var _binding: FragmentCreateMemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    private val requestLocationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            getLastLocation()
        } else {
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.buttonGetLocation.setOnClickListener {
            requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.imageViewSelected.setOnClickListener {
            val imageUrl = binding.editTextImageUrl.text.toString()
            if (imageUrl.isNotBlank()) {
                binding.imageViewSelected.load(imageUrl)
            } else {
                Toast.makeText(requireContext(), "Please enter a valid image URL", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonPostMeme.setOnClickListener {
            postMeme()
        }
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
            android.content.pm.PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), "Location permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    Toast.makeText(requireContext(), "Location: ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun postMeme() {
        val imageUrl = binding.editTextImageUrl.text.toString()
        val caption = binding.editTextCaption.text.toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (imageUrl.isBlank()) {
            Toast.makeText(requireContext(), "Enter an image URL", Toast.LENGTH_SHORT).show()
            return
        }
        if (caption.isBlank()) {
            Toast.makeText(requireContext(), "Enter a caption", Toast.LENGTH_SHORT).show()
            return
        }
        if (userId == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val memeRequest = MemePostRequest(
            imageUrl = imageUrl,
            caption = caption,
            userId = userId,
            latitude = currentLocation?.latitude,
            longitude = currentLocation?.longitude
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.postMeme(memeRequest)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Meme posted!", Toast.LENGTH_SHORT).show()
                    binding.editTextCaption.text.clear()
                    binding.editTextImageUrl.text.clear()
                    binding.imageViewSelected.setImageDrawable(null)
                    currentLocation = null
                } else {
                    Toast.makeText(requireContext(), "Failed to post meme", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error posting meme", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

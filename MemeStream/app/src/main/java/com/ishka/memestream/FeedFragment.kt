package com.ishka.memestream

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishka.memestream.api.RetrofitClient
import com.ishka.memestream.databinding.FragmentFeedBinding
import com.ishka.memestream.model.Meme
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MemeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MemeAdapter()
        binding.recyclerViewFeed.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFeed.adapter = adapter

        binding.buttonRefresh.setOnClickListener {
            loadMemes()
        }

        loadMemes() // initial load on fragment creation
    }
    private fun loadMemes() {
        lifecycleScope.launch {
            try {
                val memes = RetrofitClient.instance.getMemes()
                adapter.submitList(memes)
                Toast.makeText(requireContext(), "Loaded ${memes.size} memes", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to load memes: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

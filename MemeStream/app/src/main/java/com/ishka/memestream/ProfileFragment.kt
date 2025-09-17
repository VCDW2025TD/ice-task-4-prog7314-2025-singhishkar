//package com.ishka.memestream
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.*
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.ishka.memestream.api.RetrofitClient
//import com.ishka.memestream.databinding.FragmentProfileBinding
//import com.ishka.memestream.model.Meme
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.launch
//
//class ProfileFragment : Fragment(), MemeAdapter.OnItemClickListener {
//
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var adapter: MemeAdapter
//    private val userId = FirebaseAuth.getInstance().currentUser?.uid
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        adapter = MemeAdapter(this)
//        binding.recyclerViewProfile.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerViewProfile.adapter = adapter
//
//   //     loadUserMemes()
//    }
//
////    private fun loadUserMemes() {
////        if (userId == null) return
////        lifecycleScope.launch {
////            try {
////                val memes = RetrofitClient.instance.getUserMemes(userId)
////                adapter.submitList(memes)
////            } catch (e: Exception) {
////                e.printStackTrace()
////            }
////        }
////    }
//
//    override fun onItemClick(meme: Meme) {
//        // Share meme URL and caption
//        val sendIntent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, "${meme.caption} \n${meme.imageUrl}")
//            type = "text/plain"
//        }
//        startActivity(Intent.createChooser(sendIntent, "Share Meme via"))
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//
//    }
//
//}

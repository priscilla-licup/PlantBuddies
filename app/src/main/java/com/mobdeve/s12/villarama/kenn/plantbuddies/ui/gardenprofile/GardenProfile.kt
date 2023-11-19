package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentGardenProfileBinding


class GardenProfile : Fragment() {

private var _binding: FragmentGardenProfileBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val gardenProfileViewModel =
            ViewModelProvider(this).get(GardenProfileViewModel::class.java)

    _binding = FragmentGardenProfileBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    gardenProfileViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
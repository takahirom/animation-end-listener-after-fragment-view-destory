package com.github.takahirom.animation_after_fragment_view_destory

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.navigation.fragment.findNavController
import com.github.takahirom.animation_after_fragment_view_destory.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

  private var _binding: FragmentSecondBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    _binding = FragmentSecondBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.buttonSecond.setOnClickListener {
      findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }
    val viewPropertyAnimator = binding.buttonSecond.animate()
      .scaleX(2F)
      .scaleY(2F)
      .setDuration(10000)
    val viewLifecycleOwner = viewLifecycleOwner
    viewPropertyAnimator
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          super.onAnimationEnd(animation)
          // I/System.out: DESTROYED
          println(viewLifecycleOwner.lifecycle.currentState)
        }
      })
      .start()
    val objectAnimator = ObjectAnimator.ofFloat(binding.buttonThaad, View.ALPHA, 1f)
      .setDuration(10000)
    objectAnimator
      .doOnEnd {
        // I/System.out: DESTROYED
        println(viewLifecycleOwner.lifecycle.currentState)
      }
    objectAnimator
      .start()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
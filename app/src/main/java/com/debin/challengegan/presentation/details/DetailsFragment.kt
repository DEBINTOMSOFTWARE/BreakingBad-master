package com.debin.challengegan.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.debin.challengegan.databinding.FragmentDetailsBinding
import com.debin.challengegan.presentation.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.layout_primary_details.*
import kotlinx.android.synthetic.main.layout_secondary_details.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailsFragment : Fragment() {

    private lateinit var occupationAdapter: OccupationAdapter
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var binding : FragmentDetailsBinding
    private val viewModel : CharactersViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          bindViews()
          initAdapter()
          initViews()
          observeImage()
          observeOccupation()
          observeSeasonApperance()
          onBackClick()
    }

    private fun initAdapter() {
        occupationAdapter = OccupationAdapter(arrayListOf())
        seasonAdapter = SeasonAdapter(arrayListOf())
    }

    private fun initViews() {
        rv_occupation.adapter = occupationAdapter
        rv_season_appearance.adapter = seasonAdapter
        rv_occupation.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rv_season_appearance.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun bindViews() {
        binding.charactersViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeImage() {
        viewModel.characterImage.observe(viewLifecycleOwner, Observer {image ->
            Glide.with(requireContext()).load(image).into(img_character)
        })
    }

    private fun observeOccupation() {
       viewModel.characterOccupation.observe(viewLifecycleOwner , Observer { occupationList ->
           occupationAdapter.updateOccupation(occupationList)
       })
    }

    private fun observeSeasonApperance() {
       viewModel.seasonAppearance.observe(viewLifecycleOwner, Observer { seasonApperanceList ->
          seasonAdapter.updateSeasonsAppearance(seasonApperanceList)
       })
    }

    private fun onBackClick() {
        viewModel.backClick.observe(viewLifecycleOwner, Observer { backPressed ->
            if(backPressed) {
                requireActivity().onBackPressed()
                viewModel.finishBackClick()
            }
        })
    }
}
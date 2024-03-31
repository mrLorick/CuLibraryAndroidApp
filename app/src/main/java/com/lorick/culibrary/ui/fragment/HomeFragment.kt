package com.lorick.culibrary.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseFragment
import com.lorick.culibrary.databinding.FragmentHomeBinding
import com.lorick.culibrary.databinding.ItemHomeBinding
import com.lorick.culibrary.genrics.GenericAdapter
import com.lorick.culibrary.utils.gone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding :: inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        initAcademicRecyclerview()
        initELearningRecyclerview()
        initPersonalRecyclerview()

    }

    private fun initELearningRecyclerview() {
        binding.rvELearning.adapter = eLearningAdapter
        eLearningAdapter.submitList(arrayListOf("","","",""))

    }

    private fun initPersonalRecyclerview() {
        binding.rvPersonal.adapter = personalAdapter
        personalAdapter.submitList(arrayListOf("","","",""))
    }

    private fun initAcademicRecyclerview() {
        binding.rvAcademics.adapter = academicAdapter
        academicAdapter.submitList(arrayListOf("","","","","","","",""))
    }

    private fun setToolbar(){
        binding.apply {
            toolbar.ivBack.setImageResource(R.drawable.ic_menu)
            toolbar.tvToolTitle.gone()
        }
    }

    /**
     * this adapter is Academic List
     * */
    private val academicAdapter = object : GenericAdapter<ItemHomeBinding, String>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_home
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindHolder(
            holder: ItemHomeBinding,
            dataClass: String,
            position: Int
        ) {
            holder.apply {

            }
        }
    }

    /**
     * this adapter is E-Learning List
     * */
    private val eLearningAdapter = object : GenericAdapter<ItemHomeBinding, String>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_home
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindHolder(
            holder: ItemHomeBinding,
            dataClass: String,
            position: Int
        ) {
            holder.apply {

            }
        }
    }

    /**
     * this adapter is Personal List
     * */
    private val personalAdapter = object : GenericAdapter<ItemHomeBinding, String>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_home
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindHolder(
            holder: ItemHomeBinding,
            dataClass: String,
            position: Int
        ) {
            holder.apply {

            }
        }
    }


}
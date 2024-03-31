package com.lorick.culibrary.ui.activity.jobs

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.data.response.jobs.JobsListResponse
import com.lorick.culibrary.databinding.ActivityJobsListScreenBinding
import com.lorick.culibrary.databinding.ItemJobsListBinding
import com.lorick.culibrary.genrics.GenericAdapter
import com.lorick.culibrary.genrics.Resource
import com.lorick.culibrary.genrics.RunInScope
import com.lorick.culibrary.utils.MyProgressBar
import com.lorick.culibrary.utils.finishActivity
import com.lorick.culibrary.utils.overrideColorStatusBar
import com.lorick.culibrary.utils.showErrorSnack
import com.lorick.culibrary.utils.visible
import com.lorick.culibrary.viewModel.PersonalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobsListScreenActivity : BaseActivity<ActivityJobsListScreenBinding>() {
    private var activity = this@JobsListScreenActivity
    private val personalViewModel: PersonalViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.activity_jobs_list_screen

    private fun apiHit() {
        RunInScope.ioThread {
            personalViewModel.hitJobsApi()
        }
    }

    override fun initView() {
        overrideColorStatusBar(R.color.red)
        setToolbar()
        observeDataFromViewModal()
        initJobsListRecyclerView()
        apiHit()
    }

    override fun viewModel() {

    }


    /** set toolbar*/
    private fun setToolbar() {
        binding.toolbar.apply {
            tvEndIcon.visible()
            tvToolTitle.text = getString(R.string.jobs)
            ivBack.setOnClickListener{
                finishActivity()
            }
        }
    }

    /** set recycler view Jobs  List */
    private fun initJobsListRecyclerView() {
        binding.recJobs.adapter = jobsListAdapter
    }

    /**
     * this adapter is Attendance List
     * */
    private val jobsListAdapter = object : GenericAdapter<ItemJobsListBinding, JobsListResponse>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_jobs_list
        }

        override fun onBindHolder(holder: ItemJobsListBinding, dataClass: JobsListResponse, position: Int) {
            holder.tvJobsTitle.text = dataClass.job_title
            holder.tvJobExp.text = getString(R.string.experience).plus(dataClass.job_experience)
            if (dataClass.company_location != null){
                holder.tvCompanyAddress.text = dataClass.company_location
            }else{
                holder.tvCompanyAddress.text = getString(R.string.address_na)
            }
            holder.tvCompanyName.text = dataClass.company_name
            holder.tvHrEmail.text = dataClass.hr_email
            holder.tvHrNumber.text = dataClass.hr_number
            holder.tvJobType.text = getString(R.string.job_type).plus(dataClass.job_type)
        }
    }

    /** Observer Response via View model*/
    private fun observeDataFromViewModal() {
        lifecycleScope.launch {
            personalViewModel.jobsResponseSharedFlow.collectLatest { isResponse ->
                when (isResponse) {
                    is Resource.Success -> {
                        val data = isResponse.data
                        if (data?.isSuccess() == true) {
                            jobsListAdapter.submitList(data.data)
                        } else {
                            showErrorSnack(activity, data?.message ?: "")
                        }
                    }

                    is Resource.Error -> {
                        isResponse.message?.let { msg ->
                            showErrorSnack(activity, msg)
                        }
                    }
                }
            }
        }

        personalViewModel.showLoading.observe(activity) {
            if (it) {
                MyProgressBar.showProgress(activity)
            } else {
                MyProgressBar.hideProgress()
            }
        }
    }
}
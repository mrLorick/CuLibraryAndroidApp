package com.lorick.culibrary.ui.fragment

import android.annotation.SuppressLint
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseFragment
import com.lorick.culibrary.databinding.FragmentHomeBinding
import com.lorick.culibrary.databinding.ItemHomeBinding
import com.lorick.culibrary.genrics.GenericAdapter
import com.lorick.culibrary.ui.activity.attendance.AttendanceActivity
import com.lorick.culibrary.ui.activity.jobs.JobsListScreenActivity
import com.lorick.culibrary.utils.gone
import com.lorick.culibrary.utils.launchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding :: inflate) {

    override fun initView() {
        setToolbar()
        initAcademicRecyclerview()
        initELearningRecyclerview()
        initPersonalRecyclerview()
    }

    private fun initELearningRecyclerview() {
        binding.rvELearning.adapter = eLearningAdapter
        eLearningAdapter.submitList(getLearningList())

    }

    private fun initPersonalRecyclerview() {
        binding.rvPersonal.adapter = personalAdapter
        personalAdapter.submitList(getPersonalList())
    }

    private fun initAcademicRecyclerview() {
        binding.rvAcademics.adapter = academicAdapter
        academicAdapter.submitList(getAcademicList())
    }

    private fun setToolbar(){
        binding.toolbar.apply {
            ivBack.setImageResource(R.drawable.ic_menu)
            tvToolTitle.gone()
        }
    }

    /**
     * this adapter is Academic List
     * */
    private val academicAdapter = object : GenericAdapter<ItemHomeBinding, DashboardModel>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_home
        }

        override fun onBindHolder(holder: ItemHomeBinding, dataClass: DashboardModel, position: Int) {
            holder.apply {
                tvTitle.text = dataClass.title
                icImage.setBackgroundResource(dataClass.icon)
            }

            if(position == 2){
                holder.cvImg.setOnClickListener {
                    requireActivity().launchActivity<AttendanceActivity> {  }
                }
            }
        }
    }

    /**
     * this adapter is E-Learning List
     * */
    private val eLearningAdapter = object : GenericAdapter<ItemHomeBinding, DashboardModel>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_home
        }

        override fun onBindHolder(holder: ItemHomeBinding, dataClass: DashboardModel, position: Int) {
            holder.apply {
                tvTitle.text = dataClass.title
                icImage.setBackgroundResource(dataClass.icon)
            }
        }
    }

    /**
     * this adapter is Personal List
     * */
    private val personalAdapter = object : GenericAdapter<ItemHomeBinding, DashboardModel>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_home
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindHolder(holder: ItemHomeBinding, dataClass: DashboardModel, position: Int) {
            holder.apply {
                tvTitle.text = dataClass.title
                icImage.setBackgroundResource(dataClass.icon)
            }
            if(position == 2){
                holder.cvImg.setOnClickListener {
                    requireActivity().launchActivity<JobsListScreenActivity> {  }
                }
            }
        }
    }


    private fun getAcademicList() :ArrayList<DashboardModel>{
        val list = ArrayList<DashboardModel>()
        list.add(DashboardModel("Class\nTimetable",R.drawable.ic_class_timetable))
        list.add(DashboardModel("Syllabus\nStatus",R.drawable.ic_syllabus))
        list.add(DashboardModel("Attendance",R.drawable.ic_attendance))
        list.add(DashboardModel("Date sheet",R.drawable.ic_datesheet))
        list.add(DashboardModel("Notice\nBoard",R.drawable.ic_notice_board))
        list.add(DashboardModel("DSW",R.drawable.ic_dsw))
        list.add(DashboardModel("View\nMarks",R.drawable.ic_view_marks))
        list.add(DashboardModel("Block\nInformation",R.drawable.ic_block_info))
        return list
    }


    private fun getLearningList() :ArrayList<DashboardModel>{
        val list = ArrayList<DashboardModel>()
        list.add(DashboardModel("Homework",R.drawable.ic_homework))
        list.add(DashboardModel("Online Quiz",R.drawable.ic_online_quiz))
        list.add(DashboardModel("My \nAssignment",R.drawable.ic_my_assignment))
        list.add(DashboardModel("Library",R.drawable.ic_library))
        return list
    }
    private fun getPersonalList() :ArrayList<DashboardModel>{
        val list = ArrayList<DashboardModel>()
        list.add(DashboardModel("My\nDocuments",R.drawable.ic_my_documents))
        list.add(DashboardModel("My Notes",R.drawable.ic_my_notes))
        list.add(DashboardModel("Jobs",R.drawable.ic_jobs))
        list.add(DashboardModel("Favorite\nNotes",R.drawable.ic_favourite_notes))
        return list
    }

    data class DashboardModel(
        var title:String,
        var icon:Int
    )
}
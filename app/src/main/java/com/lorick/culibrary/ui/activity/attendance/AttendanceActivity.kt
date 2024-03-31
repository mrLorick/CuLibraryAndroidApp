package com.lorick.culibrary.ui.activity.attendance

import android.graphics.Typeface
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.data.response.attendance.GetAttendanceListResponse
import com.lorick.culibrary.databinding.ActivityAttendanceBinding
import com.lorick.culibrary.databinding.ItemAttendanceListBinding
import com.lorick.culibrary.genrics.GenericAdapter
import com.lorick.culibrary.genrics.Resource
import com.lorick.culibrary.genrics.RunInScope
import com.lorick.culibrary.utils.MyProgressBar
import com.lorick.culibrary.utils.RoundedBarChartRenderer
import com.lorick.culibrary.utils.finishActivity
import com.lorick.culibrary.utils.overrideColorStatusBar
import com.lorick.culibrary.utils.showErrorSnack
import com.lorick.culibrary.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AttendanceActivity : BaseActivity<ActivityAttendanceBinding>() {
    private var activity = this@AttendanceActivity
    private val authViewModel: AuthViewModel by viewModels()
    private val attendanceList = ArrayList<String>()

    override fun getLayoutRes(): Int = R.layout.activity_attendance

    override fun initView() {
        overrideColorStatusBar(R.color.red)
        setToolbar()
        observeDataFromViewModal()
        initAttendanceListRecyclerView()
        apiCall()
    }

    private fun apiCall() {
        RunInScope.ioThread {
            authViewModel.hitGetAttendanceApi()
        }
    }

    override fun viewModel() {

    }

    /** set toolbar*/
    private fun setToolbar() {
        binding.toolbar.apply {
            tvToolTitle.text = getString(R.string.title_attendance)
            ivBack.setOnClickListener{
                finishActivity()
            }
        }
    }

    /** Set Bar chart with data*/
    private fun setData(list: ArrayList<GetAttendanceListResponse>) {
        val values = ArrayList<BarEntry>()
        attendanceList.clear()
        for (i in list.indices){
            values.add(BarEntry(i.toFloat(), list[i].TotalPercentage.toFloat()))
            attendanceList.add(list[i].Code)
        }

        val set1: BarDataSet
        if (binding.chart1.data != null && binding.chart1.data.dataSetCount > 0) {
            set1 = binding.chart1.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            binding.chart1.data.notifyDataChanged()
            binding.chart1.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1.setDrawIcons(false)
            val startColor1 = ContextCompat.getColor(this, R.color.red)
            set1.color = startColor1
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.5f

            binding.chart1.apply {
                xAxis.setDrawGridLines(false)
                axisLeft.setDrawGridLines(false)
                axisRight.setDrawGridLines(true)
                setGridBackgroundColor(128)
                setBorderColor(255)
                axisRight.isEnabled = true
                val leftAxis: YAxis = axisLeft
                leftAxis.isEnabled = true
                setDrawGridBackground(true)
                axisRight.setDrawLabels(false)
                axisLeft.setDrawLabels(true)
                legend.isEnabled = false
                setPinchZoom(false)
                description = null
                setTouchEnabled(false)
                isDoubleTapToZoomEnabled = false
                xAxis.isEnabled = true
                xAxis.labelCount = attendanceList.size

                // Change font family and other settings
                val typeface = Typeface.create("manrope_medium", Typeface.NORMAL)
                axisRight.typeface = typeface
                axisRight.textSize = 12f
                axisRight.textColor = getColor(R.color.black)
                xAxis.textSize = 10f
                xAxis.typeface = typeface
                xAxis.textColor = getColor(R.color.text_color_hint)
                xAxis.labelRotationAngle = -45f

//                val labels = arrayOf("0","5" ,"10","15", "20","25", "30","35", "40", "45","50","55", "60","65", "70","75",  "80","85",  "90", "95", "100")
//
//                leftAxis.valueFormatter = object : ValueFormatter() {
//                    override fun getFormattedValue(value: Float): String {
//                        return labels[value.toInt()]
//                    }
//                }

                xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return attendanceList[value.toInt()]
                    }
                }

                xAxis.position = XAxis.XAxisPosition.BOTTOM
                invalidate()
            }

            /** Edge Round */
            val barChartRender = RoundedBarChartRenderer(
                binding.chart1,
                binding.chart1.animator,
                binding.chart1.viewPortHandler
            )
            barChartRender.setRadius(20)
            binding.chart1.renderer = barChartRender

            binding.chart1.data = data
        }
    }


    /** set recycler view Attendance  List */
    private fun initAttendanceListRecyclerView() {
        binding.recAttendance.adapter = attendanceListAdapter
    }

    /**
     * this adapter is Attendance List
     * */
    private val attendanceListAdapter = object : GenericAdapter<ItemAttendanceListBinding, GetAttendanceListResponse>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_attendance_list
        }

        override fun onBindHolder(holder: ItemAttendanceListBinding, dataClass: GetAttendanceListResponse, position: Int) {
            holder.tvSubjectName.text = dataClass.Title
            holder.tvSubjectCode.text = dataClass.Code
            holder.tvAttendancePercentage.text = dataClass.TotalPercentage.plus("%")
            holder.tvAttendanceCount.text = dataClass.Total_Attd.plus("/").plus(dataClass.Total_Delv).plus( " days")
            holder.pieAttendance.progress = dataClass.TotalPercentage.toInt()
            if (dataClass.colorcode == "Green"){
                holder.pieAttendance.setIndicatorColor(ContextCompat.getColor(applicationContext,R.color.green))
            }else{
                holder.pieAttendance.setIndicatorColor(ContextCompat.getColor(applicationContext,R.color.red))
            }
        }
    }

    /** Observer Response via View model*/
    private fun observeDataFromViewModal() {
        lifecycleScope.launch {
            authViewModel.getAttendanceResponseSharedFlow.collectLatest { isResponse ->
                when (isResponse) {
                    is Resource.Success -> {
                        val data = isResponse.data
                        if (data?.isSuccess() == true) {
                            attendanceListAdapter.submitList(data.data)
                            setData(data.data)
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

        authViewModel.showLoading.observe(activity) {
            if (it) {
                MyProgressBar.showProgress(activity)
            } else {
                MyProgressBar.hideProgress()
            }
        }
    }
}
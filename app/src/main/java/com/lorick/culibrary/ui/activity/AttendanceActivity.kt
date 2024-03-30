package com.lorick.culibrary.ui.activity

import android.annotation.SuppressLint
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.lorick.culibrary.R
import com.lorick.culibrary.base.BaseActivity
import com.lorick.culibrary.databinding.ActivityAttendanceBinding
import com.lorick.culibrary.databinding.ItemAttendanceListBinding
import com.lorick.culibrary.genrics.GenericAdapter
import com.lorick.culibrary.utils.RoundedBarChartRenderer
import com.lorick.culibrary.utils.finishActivity
import com.lorick.culibrary.utils.overrideColorStatusBar

class AttendanceActivity : BaseActivity<ActivityAttendanceBinding>() {
    private var activity = this@AttendanceActivity

    override fun getLayoutRes(): Int = R.layout.activity_attendance

    override fun initView() {
        overrideColorStatusBar(R.color.red)
        setToolbar()
        setData()
        initAttendanceListRecyclerView()
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
    private fun setData() {
        val values = ArrayList<BarEntry>()
        values.add(BarEntry(0.toFloat(), 6f))
        values.add(BarEntry(1.toFloat(), 1f))
        values.add(BarEntry(2.toFloat(), 2f))
        values.add(BarEntry(4.toFloat(), 10f))
        values.add(BarEntry(5.toFloat(), 3f))
        values.add(BarEntry(6.toFloat(), 3f))

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

                // Change font family and other settings
                val typeface = Typeface.create("manrope_medium", Typeface.NORMAL)
                axisRight.typeface = typeface
                axisRight.textSize = 12f
                axisRight.textColor = getColor(R.color.black)
                xAxis.textSize = 10f
                xAxis.typeface = typeface
                xAxis.textColor = getColor(R.color.text_color_hint)
                xAxis.labelRotationAngle = -45f

                val bottomLabel = arrayOf("22UCT-294", "22UCT-294", "22UCT-294", "22UCT-294", "22UCT-294", "22UCT-294", "22UCT-294")
                val labels = arrayOf("0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100")

                leftAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return labels[value.toInt()]
                    }
                }

                xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return bottomLabel[value.toInt()]
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


    private fun attendanceList() : ArrayList<AttendanceModel>{
        val list = ArrayList<AttendanceModel>()
        list.add(AttendanceModel("Web Technologies","22CAH-254","","You have passed the eligibility criteria and can leave 10 classes",""))
        list.add(AttendanceModel("Mobile Technologies","22CAH-254","","You have passed the eligibility criteria and can leave 10 classes",""))
        list.add(AttendanceModel("Computer Science","22CAH-254","","You have passed the eligibility criteria and can leave 10 classes",""))
        list.add(AttendanceModel("Web Technologies","22CAH-254","","You have passed the eligibility criteria and can leave 10 classes",""))
        list.add(AttendanceModel("Web Technologies","22CAH-254","","You have passed the eligibility criteria and can leave 10 classes",""))
        return list
    }

    /** set recycler view Attendance  List */
    private fun initAttendanceListRecyclerView() {
        binding.recAttendance.adapter = attendanceListAdapter
        attendanceListAdapter.submitList(attendanceList())
    }

    /**
     * this adapter is Attendance List
     * */
    private val attendanceListAdapter = object : GenericAdapter<ItemAttendanceListBinding, AttendanceModel>() {
        override fun getResourceLayoutId(): Int {
            return R.layout.item_attendance_list
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindHolder(holder: ItemAttendanceListBinding, dataClass: AttendanceModel, position: Int) {
            holder.tvSubjectName.text = dataClass.subjectName
            holder.tvSubjectCode.text = dataClass.subjectCode

            if (position == 3){
                holder.pieAttendance.setIndicatorColor(ContextCompat.getColor(applicationContext,R.color.green))
            }
            if (position == 1){
                holder.pieAttendance.setIndicatorColor(ContextCompat.getColor(applicationContext,R.color.green))
            }
        }
    }


    data class AttendanceModel(
        var subjectName :String,
        var subjectCode :String,
        var subjectDec :String,
        var totalAttendance :String,
        var attendAttendance :String,
    )
}
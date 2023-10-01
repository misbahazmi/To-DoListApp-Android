package com.misbah.todo.ui.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.misbah.todo.core.base.BaseFragment
import com.misbah.todo.databinding.ConfirmationDialogBinding
import com.misbah.todo.ui.utils.Utils
import com.nytimes.utils.AppLog
import java.util.Calendar
import javax.inject.Inject

class TimePickerFragment : BaseFragment<DialogViewModel>()  , TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private val calendar: Calendar = Calendar.getInstance()
    internal lateinit var viewModel: DialogViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var utils: Utils
    override fun getViewModel(): DialogViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[DialogViewModel::class.java]
        return viewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return  DatePickerDialog(requireContext(), this, year, month, day)
    }
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        AppLog.debugD("Date: ${java.text.DateFormat.getDateTimeInstance().format(calendar.time.time)}")
//        setFragmentResult(
//            "date_time",
//            bundleOf("date_time" to calendar.time.time)
//        )
//        val navController = findNavController()
//        navController.previousBackStackEntry?.savedStateHandle?.set("date_time", calendar.time.time)
//        navController.popBackStack()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month,day)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }
}
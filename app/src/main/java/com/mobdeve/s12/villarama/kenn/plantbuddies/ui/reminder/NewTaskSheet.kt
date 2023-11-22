package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNewTaskSheetBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId


class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null)
        {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.dueTime() != null)
            {
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        }
        else
        {
            binding.taskTitle.text = "New Task"
        }

        // try to fix class instance error
        val taskItemDao = TaskItemDatabase.getDatabase(requireContext()).taskItemDao()
        val repository = TaskItemRepository(taskItemDao)
        val factory = TaskItemModelFactory(repository)
        taskViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)

//        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Reminder Due")
        dialog.show()

    }

    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }


    private fun saveAction()
    {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc, dueTimeString,null)
            taskViewModel.addTaskItem(newTask)
            scheduleAlarm(newTask)
        }
        else
        {
            taskItem!!.name = name
            taskItem!!.desc = name
            taskItem!!.dueTimeString = dueTimeString

            taskViewModel.updateTaskItem(taskItem!!)
            scheduleAlarm(taskItem!!)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }
    private fun scheduleAlarm(taskItem: TaskItem) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(AlarmReceiver.TASK_NAME_EXTRA, taskItem.name)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            taskItem.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val currentTimeMillis = System.currentTimeMillis()
        val dueTimeMillis = calculateMillisOfDay(taskItem.dueTime()) ?: return

        val triggerTime = currentTimeMillis + (dueTimeMillis - System.currentTimeMillis())

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
        Log.d("AlarmDebug", "currentTimeMillis: $currentTimeMillis")
        Log.d("AlarmDebug", "dueTimeMillis: $dueTimeMillis")
        Log.d("AlarmDebug", "triggerTime: $triggerTime")

        Toast.makeText(requireContext(), "Reminder scheduled for: ${taskItem.name}", Toast.LENGTH_SHORT).show()
    }

    private fun calculateMillisOfDay(localTime: LocalTime?): Long? {


        return localTime?.let {
            val now = LocalDate.now().atTime(it)
            return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }
    }



}
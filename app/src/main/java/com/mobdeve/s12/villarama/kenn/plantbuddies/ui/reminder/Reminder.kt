package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyApplication
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentReminderBinding


class Reminder : Fragment(), TaskItemClickListener {
    private lateinit var binding: FragmentReminderBinding

    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((requireActivity().application as PlantBuddyApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(childFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        taskViewModel.taskItems.observe(viewLifecycleOwner) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = TaskItemAdapter(it, this@Reminder)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(childFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentReminderBinding
//
//class ReminderFragment : Fragment(), TaskItemClickListener {
//    private lateinit var binding: FragmentReminderBinding
//
//    private val taskViewModel: TaskViewModel by viewModels {
//        TaskItemModelFactory((requireActivity().application as PlantBuddyApplication).repository)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentReminderBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.newTaskButton.setOnClickListener {
//            NewTaskSheet(null).show(childFragmentManager, "newTaskTag")
//        }
//        setRecyclerView()
//    }
//
//    private fun setRecyclerView() {
//        taskViewModel.taskItems.observe(viewLifecycleOwner) {
//            binding.todoListRecyclerView.apply {
//                layoutManager = LinearLayoutManager(requireContext())
//                adapter = TaskItemAdapter(it, this@ReminderFragment)
//            }
//        }
//    }
//
//    override fun editTaskItem(taskItem: TaskItem) {
//        NewTaskSheet(taskItem).show(childFragmentManager, "newTaskTag")
//    }
//
//    override fun completeTaskItem(taskItem: TaskItem) {
//        taskViewModel.setCompleted(taskItem)
//    }
//}




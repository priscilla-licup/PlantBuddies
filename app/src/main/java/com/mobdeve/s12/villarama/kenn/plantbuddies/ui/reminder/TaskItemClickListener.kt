package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder

interface TaskItemClickListener {
   //fun onTaskItemClick(taskItem: TaskItem)
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun deleteTaskItem(taskItem: TaskItem)

}
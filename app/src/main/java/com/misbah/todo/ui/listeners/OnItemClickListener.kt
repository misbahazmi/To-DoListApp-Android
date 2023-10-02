package com.misbah.todo.ui.listeners

import com.misbah.todo.core.data.model.Task

interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }
package com.misbah.todo.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.misbah.todo.core.base.BaseFragment
import com.misbah.todo.core.data.remote.APIResult
import com.misbah.todo.databinding.ConfirmationDialogBinding
import com.misbah.todo.databinding.QuitAppDialogBinding
import com.misbah.todo.ui.utils.Utils
import javax.inject.Inject

class ConfirmationDialogFragment :  BaseFragment<DialogViewModel>()  {
    lateinit var binding: ConfirmationDialogBinding
    internal lateinit var viewModel: DialogViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var utils: Utils

    override fun getViewModel(): DialogViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[DialogViewModel::class.java]
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfirmationDialogBinding.inflate(inflater, container, false)
        binding.farg = this@ConfirmationDialogFragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT),48))
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConfirmationDialogFragment()
    }

    fun clickOnCancel() {
        dismiss()
    }

    fun clickOnContinue() {
        viewModel.onConfirmDeleteAllClick()
        dismiss()
    }
}
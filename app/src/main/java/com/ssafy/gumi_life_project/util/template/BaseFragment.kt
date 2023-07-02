package com.ssafy.gumi_life_project.util.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    private var _binding : B? = null
    protected val binding get() = _binding

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onCreateBinding(inflater, container)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    protected val bindingNonNull: B
        get() = requireNotNull(_binding)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}

package com.example.astonfinalproject.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.astonfinalproject.MyApplication
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.di.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    @LayoutRes private val layoutRes: Int,
    private val viewModelClass: Class<VM>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding) { "Binding isn't init" }

    private var _viewModel: VM? = null
    protected val viewModel get() = requireNotNull(_viewModel) { "ViewModel isn't init" }

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDaggerComponent((context.applicationContext as MyApplication).appComponent)
        _viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutRes, container, false)
        _binding = createBinding(view)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun createBinding(view: View): VB
    abstract fun initDaggerComponent(appComponent: ApplicationComponent)
}
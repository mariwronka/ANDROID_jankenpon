package com.mariwronka.jankenpon.ui.common

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<viewBinding : ViewBinding> : AppCompatActivity() {

    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!

    abstract fun bind(): viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bind()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setUpToolbar(
        toolbar: Toolbar,
        title: String? = null,
        actionBackNavigation: (() -> Unit)? = null,
    ) {
        title?.let { toolbar.title = it }
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            actionBackNavigation?.invoke() ?: finish()
        }
    }

    fun launchRepeatOnLifecycle(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state, block)
        }
    }

    fun hideKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

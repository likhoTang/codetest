package com.nick.codetest.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.nick.codetest.R
import com.nick.codetest.widget.AlertSnackBar
import com.nick.codetest.widget.LoadingDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity<B : ViewDataBinding, T : ViewModel>: AppCompatActivity() {
    lateinit var binding: B
    lateinit var viewModel: T

    private lateinit var loadingDialog: LoadingDialog

    var disposeBag = CompositeDisposable()
        private set

    val rootView : View by lazy {
        findViewById<View>(android.R.id.content)
    }

    /**
     * the layout resource of this activity
     */
    @get:LayoutRes
    protected abstract val layout: Int

    /**
     * the method of creating viewmodel
     */
    protected abstract fun createViewModel(): T

    /**
     * viewmodel binding
     */
    protected abstract fun bindingViewModel()

    /**
     * do the rx / listener operation here
     */
    protected open fun observeViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
        viewModel = createViewModel()

        bindingViewModel()
        observeViewModel()

        loadingDialog = LoadingDialog(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        loadingDialog.dismiss()
        binding.lifecycleOwner = null
    }

    fun showLoadingDialog(message: String = "载入中...") {
        loadingDialog.showWithTitle(message)
    }

    fun showMessageSnackBar(message:String = ""){
        AlertSnackBar.show(
            this.rootView,
            message,
        )
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    fun hideSoftKeyBoard() {
        val imm: InputMethodManager? = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.let {
            if (it.isAcceptingText) {
                it.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
            }
        }
    }

}
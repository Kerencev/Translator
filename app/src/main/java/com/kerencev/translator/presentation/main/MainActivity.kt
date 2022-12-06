package com.kerencev.translator.presentation.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.kerencev.translator.R
import com.kerencev.translator.databinding.ActivityMainBinding
import com.kerencev.translator.presentation.base.DialogFragmentListener
import com.kerencev.translator.presentation.base.NavigationActivity
import com.kerencev.translator.presentation.search.SearchDialogFragment
import com.kerencev.translator.presentation.search.SearchFragmentImpl

class MainActivity : AppCompatActivity(), NavigationActivity {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpSplashScreen()
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragmentImpl())
                .commit()
        }
    }

    override fun showSearchDialog(dialogFragmentListener: DialogFragmentListener) {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object :
            SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                dialogFragmentListener.onSearchClick(searchWord)
            }

            override fun onDismiss() {
                dialogFragmentListener.onDismiss()
            }
        })
        searchDialogFragment.show(supportFragmentManager, "")
    }

    override fun navigateTo(currentFragment: Fragment, nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(currentFragment)
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack("")
            .commit()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    private fun setUpSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen().apply {
                setOnExitAnimationListener { splashScreenProvider ->
                    ObjectAnimator.ofFloat(
                        splashScreenProvider.view,
                        View.TRANSLATION_Y,
                        0f,
                        -splashScreenProvider.view.height.toFloat(),
                    ).apply {
                        duration = EXIT_ANIM_DURATION_MIN
                        interpolator = AnticipateInterpolator()
                        doOnEnd {
                            splashScreenProvider.remove()
                        }
                    }.start()
                }
            }
        }
    }

    companion object {
        private const val EXIT_ANIM_DURATION_MIN = 200L
    }
}
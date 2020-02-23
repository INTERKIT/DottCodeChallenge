package com.example.dottchallenge.common.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import kotlin.reflect.KClass

fun Fragment.popBackStack() {
    (requireActivity() as? FragmentActivity)?.popBackStack()
}

fun FragmentActivity.popBackStack() {

    hideKeyboard()

    if (supportFragmentManager.backStackEntryCount < 2) {
        finish()
    } else {
        supportFragmentManager.popBackStackImmediate()
    }
}

fun Fragment.replaceFragment(
    fragment: Fragment,
    @IdRes layoutId: Int = android.R.id.content,
    addToBackStack: Boolean = true,
    requestCode: Int? = null,
    tag: String = fragment::class.java.name
) {
    // Since it may be used by fragments that are nested and contained in child FM
    requireActivity().supportFragmentManager.commit(allowStateLoss = true) {
        replace(layoutId, fragment)
        if (addToBackStack) addToBackStack(tag)
        if (requestCode != null) fragment.setTargetFragment(this@replaceFragment, requestCode)
    }
}

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    @IdRes layoutId: Int = android.R.id.content,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.name
) {
    supportFragmentManager.commit(allowStateLoss = true) {
        replace(layoutId, fragment)
        if (addToBackStack) addToBackStack(tag)
    }
}
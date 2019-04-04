package br.com.hussan.cleanarch.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.app.ActivityOptionsCompat
import br.com.hussan.cleanarch.binding.BIG_FONT
import br.com.hussan.cleanarch.binding.SMALL_FONT
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun TextView.checkTextSize() = when (text.length) {
    in 1..80 -> textSize.pxToSp(context) == BIG_FONT
    else -> textSize.pxToSp(context) == SMALL_FONT
}

inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG) {
    snack(resources.getString(messageRes), length)
}

inline fun View.snack(
    message: String,
    length: Int = Snackbar.LENGTH_LONG
) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

fun Float.spToPx(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, context.resources.displayMetrics)

fun Float.pxToSp(context: Context) =
    this / context.resources.displayMetrics.scaledDensity

fun Disposable.add(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

inline fun <reified T : Activity> Activity.navigate(
    bundle: Bundle? = null,
    options: ActivityOptionsCompat? = null
) {
    val intent = Intent(this, T::class.java)
    intent.apply {
        bundle?.let { putExtras(bundle) }
        startActivity(this, options?.toBundle())
    }
}

inline fun <reified T : Activity> Activity.navigateForResult(
    codeRequest: Int,
    bundle: Bundle? = null
) {
    val intent = Intent(this, T::class.java)
    intent.apply {
        bundle?.let { putExtras(bundle) }
        startActivityForResult(this, codeRequest)
    }
}

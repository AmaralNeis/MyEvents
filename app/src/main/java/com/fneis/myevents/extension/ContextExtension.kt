package com.fneis.myevents.extension

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.color(id: Int) = ContextCompat.getColor(this, id)
@ColorInt
fun Context.colorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.drawable(id: Int) = ContextCompat.getDrawable(this, id)

fun Context.getStringWithHtml(@StringRes stringRes: Int, vararg format: Any): Spanned? {

    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        Html.fromHtml(getString(stringRes, format.first()), Html.FROM_HTML_MODE_LEGACY) else
        Html.fromHtml(getString(stringRes, format.first()))
}

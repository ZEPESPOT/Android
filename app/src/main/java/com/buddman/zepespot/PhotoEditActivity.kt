package com.buddman.zepespot

import com.buddman.zepespot.utils.NetworkDrawable
import com.xiaopo.flying.sticker.DrawableSticker
import kotlinx.android.synthetic.main.activity_photo_edit.*


class PhotoEditActivity: BaseActivity() {
    override fun setDefault() {

        editView.apply {
            addSticker(DrawableSticker(NetworkDrawable.drawableFromUrl(
                    "http://47.74.149.35/api/photo/1zUcXMFLQ5FIGkaMBtc28K/?hashCodes=1XBRWB,7V17S2&width=512"
            )))
        }
    }


    override val viewId: Int = R.layout.activity_photo_edit
    override val toolbarId: Int = 0
}


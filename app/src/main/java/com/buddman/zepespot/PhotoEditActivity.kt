package com.buddman.zepespot

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import com.buddman.zepespot.utils.CredentialManager
import com.buddman.zepespot.utils.NetworkDrawable
import com.xiaopo.flying.sticker.DrawableSticker
import kotlinx.android.synthetic.main.activity_photo_edit.*
import org.jetbrains.anko.toast

class PhotoEditActivity: BaseActivity() {
    override fun setDefault() {
        save.setOnClickListener {
            toast("Photo saved!")
            finish()
        }
        sendTakePhotoIntent()
    }

    private fun sendTakePhotoIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, 1234)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            editView.addSticker(DrawableSticker(BitmapDrawable(resources, imageBitmap)))
            addMe()
        }
    }

    private fun addMe(){
        editView.apply {
            addSticker(DrawableSticker(NetworkDrawable.drawableFromUrl(
                    "http://47.74.149.35/api/photo/1zUcXMFLQ5FIGkaMBtc28K/?hashCodes=1XBRWB,${CredentialManager.instance.zepetoId}&width=512"
            )))
        }
    }
    override val viewId: Int = R.layout.activity_photo_edit
    override val toolbarId: Int = 0
}


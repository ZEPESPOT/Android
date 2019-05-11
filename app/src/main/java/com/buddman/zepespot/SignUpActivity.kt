package com.buddman.zepespot

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_signup_check.view.*
import kotlinx.android.synthetic.main.fragment_signup_1.view.*
import kotlinx.android.synthetic.main.fragment_signup_2.view.*
import org.jetbrains.anko.toast


class SignUpActivity : BaseActivity() {

    private val pagerAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager) }
    override fun setDefault() {
        initView()
    }

    private fun initView() {
        signupBack.setOnClickListener { finish() }
        signupViewPager.adapter = pagerAdapter
        signupConfirm.text = "Next"
        signupConfirm.setOnClickListener {
            if (signupViewPager.currentItem == 0) {
                if ((findFragmentByPosition(0) as Page1Fragment).canGoNext()) {
                    signupViewPager.currentItem = 1
                    signupConfirm.text = "Let's Start!"
                } else toast("Please check input fields.")
            } else {
                val userInfo = (findFragmentByPosition(0) as Page1Fragment).getInputValues()
                val zepetoId = (findFragmentByPosition(1) as Page2Fragment).getInputValue()
                if (zepetoId.isNotBlank()) {
                    val dialog = ZepetoCheckDialog()
                    dialog.arguments = userInfo.apply {
                        putString("zepetoId", zepetoId)
                    }
                    dialog.show(supportFragmentManager, "dialog")
                } else toast("Uh-oh, no blank please!")
            }

        }
    }

    fun findFragmentByPosition(position: Int): Fragment? {
        val fragmentPagerAdapter = pagerAdapter
        return supportFragmentManager.findFragmentByTag(
                "android:switcher:" + signupViewPager.id + ":"
                        + fragmentPagerAdapter.getItemId(position))
    }

    override val viewId: Int = R.layout.activity_sign_up
    override val toolbarId: Int = 0

    class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(p0: Int): Fragment? {
            return when (p0) {
                0 -> Page1Fragment()
                1 -> Page2Fragment()
                else -> null
            }
        }

        override fun getCount(): Int = 2

    }

    class Page1Fragment : Fragment() {

        private lateinit var realView: View

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            realView = inflater.inflate(R.layout.fragment_signup_1, container, false)
            return realView
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) = view.run {
        }

        public fun canGoNext(): Boolean = realView.run {
            return signupEmailInput.textString().contains("@")
                    && signupPwInput.textString() == signupPwReInput.textString()
                    && signupPwInput.textString().isNotBlank() && signupPwReInput.textString().isNotBlank()
                    && signupNickNameInput.textString().isNotEmpty()
        }

        public fun getInputValues(): Bundle = realView.run {
            return Bundle().apply {
                putString("email", signupEmailInput.textString())
                putString("password", signupPwInput.textString())
                putString("nickname", signupNickNameInput.textString())
            }
        }
    }

    class Page2Fragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_signup_2, container, false)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) = view.run {
            signupZepetoInput.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        }

        public fun getInputValue(): String = (view!!.findViewById(R.id.signupZepetoInput) as EditText).textString()
    }

    class ZepetoCheckDialog : DialogFragment() {

        lateinit var realView: View
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            realView = inflater.inflate(R.layout.dialog_signup_check, container, false)
            return realView
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) = view.run {

            val controller = Fresco.newDraweeControllerBuilder()
                    .setUri("http://47.74.149.35/api/photo/20H0qfm5DHKnIO96C8UEMp?hashCodes=${arguments!!.getString("zepetoId")}&width=300")
                    .setTapToRetryEnabled(true)
                    .setOldController(signupZepetoImage.controller)
                    .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                        override fun onFailure(id: String?, throwable: Throwable?) {
                            super.onFailure(id, throwable)
                            dismiss()
                            context.toast("Please input correct ZEPETO ID.")
                        }

                        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                            super.onFinalImageSet(id, imageInfo, animatable)
                            signupZepetoLoading.visibility = View.GONE
                            initView(view)
                        }
                    })
                    .build()
            signupZepetoImage.controller = controller

        }

        private fun initView(view: View) = view.run {
            cancel.setOnClickListener { dismiss() }
            confirm.setOnClickListener { signup(arguments!!) }
        }

        private fun signup(userInfos : Bundle){
            var email = userInfos.getString("email")
            var password = userInfos.getString("password")
            var nickname = userInfos.getString("nickname")
            var zepetoId = userInfos.getString("zepetoId")
        }
    }
}



package com.buddman.zepespot

import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

class SignInActivity : BaseActivity() {

    override fun setDefault() {
        initView()
    }

    private fun initView(){
        signinBack.setOnClickListener { finish() }
        signinFindPW.setOnClickListener { startActivity<FindPasswordActivity>() }
        signinLogin.setOnClickListener { startActivity(intentFor<MainMapsActivity>().clearTask().newTask()) }
    }

    override val viewId: Int = R.layout.activity_sign_in
    override val toolbarId: Int = 0
}

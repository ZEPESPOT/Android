package com.buddman.zepespot

import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : BaseActivity() {

    override fun setDefault() {
        initView()
    }

    private fun initView(){
        findpwBack.setOnClickListener { finish() }
    }

    override val viewId: Int = R.layout.activity_find_password
    override val toolbarId: Int = 0
}

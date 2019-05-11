package com.buddman.zepespot

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.tsengvn.typekit.TypekitContextWrapper

abstract class BaseActivity : AppCompatActivity(){

    lateinit var toolbar: Toolbar
    internal var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewId)
        if (toolbarId != 0) {
            toolbar = findViewById<View>(toolbarId) as Toolbar
            setSupportActionBar(toolbar)
            toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
            toolbar.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
            toolbar.contentInsetStartWithNavigation = 0
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.navigationBarColor = Color.BLACK
        }
        setDefault()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

    protected abstract fun setDefault()

    protected abstract val viewId: Int

    protected abstract val toolbarId: Int

    var toolbarTitle : String
        get() = supportActionBar!!.title.toString()
        set(value) {
            supportActionBar!!.title = value
        }

    fun disableToggle() {
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    fun enableToggle() {
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun isFullFilled(vararg fields: TextView): Boolean {
        fields.forEach { if (it.textString().trim() == "") return false }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
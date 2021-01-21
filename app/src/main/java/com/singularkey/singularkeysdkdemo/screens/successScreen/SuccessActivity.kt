package com.singularkey.singularkeysdkdemo.screens.successScreen

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.singularkey.base.base.BaseActivity
import com.singularkey.base.data.model.AuthenticationRegistrationSuccess
import com.singularkey.singularkeysdkdemo.R
import com.singularkey.singularkeysdkdemo.databinding.ActivityMainBinding


class SuccessActivity : BaseActivity<ActivityMainBinding>() {
    companion object {
        const val FIDO_DATA = "fido_data"
        const val IS_LOGIN = "is_login"
    }

    private lateinit var mBinding: ActivityMainBinding

    override fun getLayoutId() = R.layout.activity_main

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        mBinding = mViewDataBinding as ActivityMainBinding

        if (intent.hasExtra(FIDO_DATA)) {
            /*var successData = Gson().fromJson(intent.extras!!.getString(FIDO_DATA),
                AuthenticationRegistrationSuccess::class.java)*/
            var successData =
                intent.extras!!.getSerializable(FIDO_DATA) as AuthenticationRegistrationSuccess
            val isLogin = intent!!.extras!!.getBoolean(IS_LOGIN)
            if (isLogin)
                mBinding.tvMsg.setText(getString(R.string.login_congratulation_msg))
            else
                mBinding.tvMsg.setText(getString(R.string.congratulation_msg))

            AlertDialog.Builder(this)
                .setTitle("Congratulation")
                .setMessage("Your token is: " + successData.id_token)
                .setCancelable(false)
                .setNeutralButton("OK", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }

                }).show()

        }
    }
}
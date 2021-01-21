package com.singularkey.singularkeysdkdemo.screens

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.gox.base.extensions.provideViewModel
import com.singularkey.base.base.BaseActivity
import com.singularkey.base.base.SingularKey
import com.singularkey.base.data.model.BaseError
import com.singularkey.base.utils.SingularKeyConstantUtils
import com.singularkey.singularkeysdkdemo.R
import com.singularkey.singularkeysdkdemo.databinding.SplashLayoutBinding
import com.singularkey.singularkeysdkdemo.DemoApplication
import com.singularkey.singularkeysdkdemo.network.Config
import com.singularkey.singularkeysdkdemo.network.model.PasswordLessRegisterRequest
import com.singularkey.singularkeysdkdemo.network.model.RegisterResponse
import com.singularkey.singularkeysdkdemo.screens.successScreen.SuccessActivity

class SplashActivity : BaseActivity<SplashLayoutBinding>(), SplashNavigator {

    private lateinit var mLayoutBinding: SplashLayoutBinding
    private lateinit var mViewModel: SplashViewModel
    private var isLogin: Boolean? = false

    override fun getLayoutId() = R.layout.splash_layout

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        mLayoutBinding = mViewDataBinding as SplashLayoutBinding
        mViewModel = provideViewModel { SplashViewModel() }
        mViewModel.navigator = this

        mLayoutBinding.btnRegister.setOnClickListener {
            if (mLayoutBinding.edtUsername.text.toString().isNotEmpty()) {
                isLogin = false
                loadingObservable.value = true
                var request =
                    PasswordLessRegisterRequest(mLayoutBinding.edtUsername.text.toString().trim())
                mViewModel.doPasswordLessRegister(request)
            }
        }

        mLayoutBinding.btnLogin.setOnClickListener {
            if (mLayoutBinding.edtUsername.text.isNotEmpty()) {
                isLogin = true
                loadingObservable.value = true
                var request =
                    PasswordLessRegisterRequest(mLayoutBinding.edtUsername.text.toString().trim())
                mViewModel.doPasswordLessLogin(request)
            }
        }
    }

    override fun onPasswordLessRegisterSuccess(response: RegisterResponse) {
        loadingObservable.value = false
        DemoApplication.instance().saveToken(response.singularKeySdkToken!!.access_token!!)
        showSuccess("Success")
        DemoApplication.instance().setUserName(mLayoutBinding.edtUsername.text.toString())
        /*var intent = Intent(this, MFAScreen::class.java)
        intent.putExtra(MFAScreen.USER_NAME, mLayoutBinding.edtUsername.text.toString())
        intent.putExtra(MFAScreen.TOKEN, DemoApplication.instance().authToken)
        intent.putExtra(MFAScreen.COMPANY_ID, Config.companyId)
        intent.putExtra(MFAScreen.FLOW_ID, response.flowId)
        intent.putExtra(MFAScreen.POLICY_ID, response.policyId)
        startActivityForResult(intent, REQUEST_CODE_CLIENT)*/

        SingularKey.from(this)
            .setCompanyId(Config.companyId)
            .setAccessToken(DemoApplication.instance().authToken!!)
            .setFlowId(response.flowId)
            .setPolicyId(response.policyId)
            .setUserName(mLayoutBinding.edtUsername.text.toString())
            .setActionBarColor(Color.parseColor("#FF03DAC5"))
            .setActionBarTitleColor(Color.parseColor("#FFFFFF"))
            .setStatusBarColor(Color.parseColor("#FF018786"))
            .startAuthenticationFlow()
    }

    override fun onPasswordLessRegisterError(error: BaseError) {
        loadingObservable.value = false
        showError(error.message!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == SingularKey.SINGULAR_KEY_REQUEST_CODE) {
            var intent = Intent(this, SuccessActivity::class.java)
            intent.putExtra(SuccessActivity.IS_LOGIN, isLogin);
            intent.putExtra(
                SuccessActivity.FIDO_DATA,
                data!!.getSerializableExtra(SingularKeyConstantUtils.FIDO_RESPONSE_DATA)
            )
            startActivity(intent)
            finish()
        } else if (resultCode == RESULT_CANCELED && requestCode == SingularKey.SINGULAR_KEY_REQUEST_CODE) {
            //TODO DO changes for 401 error
            if (data != null) {
                var code = data!!.getIntExtra(SingularKeyConstantUtils.FIDO_ERROR_CODE, 0)
                var message = data.getStringExtra(SingularKeyConstantUtils.FIDO_ERROR_MESSAGE)
                showErrorDialog(code, message!!)
            } else {
                showErrorDialog(10, "something went wrong")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showErrorDialog(code: Int, message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error: " + code)
            .setMessage(message)
            .setCancelable(false)
            .setNeutralButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0!!.dismiss()
                }

            }).show()
    }

}
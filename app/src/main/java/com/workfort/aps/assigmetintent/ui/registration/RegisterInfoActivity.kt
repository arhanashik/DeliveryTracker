package com.workfort.aps.assigmetintent.ui.registration

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.workfort.aps.assigmetintent.R
import com.workfort.aps.assigmetintent.data.constant.Const
import com.workfort.aps.assigmetintent.data.user.UserEntity
import com.workfort.aps.assigmetintent.databinding.ActivityRegisterInfoBinding
import com.workfort.aps.assigmetintent.ui.profile.ProfileActivity
import com.workfort.aps.util.helper.ImageLoader
import com.workfort.aps.util.helper.ImagePicker
import com.workfort.aps.util.helper.PermissionUtil

class RegisterInfoActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterInfoBinding

    private var userImgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_info)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PermissionUtil.REQUEST_CODE_STORAGE) {
            for (i in permissions.indices) {
                val permission = permissions[i]

                when (permission) {
                    Manifest.permission.CAMERA -> {
                    }

                    Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    }

                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            showToast("Permission is denied")
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            val pickedImageInfo = ImagePicker.getPickedImageInfo(this, resultCode, data)

            if(pickedImageInfo != null && pickedImageInfo.imageUri != null) {
                when (requestCode) {
                    Const.RequestCode.PIC_USER_PHOTO -> {
                        userImgUri = pickedImageInfo.imageUri

                        mBinding.btnChoosePhoto.visibility = View.INVISIBLE
                        mBinding.imgProfile.visibility = View.VISIBLE

                        ImageLoader.load(this, mBinding.imgProfile, userImgUri.toString())
                    }
                }
            }

        } else {
            if (resultCode != Activity.RESULT_CANCELED) {
                showToast("Could not pick image")
            }
        }
    }

    fun onClickPickUserImage(view: View) {
        if (PermissionUtil.on().request(this,
                PermissionUtil.REQUEST_CODE_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            ImagePicker.pickImage(this, Const.RequestCode.PIC_USER_PHOTO)
        }
    }

    fun onClickRegistration(view: View) {
        val name: String?
        val email: String?
        val age: Int
        val phone: String?

        if(userImgUri == null) {
            showToast(getString(R.string.image_required_exception))
            return
        }

        name = checkAndMakeValue(mBinding.tilName, mBinding.etName,
            getString(R.string.name_required_exception)) ?: return

        email = checkAndMakeValue(mBinding.tilEmail, mBinding.etEmail,
            getString(R.string.email_required_exception)) ?: return

        val ageStr = checkAndMakeValue(mBinding.tilAge, mBinding.etAge,
            getString(R.string.age_required_exception)) ?: return
        age = ageStr.toInt()

        phone = checkAndMakeValue(mBinding.tilPhone, mBinding.etPhone,
            getString(R.string.phone_required_exception)) ?: return

        val user = UserEntity(1, name, email, age, phone, userImgUri.toString())
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(Const.Key.USER, user)
        startActivity(intent)
    }

    private fun checkAndMakeValue(layout: TextInputLayout, editText: EditText, exception: String)
            : String? {

        if(editText.text == null){
            setError(true, exception, layout)
            return null
        }

        val data = editText.text.toString()

        if(TextUtils.isEmpty(data)){
            setError(true, exception, layout)
            return null
        }

        setError(false, null, layout)
        return data
    }

    private fun setError(error: Boolean, message: String?, view: TextInputLayout) {
        view.error = message
        view.isErrorEnabled = error
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

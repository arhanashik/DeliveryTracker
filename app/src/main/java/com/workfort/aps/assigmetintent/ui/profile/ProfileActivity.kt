package com.workfort.aps.assigmetintent.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.workfort.aps.assigmetintent.R
import com.workfort.aps.assigmetintent.data.constant.Const
import com.workfort.aps.assigmetintent.data.user.UserEntity
import com.workfort.aps.assigmetintent.databinding.ActivityProfileBinding
import com.workfort.aps.util.helper.ImageLoader
import com.workfort.aps.util.helper.LinkUtil

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding

    private var user: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(Const.Key.USER)
        if(user == null) {
            showToast(getString(R.string.user_required_exception))
            finish()
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        ImageLoader.load(this, mBinding.imgProfile, user!!.imagePath)
        mBinding.tvName.text = user!!.name
        mBinding.tvEmail.text = user!!.email
        mBinding.tvAge.text = user!!.age.toString()
        mBinding.tvPhone.text = user!!.phone
    }

    fun onClickEmail(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Send email to: ${user!!.email}")
            .setPositiveButton("Send email") {
                    _, _ -> LinkUtil(this).sendEmail(user!!.email!!)
            }
            .create()
            .show()
    }

    fun onClickPhone(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Call to: ${user!!.phone}")
            .setPositiveButton("Call") {
                    _, _ -> LinkUtil(this).callTo(user!!.phone!!)
            }
            .create()
            .show()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

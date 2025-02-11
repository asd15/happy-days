package com.example.happydates.activites

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.happydates.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeface: Typeface = Typeface.createFromAsset(assets, "LittleLordFontleroyNF.ttf")
        tv_app_name_intro.typeface = typeface

        btn_sign_up_intro.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        btn_sign_in_intro.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

    }
}

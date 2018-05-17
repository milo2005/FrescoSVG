package mobile.svgtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fresco.*
import mobile.svgtest.util.SvgUtil

class FrescoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fresco)

        SvgUtil.addSVG(null, imga)
        SvgUtil.addSVG(null, imgb)
        SvgUtil.addSVG(null, imgc)


    }

    override fun onResume() {
        super.onResume()

    }
}

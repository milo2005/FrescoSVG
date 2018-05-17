package mobile.svgtest

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import kotlinx.android.synthetic.main.activity_svg.*


class SVGActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)

        val svg = SVG.getFromAsset(assets, "egg.svg")
        svg.documentHeight = 200f
        svg.documentWidth = 100f
        if (svg.documentWidth != -1f) {
            val newBM = Bitmap.createBitmap(Math.ceil(svg.documentWidth.toDouble()).toInt(),
                    Math.ceil(svg.documentHeight.toDouble()).toInt(),
                    Bitmap.Config.ARGB_8888)
            val bmcanvas = Canvas(newBM)

            // Clear background to white
            // bmcanvas.drawRGB(255, 255, 255)

            // Render our document onto our canvas
            svg.renderToCanvas(bmcanvas)


            img1.setImageBitmap(newBM)
            img2.setImageBitmap(newBM)
            img3.setImageBitmap(newBM)

        }
    }
}


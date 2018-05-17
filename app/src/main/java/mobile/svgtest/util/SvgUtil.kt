package mobile.svgtest.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.ImageView
import com.caverock.androidsvg.SVG
import java.io.InputStream

object SvgUtil {

    fun addSVG(inputStream: InputStream?, img: ImageView, with: Float = 200f, height: Float = 200f) {
        val svg = SVG.getFromAsset(img.context.assets, "egg.svg")
        svg.documentHeight = with
        svg.documentWidth = height

        val newBM = Bitmap.createBitmap(Math.ceil(svg.documentWidth.toDouble()).toInt(),
                Math.ceil(svg.documentHeight.toDouble()).toInt(),
                Bitmap.Config.ARGB_8888)

        val bmcanvas = Canvas(newBM)

        svg.renderToCanvas(bmcanvas)
        img.setImageBitmap(newBM)
    }

}
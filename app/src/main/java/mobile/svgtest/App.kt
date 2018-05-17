package mobile.svgtest

import android.app.Application
import com.facebook.common.internal.Supplier
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.DraweeConfig
import mobile.svgtest.util.SVGDecoder
import com.facebook.common.internal.Suppliers.BOOLEAN_TRUE
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.decoder.ImageDecoderConfig
import mobile.svgtest.util.SVGDecoder.SVG_FORMAT


class App : Application() {


    override fun onCreate() {
        super.onCreate()

        val config = ImageDecoderConfig.newBuilder()


        config.addDecodingCapability(
                SVG_FORMAT,
                SVGDecoder.SvgFormatChecker(),
                SVGDecoder.SvgDecoder())


        val imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setImageDecoderConfig(config.build())
                .build()




        val draweeConfigBuilder = DraweeConfig.newBuilder()

        draweeConfigBuilder.addCustomDrawableFactory(SVGDecoder.SvgDrawableFactory())

        Fresco.initialize(this, imagePipelineConfig, draweeConfigBuilder.build())
    }

}
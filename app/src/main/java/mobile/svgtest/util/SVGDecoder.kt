package mobile.svgtest.util

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.facebook.imageformat.ImageFormat
import com.facebook.imageformat.ImageFormatCheckerUtils
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.decoder.ImageDecoder
import com.facebook.imagepipeline.drawable.DrawableFactory
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.image.EncodedImage
import com.facebook.imagepipeline.image.QualityInfo


object SVGDecoder{
    val SVG_FORMAT = ImageFormat("SVG_FORMAT", "svg")

    // We do not include the closing ">" since there can be additional information
    private val HEADER_TAG = "<svg"
    private val POSSIBLE_HEADER_TAGS = arrayOf(ImageFormatCheckerUtils.asciiBytes("<?xml"))


    class SvgFormatChecker : ImageFormat.FormatChecker {

        override fun getHeaderSize(): Int {
            return HEADER.size
        }


        override fun determineFormat(headerBytes: ByteArray, headerSize: Int): ImageFormat? {
            if (headerSize < getHeaderSize()) {
                return null
            }
            if (ImageFormatCheckerUtils.startsWithPattern(headerBytes, HEADER)) {
                return SVG_FORMAT
            }
            for (possibleHeaderTag in POSSIBLE_HEADER_TAGS) {
                if (ImageFormatCheckerUtils.startsWithPattern(headerBytes, possibleHeaderTag) && ImageFormatCheckerUtils
                                .indexOfPattern(headerBytes, headerBytes.size, HEADER, HEADER.size) > -1) {
                    return SVG_FORMAT
                }
            }
            return null
        }

        companion object {

            val HEADER = ImageFormatCheckerUtils.asciiBytes(HEADER_TAG)
        }
    }


    class CloseableSvgImage(val svg: SVG) : CloseableImage() {

        private var mClosed = false

        override fun getSizeInBytes(): Int {
            return 0
        }

        override fun close() {
            mClosed = true
        }

        override fun isClosed(): Boolean {
            return mClosed
        }

        override fun getWidth(): Int {
            return 0
        }

        override fun getHeight(): Int {
            return 0
        }
    }

    class SvgDecoder : ImageDecoder {

        override fun decode(
                encodedImage: EncodedImage,
                length: Int,
                qualityInfo: QualityInfo,
                options: ImageDecodeOptions): CloseableImage? {
            try {
                val svg = SVG.getFromInputStream(encodedImage.inputStream)
                return CloseableSvgImage(svg)
            } catch (e: SVGParseException) {
                e.printStackTrace()
            }

            return null
        }
    }

    class SvgDrawableFactory : DrawableFactory {

        override fun supportsImageType(image: CloseableImage): Boolean {
            return image is CloseableSvgImage
        }

        override fun createDrawable(image: CloseableImage): Drawable? {
            return SvgPictureDrawable((image as CloseableSvgImage).svg)
        }
    }

    class SvgPictureDrawable(private val mSvg: SVG) : PictureDrawable(null) {

        override fun onBoundsChange(bounds: Rect) {
            super.onBoundsChange(bounds)
            picture = mSvg.renderToPicture(bounds.width(), bounds.height())
        }
    }


}
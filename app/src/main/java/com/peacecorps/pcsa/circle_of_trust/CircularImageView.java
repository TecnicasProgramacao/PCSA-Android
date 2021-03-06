/*
 * Copyright 2014 - 2015 Henning Dodenhof
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peacecorps.pcsa.circle_of_trust;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.peacecorps.pcsa.R;

/*
 * ImageView with circle border
 *
 * @author chamika
 * @since 2016-02-24
 */
public class CircularImageView extends ImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 1;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();

    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    private float mBorderRadius;

    private boolean mReady;
    private boolean mSetupPending;

    public CircularImageView(final Context context) {
        super(context);

        init();
    }

    public CircularImageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyle, 0);

        mBorderWidth = a.getDimensionPixelSize(
                R.styleable.CircularImageView_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(
                R.styleable.CircularImageView_border_color, DEFAULT_BORDER_COLOR);

        a.recycle();

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public final ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public final void setScaleType(final ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    /**
     * Draws circle in the center of border rectangle
     * @param canvas
     */
    @Override
    protected final void onDraw(final Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }

        /* HALF_BORDER_WIDTH and HALF_BORDER_HEIGHT indicate the center of the rectangle (borders)
         * The will be used to draw a circle in the center of the rectangle.
         */
        final int HALF_BORDER_WIDTH = getWidth() / 2;
        final int HALF_BORDER_HEIGHT = getHeight() / 2;

        canvas.drawCircle(HALF_BORDER_WIDTH, HALF_BORDER_HEIGHT, mDrawableRadius, mBitmapPaint);

        if (mBorderWidth != 0) {
            canvas.drawCircle(HALF_BORDER_WIDTH, HALF_BORDER_HEIGHT, mBorderRadius, mBorderPaint);
        }
        else {
            // Do nothing.
        }
    }

    @Override
    protected final void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public final int getBorderColor() {
        return mBorderColor;
    }

    public final void setBorderColor(final int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }

        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    public final int getBorderWidth() {
        return mBorderWidth;
    }

    public final void setBorderWidth(final int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }

        mBorderWidth = borderWidth;
        setup();
    }

    @Override
    public final void setImageBitmap(final Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public final void setImageDrawable(final Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public final void setImageResource(final int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public final void setImageURI(final Uri uri) {
        super.setImageURI(uri);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    /**
     * Get the Bitmap from the given drawable
     * @param drawable drawable to be used
     * @return bitmap object created using the drawable
     */
    private Bitmap getBitmapFromDrawable(final Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    /**
     * Refresh view
     */
    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mBorderRect.set(0, 0, getWidth(), getHeight());
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
                                 (mBorderRect.width() - mBorderWidth) / 2);

        mDrawableRect.set(mBorderWidth,
                          mBorderWidth, mBorderRect.width() - mBorderWidth,
                          mBorderRect.height() - mBorderWidth);

        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);

        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        final float HALF = 0.5F;

        float scale = 0.0F;
        float dx = 0.0F;
        float dy = 0.0F;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * HALF;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * HALF;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + HALF) + mBorderWidth, (int) (dy + HALF) + mBorderWidth);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

}

package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.openGL.surfaceView;
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.openGL.render.OpenGLRenderer;

public class OpenGLSurfaceView extends GLSurfaceView {

    private final OpenGLRenderer mRenderer;
    private ScaleGestureDetector scaleGestureDetector;

    public OpenGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);

        mRenderer = new OpenGLRenderer();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 1500;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        scaleGestureDetector.onTouchEvent(e);

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            mRenderer.scaleFactor *= detector.getScaleFactor();
            mRenderer.scaleFactor = Math.max(0.1f, Math.min(mRenderer.scaleFactor, 5.0f));

            return true;
        }
    }
}

package cn.berfy.sdk.demohttp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View

import com.axingxing.demohttp.R

import java.util.ArrayList
import java.util.ConcurrentModificationException
import java.util.Random


class WaterWaveView : View {

    private var mContext: Context? = null
    private val mPaint1 = Paint()//远处
    private val mPaint2 = Paint()//近处
    var isRunning: Boolean = false
        private set
    private val FPS = 30// fps
    private var mAngle1 = 0f//远处远处波浪角度1
    private var mAngle2 = 180f//近处波浪角度2
    private val mWaterSin = 30f// 睡眠波浪振幅 数值越大波浪越大
    private val mLevel = 0.4f// 水面高度
    private val mWaterSpeed = 0.0002f// 水滴速度
    private val mSpeed1 = 1.5f// 远处水浪速度 越大越快
    private val mSpeed2 = 3f// 近处水浪速度 越大越快
    private var mRefreshThread1: Thread? = null
    private var mRefreshThread2: Thread? = null
    private var mRefreshThread3: Thread? = null
    private var mAddThread: Thread? = null
    private var mDrawFilter: PaintFlagsDrawFilter? = null
    private val mMaxWaterCount = 20
    private val mRandom = Random()
    private val mWaters = ArrayList<Water>()
    private val mAddWaters = ArrayList<Water>()
    private val mIsScrollToDown = true
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> invalidate()
                1 -> {
                }
            }//                    mIsScrollToDown = !mIsScrollToDown;
            //                    mHandler.sendEmptyMessageDelayed(1, 10000);
        }
    }

    constructor(paramContext: Context) : super(paramContext) {
        mContext = paramContext
        init()
    }

    constructor(paramContext: Context, paramAttributeSet: AttributeSet) : super(paramContext, paramAttributeSet) {
        mContext = paramContext
        init()
    }

    private fun init() {
        mDrawFilter = PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG,
                Paint.DITHER_FLAG)
        mPaint1.alpha = 150
        mPaint1.isAntiAlias = true
        mPaint1.color = Color.rgb(89, 176, 200)
        mPaint2.alpha = 150
        mPaint2.isAntiAlias = true
        mPaint2.color = Color.rgb(89, 196, 231)
    }

    fun startWave() {
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        mWaters?.clear()
        if (!isRunning) {
            isRunning = true
            mRefreshThread1 = Thread(Runnable {
                // TODO Auto-generated method stub
                while (isRunning) {
                    mAngle1 += mSpeed1
                    mAngle2 += mSpeed2
                    if (mAngle1 == 360f) {
                        mAngle1 = 0f
                    }
                    if (mAngle2 == 360f) {
                        mAngle2 = 0f
                    }
                    try {
                        if (mWidth > 0 && mHeight > 0 && mWaters.size > 0) {
                            for (i in 0 until mWaters.size / 3) {
                                val water = mWaters[i]
                                val trueHeight = mHeight * (1 - mLevel)
                                if (mIsScrollToDown) {
                                    water.size += 0.0005f
                                } else {
                                    water.size += 0.0005f
                                }
                                if (mIsScrollToDown && water.y > trueHeight + 50) {
                                    water.x = mRandom.nextInt(mWidth).toFloat()
                                    water.y = mRandom.nextInt(mHeight / 4).toFloat()
                                    water.size = 0.01f
                                    water.rawTime = System.currentTimeMillis()
                                } else if (!mIsScrollToDown && water.y < -50) {
                                    water.x = mRandom.nextInt(mWidth).toFloat()
                                    water.y = trueHeight + 50f + mRandom.nextInt((mHeight * mLevel).toInt()).toFloat()
                                    water.size = 0.01f
                                    water.rawTime = System.currentTimeMillis()
                                }
                                val moveTime = System.currentTimeMillis() * 0.1 - water.rawTime * 0.1
                                if (mIsScrollToDown) {
                                    water.y += ((moveTime + moveTime * 3) / 200).toFloat()
                                } else {
                                    water.y -= ((moveTime + moveTime * 3) / 200).toFloat()
                                }
                                //                                    HLogF.d("水滴刷新", "===高度" + mHeight + "   " + water.x + "," + water.y + "  " + water.size);
                            }
                        }
                        mHandler.sendEmptyMessage(0)
                        try {
                            Thread.sleep((1000 / FPS).toLong())
                        } catch (e1: InterruptedException) {
                            e1.printStackTrace()
                        }

                    } catch (e: ConcurrentModificationException) {
                        e.printStackTrace()
                        mHandler.sendEmptyMessage(0)
                        try {
                            Thread.sleep(1L)
                        } catch (e1: InterruptedException) {
                            e1.printStackTrace()
                        }

                    }

                }
            })
            mRefreshThread1!!.start()

            mRefreshThread2 = Thread(Runnable {
                // TODO Auto-generated method stub
                while (isRunning) {
                    try {
                        if (mWidth > 0 && mHeight > 0 && mWaters.size > 0) {
                            for (i in mWaters.size / 3 until mWaters.size / 3 * 2) {
                                val water = mWaters[i]
                                val trueHeight = mHeight * (1 - mLevel)
                                if (mIsScrollToDown) {
                                    water.size += 0.0004f
                                } else {
                                    water.size += 0.0004f
                                }
                                if (mIsScrollToDown && water.y > trueHeight + 50) {
                                    water.x = mRandom.nextInt(mWidth).toFloat()
                                    water.y = mRandom.nextInt(mHeight / 4).toFloat()
                                    water.size = 0.01f
                                    water.rawTime = System.currentTimeMillis()
                                } else if (!mIsScrollToDown && water.y < -50) {
                                    water.x = mRandom.nextInt(mWidth).toFloat()
                                    water.y = trueHeight + 50f + mRandom.nextInt((mHeight * mLevel).toInt()).toFloat()
                                    water.size = 0.01f
                                    water.rawTime = System.currentTimeMillis()
                                }
                                val moveTime = System.currentTimeMillis() * 0.1 - water.rawTime * 0.1
                                if (mIsScrollToDown) {
                                    water.y += ((moveTime + moveTime * 3) / 600).toFloat()
                                } else {
                                    water.y -= ((moveTime + moveTime * 3) / 600).toFloat()
                                }
                                //                                    HLogF.d("水滴刷新", "===高度" + mHeight + "   " + water.x + "," + water.y + "  " + water.size);
                            }
                        }
                        mHandler.sendEmptyMessage(0)
                        try {
                            Thread.sleep((1000 / FPS).toLong())
                        } catch (e1: InterruptedException) {
                            e1.printStackTrace()
                        }

                    } catch (e: ConcurrentModificationException) {
                        e.printStackTrace()
                        mHandler.sendEmptyMessage(0)
                        try {
                            Thread.sleep(1)
                        } catch (e1: InterruptedException) {
                            e1.printStackTrace()
                        }

                    }

                }
            })
            mRefreshThread2!!.start()

            mRefreshThread3 = Thread(Runnable {
                // TODO Auto-generated method stub
                while (isRunning) {
                    try {
                        if (mWidth > 0 && mHeight > 0 && mWaters.size > 0) {
                            for (i in mWaters.size / 3 * 2 until mWaters.size) {
                                val water = mWaters[i]
                                val trueHeight = mHeight * (1 - mLevel)
                                if (mIsScrollToDown) {
                                    water.size += 0.0002f
                                } else {
                                    water.size += 0.0002f
                                }
                                if (mIsScrollToDown && water.y > trueHeight + 50) {
                                    water.x = mRandom.nextInt(mWidth).toFloat()
                                    water.y = mRandom.nextInt(mHeight / 4).toFloat()
                                    water.size = 0.01f
                                    water.rawTime = System.currentTimeMillis()
                                } else if (!mIsScrollToDown && water.y < -50) {
                                    water.x = mRandom.nextInt(mWidth).toFloat()
                                    water.y = trueHeight + 50f + mRandom.nextInt((mHeight * mLevel).toInt()).toFloat()
                                    water.size = 0.01f
                                    water.rawTime = System.currentTimeMillis()
                                }
                                val moveTime = System.currentTimeMillis() * 0.1 - water.rawTime * 0.1
                                if (mIsScrollToDown) {
                                    water.y += ((moveTime + moveTime * 3) / 800).toFloat()
                                } else {
                                    water.y -= ((moveTime + moveTime * 3) / 800).toFloat()
                                }
                                //                                    HLogF.d("水滴刷新", "===高度" + mHeight + "   " + water.x + "," + water.y + "  " + water.size);
                            }
                        }
                        mHandler.sendEmptyMessage(0)
                        try {
                            Thread.sleep((1000 / FPS).toLong())
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    } catch (e: ConcurrentModificationException) {
                        e.printStackTrace()
                        mHandler.sendEmptyMessage(0)
                        try {
                            Thread.sleep(1)
                        } catch (e1: InterruptedException) {
                            e1.printStackTrace()
                        }

                    }

                }
            })
            mRefreshThread3!!.start()
            mAddThread = Thread(Runnable {
                // TODO Auto-generated method stub
                while (isRunning && mWaters.size < mMaxWaterCount) {
                    if (mWidth > 0 && mHeight > 0) {
                        if (mWaters.size < mMaxWaterCount) {
                            val water = Water()
                            water.water = BitmapFactory.decodeResource(mContext!!.resources,
                                    R.mipmap.water)
                            water.x = mRandom.nextInt(mWidth).toFloat()
                            water.y = mRandom.nextInt(mHeight / 4).toFloat()
                            water.rawTime = System.currentTimeMillis() - (3000 * (water.y * 0.1 / (mHeight * 0.1))).toLong()
                            mAddWaters.add(water)
                            //                                HLogF.d("水滴增加", " " + water.x + "," + water.y + "  " + water.size);
                            //                                    mHandler.sendEmptyMessage(0);
                            //                                    postInvalidate();
                        }
                    }
                    try {
                        Thread.sleep(200)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            })
            mAddThread!!.start()
        }
        //        mHandler.sendEmptyMessageDelayed(1, 10000);
    }

    public override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)
        canvas.drawFilter = mDrawFilter
        canvas.drawColor(Color.WHITE)
        mWidth = width
        mHeight = height
        try {
            if (null != mWaters) {
                if (mAddWaters.size > 0) {
                    mWaters.addAll(mAddWaters)
                    mAddWaters.clear()
                }
                for (water in mWaters) {
                    val matrix = Matrix()
                    matrix.postScale(water.size, water.size)
                    matrix.postTranslate(water.x, water.y)
                    canvas.drawBitmap(water.water!!, matrix, mPaint1)
                    //                    HLogF.d("水滴", "==="
                    //                            + water.x + "," + water.y + "  " + water.size);
                }
            }
        } catch (e: ConcurrentModificationException) {
            e.printStackTrace()
        }

        var lineX1 = 0.0
        var lineY1 = 0.0
        var lineX2 = 0.0
        var lineY2 = 0.0
        for (i in 0 until mWidth) {
            lineX1 = i.toDouble()
            lineX2 = i.toDouble()
            if (isRunning) {
                lineY1 = mWaterSin * Math.sin((i / 4 + mAngle1) * Math.PI / 180) + 50//远处
                lineY2 = mWaterSin * Math.sin((i / 4 + mAngle2) * Math.PI / 180) + 50//近处
            } else {
                lineY1 = 50.0
                lineY2 = 50.0
            }
            canvas.drawLine(lineX1.toInt().toFloat(),
                    (lineY1 + mHeight * (1 - mLevel)).toInt().toFloat(), lineX1.toInt().toFloat(),
                    mHeight.toFloat(), mPaint1)
            canvas.drawLine(lineX2.toInt().toFloat(),
                    (lineY2 + mHeight * (1 - mLevel)).toInt().toFloat(), lineX2.toInt().toFloat(),
                    mHeight.toFloat(), mPaint2)
        }
    }

    fun stop() {
        setLayerType(View.LAYER_TYPE_NONE, null)
        for (water in mWaters) {
            water.water!!.recycle()
        }
        mWaters.clear()
        for (water in mAddWaters) {
            water.water!!.recycle()
        }
        mAddWaters.clear()
        isRunning = false
        if (null != mRefreshThread1) {
            mRefreshThread1!!.interrupt()
            mRefreshThread1 = null
        }
        if (null != mRefreshThread2) {
            mRefreshThread2!!.interrupt()
            mRefreshThread2 = null
        }
        if (null != mRefreshThread3) {
            mRefreshThread3!!.interrupt()
            mRefreshThread3 = null
        }
        if (null != mAddThread) {
            mAddThread!!.interrupt()
            mAddThread = null
        }
        System.gc()
    }

    class Water {

        var water: Bitmap? = null
        var x: Float = 0.toFloat()
        var y: Float = 0.toFloat()
        var size = 0.01f// 水滴尺寸 0-1
        var rawTime: Long = 0
    }


}
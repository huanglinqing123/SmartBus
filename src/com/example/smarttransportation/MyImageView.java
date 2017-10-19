package com.example.smarttransportation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	private boolean onAnimation = true;// 是否处于动画状态
	private int rotateDegree = 10;// 执行动画的时间
	private boolean isFirst = true;// 标识是否为第一次绘制视图
	private float minScale = 0.95f;// 最小缩放限度
	private int vWidth;// 视图宽度
	private int vHeight;// 视图高度
	private boolean isFinish = true;// 标识是否为结束
	private boolean isActionMove = false;// 标识是否在移动状态
	private boolean isScale = false;// 标识是否为缩放状态
	private Camera camera;

	boolean XbigY = false;// 是否放大
	float RolateX = 0;// 旋转的X轴点
	float RolateY = 0;// 旋转的Y轴点

	OnViewClick onclick = null;

	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		camera = new Camera();
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		camera = new Camera();
	}

	public void SetAnimationOnOff(boolean oo) {
		onAnimation = oo;
	}

	/**
	 * 设置点击事件
	 * 
	 * @param onclick
	 */
	public void setOnClickIntent(OnViewClick onclick) {
		this.onclick = onclick;
	}

	/**
	 * 绘制View
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirst) {// 判断是否为第一次进入
			isFirst = false;
			init();
		}
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));// 抗锯齿
	}

	/**
	 * 初始化视图---初始化配置
	 */
	public void init() {
		vWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		vHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		Drawable drawable = getDrawable();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		bd.setAntiAlias(true);
	}

	/**
	 * 触摸监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (!onAnimation)// 判断是否为动画状态
			return true;

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:// 按下
			float X = event.getX();
			float Y = event.getY();
			RolateX = vWidth / 2 - X;
			RolateY = vHeight / 2 - Y;
			XbigY = Math.abs(RolateX) > Math.abs(RolateY) ? true : false;

			isScale = X > vWidth / 3 && X < vWidth * 2 / 3 && Y > vHeight / 3
					&& Y < vHeight * 2 / 3;
			isActionMove = false;

			if (isScale) {// 判断是否允许缩放
				scaleHandler.sendEmptyMessage(1);
			} else {
				rolateHandler.sendEmptyMessage(1);
			}
			break;
		case MotionEvent.ACTION_MOVE:// 移动
			float x = event.getX();
			float y = event.getY();
			if (x > vWidth || y > vHeight || x < 0 || y < 0) {
				isActionMove = true;
			} else {
				isActionMove = false;
			}

			break;
		case MotionEvent.ACTION_UP:// 松开
			if (isScale) {// 判断是否允许缩放
				scaleHandler.sendEmptyMessage(6);
			} else {
				rolateHandler.sendEmptyMessage(6);
			}
			break;
		}
		return true;
	}

	/**
	 * 自定义View点击事件接口
	 */
	public interface OnViewClick {
		public void onClick();
	}

	/**
	 * 旋转动画的Handler，根据状态执行动画
	 */
	@SuppressLint("HandlerLeak")
	private Handler rolateHandler = new Handler() {
		private Matrix matrix = new Matrix();
		private float count = 0;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			matrix.set(getImageMatrix());
			switch (msg.what) {
			case 1:
				count = 0;
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				rolateHandler.sendEmptyMessage(2);
				break;
			case 2:
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				if (count < getDegree()) {
					rolateHandler.sendEmptyMessage(2);
				} else {
					isFinish = true;
				}
				count++;
				count++;
				break;
			case 3:
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				if (count > 0) {
					rolateHandler.sendEmptyMessage(3);
				} else {
					isFinish = true;
					if (!isActionMove && onclick != null) {
						onclick.onClick();
					}
				}
				count--;
				count--;
				break;
			case 6:
				count = getDegree();
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				rolateHandler.sendEmptyMessage(3);
				break;
			}
		}
	};

	/**
	 * 开始旋转--使用线程锁控制，确保每次只执行一个动作
	 * 
	 * @param matrix
	 * @param rolateX
	 * @param rolateY
	 */
	private synchronized void BeginRolate(Matrix matrix, float rolateX,
			float rolateY) {
		int scaleX = (int) (vWidth * 0.5f);
		int scaleY = (int) (vHeight * 0.5f);
		camera.save();
		camera.rotateX(RolateY > 0 ? rolateY : -rolateY);
		camera.rotateY(RolateX < 0 ? rolateX : -rolateX);
		camera.getMatrix(matrix);
		camera.restore();
		// 控制中心点
		if (RolateX > 0 && rolateX != 0) {
			matrix.preTranslate(-vWidth, -scaleY);
			matrix.postTranslate(vWidth, scaleY);
		} else if (RolateY > 0 && rolateY != 0) {
			matrix.preTranslate(-scaleX, -vHeight);
			matrix.postTranslate(scaleX, vHeight);
		} else if (RolateX < 0 && rolateX != 0) {
			matrix.preTranslate(-0, -scaleY);
			matrix.postTranslate(0, scaleY);
		} else if (RolateY < 0 && rolateY != 0) {
			matrix.preTranslate(-scaleX, -0);
			matrix.postTranslate(scaleX, 0);
		}
		setImageMatrix(matrix);
	}

	/**
	 * 缩放动画Handler
	 */
	@SuppressLint("HandlerLeak")
	private Handler scaleHandler = new Handler() {
		private Matrix matrix = new Matrix();
		private float s;
		int count = 0;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			matrix.set(getImageMatrix());
			switch (msg.what) {
			case 1:
				if (!isFinish) {
					return;
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(minScale));
					BeginScale(matrix, s);
					scaleHandler.sendEmptyMessage(2);
				}
				break;
			case 2:
				BeginScale(matrix, s);
				if (count < 4) {
					scaleHandler.sendEmptyMessage(2);
				} else {
					isFinish = true;
					if (!isActionMove && onclick != null) {
						onclick.onClick();
					}
				}
				count++;
				break;
			case 6:
				if (!isFinish) {
					scaleHandler.sendEmptyMessage(6);
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(1.0f / minScale));
					BeginScale(matrix, s);
					scaleHandler.sendEmptyMessage(2);
				}
				break;
			}
		}
	};

	/**
	 * 开始缩放动画
	 * 
	 * @param matrix
	 * @param scale
	 */
	private synchronized void BeginScale(Matrix matrix, float scale) {
		int scaleX = (int) (vWidth * 0.5f);
		int scaleY = (int) (vHeight * 0.5f);
		matrix.postScale(scale, scale, scaleX, scaleY);
		setImageMatrix(matrix);
	}

	/**
	 * 获取执行的时间--旋转
	 * 
	 * @return
	 */
	public int getDegree() {
		return rotateDegree;
	}

	/**
	 * 设置动画时间--旋转
	 * 
	 * @param degree
	 */
	public void setDegree(int degree) {
		rotateDegree = degree;
	}

	/**
	 * 获取缩放程度
	 * 
	 * @return
	 */
	public float getScale() {
		return minScale;
	}

	/**
	 * 设置视图缩放度
	 * 
	 * @param scale
	 */
	public void setScale(float scale) {
		minScale = scale;
	}
}

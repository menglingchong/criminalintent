package com.bignerdranch.criminalintent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {

	private static final String TAG = "CrimeCameraFragment";
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;
	public static final String EXTRA_PHOTO_FILENAME = "com.bignerdranch.criminalintent.photo_filename";

	// 实现Camera的takePicture()中的ShutterCallback回调方法的接口
	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {

		@Override
		// 用于显示处理状态条
		public void onShutter() {
			// TODO Auto-generated method stub
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};

	// 实现Camera的takePicture()中的PictureCallback回调方法的接口
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {

		@Override
		// 用于保存照片文件,通过data保存图片数据
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			String filename = UUID.randomUUID().toString() + ".jpg";// 创建文件名
			FileOutputStream os = null;
			boolean success = true;
			try {
				os = getActivity().openFileOutput(filename,
						Context.MODE_PRIVATE);
				os.write(data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "Error writing to file" + filename, e);
				success = false;
			} finally {
				try {
					if (os != null)
						os.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "Error closing file" + filename, e);
					success = false;
				}
			}
			if (success) {
				Log.i(TAG, "JPEG saved at " + filename);
				Intent i = new Intent();
				i.putExtra(EXTRA_PHOTO_FILENAME, filename);// 将文件名放到extra中并附加到intent上
				getActivity().setResult(Activity.RESULT_OK, i);
			} else {
				getActivity().setResult(Activity.RESULT_CANCELED);
			}
			getActivity().finish();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_crime_camera, container,
				false);
		Button takepictureButton = (Button) v
				.findViewById(R.id.crime_camera_takePictureButton);
		takepictureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// getActivity().finish();
				if (mCamera != null) {
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				}
			}
		});
		mSurfaceView = (SurfaceView) v
				.findViewById(R.id.crime_camera_surfaceView);
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 该方法和常量被弃用

		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				try {
					if (mCamera != null) {
						mCamera.setPreviewDisplay(holder);// 该方法用来连接Camera和surface
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "Error setting up preview display", e);
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				if (mCamera == null)
					return;
				Camera.Parameters parameters = mCamera.getParameters();
				// Size s = null;
				// 设置相机的预览尺寸
				Size s = getBestSupportedSize(
						parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				// 设置图片尺寸
				s = getBestSupportedSize(parameters.getSupportedPictureSizes(),
						width, height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();// 用来在surface上绘制帧
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if (mCamera != null) {
					mCamera.stopPreview();// 停止在surface上绘制帧
				}
			}

		});

		mProgressContainer = v
				.findViewById(R.id.crime_camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);

		return v;
	}

	// 初始化相机，在此方法中打开相机,Camera的生命周期和Fragment的生命周期进行绑定
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);// 在API9中引入的该方法，0打开设备的第一相机(后置相机)
		} else {
			mCamera = Camera.open();
		}
	}

	// 将相机资源进行释放
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	// 找出设备支持的最佳尺寸
	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size s : sizes) {
			int area = s.width * s.height;
			if (area > largestArea) {
				bestSize = s;
				largestArea = area;
			}
		}
		return bestSize;
	}

}

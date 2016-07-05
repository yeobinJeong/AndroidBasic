package org.androidtown.media.camera.capture;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String IMAGE_FILE = "capture.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CameraSurfaceView cameraView = new CameraSurfaceView(getApplicationContext());
        FrameLayout previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        if (previewFrame != null) {
            previewFrame.addView(cameraView);
        }

        // 버튼 이벤트 처리
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cameraView.capture(new Camera.PictureCallback() {
                        public void onPictureTaken(byte[] data, Camera camera) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                String outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(),
                                        bitmap, "Captured Image", "Captured Image using Camera.");

                                if (outUriStr == null) {
                                    Log.d("SampleCapture", "Image insert failed.");
                                    return;
                                } else {
                                    Uri outUri = Uri.parse(outUriStr);
                                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                                }

                                Toast.makeText(getApplicationContext(),
                                        "카메라로 찍은 사진을 앨범에 저장했습니다.", Toast.LENGTH_LONG).show();

                                camera.startPreview();
                            } catch (Exception e) {
                                Log.e("SampleCapture", "Failed to insert image.", e);
                            }
                        }
                    });
                }
            });
        }

        //checkDangerousPermissions();
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();
            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            int rotation = MainActivity.this.getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;

            switch (rotation) {
                case Surface.ROTATION_0: degrees = 90; break;
                case Surface.ROTATION_90: degrees = 0; break;
                case Surface.ROTATION_180: degrees = 270; break;
                case Surface.ROTATION_270: degrees = 180; break;
            }

            camera.setDisplayOrientation(degrees);

            camera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        public boolean capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
                return true;
            } else {
                return false;
            }
        }
    }
}

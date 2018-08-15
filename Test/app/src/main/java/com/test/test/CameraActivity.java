package com.test.test;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.test.test.LoginActivity.music_selected;

public class CameraActivity extends AppCompatActivity {
    TextView messageText;
    ImageView imgTest;
    ImageView uploadButton;
    ImageView aa;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    String chooseFileRealPath;
    private static int PICK_IMAGE_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        getSupportActionBar().hide();

        aa = (ImageView)findViewById(R.id.btn_gallery);

        uploadButton = (ImageView)findViewById(R.id.uploadButton);
        upLoadServerUri = "http://59.14.120.193:80/UploadToServer.php"; // 서버컴퓨터의 ip주소

        uploadButton.setOnClickListener(new View.OnClickListener() { // 업로드 버튼을 누르면
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(CameraActivity.this, "", "Uploading file...", true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadFile(chooseFileRealPath); //파일 업로드
                    }
                }).start();
            }
        });
    }

    public void playCamera(View v) {
        ImageView camera = (ImageView)findViewById(R.id.btn_camera);

        switch(v.getId()) {
            case R.id.btn_camera:
                camera.setBackgroundResource(R.drawable.btn_camera_entered);
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                break;
        }
    }

    public void loadImagefromGallery(View view) { // 갤러리에서 이미지 선택
        ImageView gallery = (ImageView)findViewById(R.id.btn_gallery);
        gallery.setBackgroundResource(R.drawable.gallery_entered);

        Intent intent = new Intent(Intent.ACTION_PICK); // ACTION_PIC과 차이점?
        intent.setType("image/*"); // 이미지만 보이게
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE); // Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
        startActivityForResult(Intent.createChooser(intent, "악보를 선택하세요"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 이미지 선택작업을 후의 결과 처리
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) { // 이미지를 하나 골랐을때
                Uri uri = data.getData(); // data에서 절대경로로 이미지를 가져옴
                chooseFileRealPath= getRealPathFromURI(uri);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth())); // 이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);
                imgTest = (ImageView) findViewById(R.id.imgtest);
                imgTest.setImageBitmap(scaled);

                Log.d("FileRealPath : " , chooseFileRealPath);
            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    } // 갤러리 이미지 선택 끝

    public String getRealPathFromURI(Uri contetnUri) { // uri경로 절대경로로 변환하기
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(contetnUri,proj,null,null,null);
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    public int uploadFile(String sourceFileUri) { // 파일 업로드
        String fileName = sourceFileUri;
        Log.d("fileName = ", sourceFileUri);
        HttpURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri); // 에러발생

        if(!sourceFile.isFile()) { // 선택된 파일이 없으면
            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :" +chooseFileRealPath);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :" +chooseFileRealPath);
                }
            });

            return 0;
        } else {
            try {
                if(Build.VERSION.SDK_INT >= 23) {
                    int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                //에러발생//////////////////////////////////////////////////////////////////////////////////
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while(bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(CameraActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //5초후에 가져오도록
                SystemClock.sleep(5000);

                String str;
                String str2 = "";
                String HOSTIP = "http://59.14.120.193:80/downloads/Protocol.txt";
                URL url2 = new URL(HOSTIP);
                BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));

                while((str = in.readLine()) != null) {
                    str2 = str2 + " " + str;
                }

                in.close();
                Log.d("str2 = ", str2);
                //UI변경 불가 (안됨)
                //Toast.makeText(MainActivity.this, "File Upload Complete2.", Toast.LENGTH_SHORT).show();
                //close the streams
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(CameraActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(CameraActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file Exception", "Exception : " + e.getMessage(), e);
            }

            dialog.dismiss();

            return serverResponseCode;
        } // End else block
    }

    public void moveActivity(View v) {
        ImageView play = (ImageView)findViewById(R.id.play);
        ImageView music = (ImageView)findViewById(R.id.music);
        ImageView set = (ImageView)findViewById(R.id.set);
        ImageView back = (ImageView)findViewById(R.id.back);
        ImageView home = (ImageView)findViewById(R.id.home);

        switch(v.getId()) {
            case R.id.play:
                play.setBackgroundResource(R.drawable.btn_play_entered);

                if(music_selected) {
                    startActivity(new Intent(CameraActivity.this, GameActivity.class));
                } else {
                    startActivity(new Intent(CameraActivity.this, SelectGenreActivity.class));
                }
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(CameraActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(CameraActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(CameraActivity.this, SelectGenreActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(CameraActivity.this, MenuActivity.class));
                break;
        }

        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(CameraActivity.this, SelectGenreActivity.class));
        CameraActivity.this.finishAffinity();
    }
}
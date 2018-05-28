package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.myInterface.UserService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadHeadPortraitsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mHeader_iv;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    //调用照相机返回图片文件
    private File tempFile;
    private File file;
    //图像文件
    private Bitmap image;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_camera_need);
        initView();


    }

    private void initView() {
        user = (User) ObjectSaveUtil.readObject(UploadHeadPortraitsActivity.this);
        mHeader_iv = (ImageView) findViewById(R.id.mHeader_iv);
        Button mGoCamera_btn = (Button) findViewById(R.id.mGoCamera_btn);
        Button mGoAlbm_btn = (Button) findViewById(R.id.mGoAlbm_btn);
        Button upload = (Button) findViewById(R.id.upload);
        mGoCamera_btn.setOnClickListener(this);
        mGoAlbm_btn.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mGoCamera_btn:
                getPicFromCamera();
                break;
            case R.id.mGoAlbm_btn:
                getPicFromAlbm();
                break;
            case R.id.upload:
                upLoad();
                break;
            default:
                break;
        }
    }

    private void upLoad() {


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Map map =new HashMap();
        map.put("userId",user.getId());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);
        Call<Boolean> resultCall = userService.uploadImage(map,body);
        resultCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean b=response.body();
                if(b){
                    Toast.makeText(UploadHeadPortraitsActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("fail");
            }
        });

    }

    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(UploadHeadPortraitsActivity.this, "com.hansion.chosehead", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(UploadHeadPortraitsActivity.this, "com.hansion.chosehead", tempFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:     //调用剪裁后返回
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    image = bundle.getParcelable("data");
                    //设置到ImageView上
                    mHeader_iv.setImageBitmap(image);
                    //也可以进行一些保存、压缩等操作后上传
                    String path = saveImage("crop", image);
                }
                break;
        }
    }

    public String saveImage(String name, Bitmap bmp) {
        //存储路径
        String filePath = Environment.getExternalStorageDirectory().getPath();
        System.out.println(Environment.getExternalStorageDirectory().getPath());
        File appDir = new File(filePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + System.currentTimeMillis() + ".jpg";
        file = new File(appDir, fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            if (bmp == null) {
                Toast.makeText(UploadHeadPortraitsActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
            }
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            System.out.println(file.getAbsolutePath());
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

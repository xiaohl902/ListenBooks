package com.mwkj.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mwkj.activity.BigImgActivity;
import com.mwkj.activity.R;
import com.mwkj.activity.SetUpActivity;
import com.mwkj.widget.ListenUserDialog;
import com.qf.kenlibrary.base.BaseFragment;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我在听
public class ListeningFragment extends BaseFragment implements ListenUserDialog.OnClickDialogs {
    @Bind(R.id.ac_listen_userimg)
    ImageView acListenUserimg;
    @Bind(R.id.ac_listen_userName)
    TextView acListenUserName;
    @Bind(R.id.ac_listen_timer)
    TextView acListenTimer;
    private ListenUserDialog listenUserDialog;

    private Bitmap bitmap = null;

    @Override
    protected int getContentId() {
        return R.layout.fragment_listening;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void init(View view) {
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
        listenUserDialog = new ListenUserDialog(getActivity(), this);
        listenUserDialog.setOnClickDialogs(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.ac_listen_userimg, R.id.ac_listen_userName, R.id.iv_sz, R.id.ac_listen_rl1, R.id.ac_listen_rl2, R.id.ac_listen_rl3, R.id.ac_listen_timer, R.id.ac_listen_rl4, R.id.ac_listen_rl5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sz:
                //跳转到设置页面
                Intent intent = new Intent(this.getActivity(), SetUpActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_listen_userimg:
                listenUserDialog.show();
                break;
            case R.id.ac_listen_userName:
                //跳转到登录页面，或跳转到设置个人资料页面
                Toast.makeText(getActivity(), "跳转到登录页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ac_listen_rl1:
                //听听历史
                Toast.makeText(getActivity(), "听听历史", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ac_listen_rl2:
                //我的下载
                Toast.makeText(getActivity(), "我的下载", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ac_listen_rl3:
                //我的收藏
                Toast.makeText(getActivity(), "我的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ac_listen_rl4:
                //定时关闭
                Toast.makeText(getActivity(), "定时关闭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ac_listen_rl5:
                //问题反馈
                Toast.makeText(getActivity(), "问题反馈", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * DiaLog的点击事件
     *
     * @param photograph
     * @param choice
     * @param bigPicture
     */
    @Override
    public void onMyClick(View photograph, View choice, View bigPicture, View finishBtn) {
        //现在去拍照
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenUserDialog.dismiss();//关闭dialog
                Intent openAlbumIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到照相机
                //保存到SDK
                File file = new File(Environment.getExternalStorageDirectory(), "aaa.jpg");
                openAlbumIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(openAlbumIntent, 1);//跳转到照相机页面
            }
        });

        //手机里选择
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenUserDialog.dismiss();//关闭dialog
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(openAlbumIntent, 2);
            }
        });

        //查看大图
        bigPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenUserDialog.dismiss();//关闭dialog
               Intent intent = new Intent(getActivity(), BigImgActivity.class);
                intent.putExtra("img",bitmap);
                startActivity(intent);
            }
        });

        //返回
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenUserDialog.dismiss();//关闭dialog
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap photo = null;
//        if (data != null) {
//            Log.d("print", "onActivityResult: 照片返回");
//            switch (requestCode) {
//                case 1: //拍摄图片并选择
//                    //两种方式 获取拍好的图片
//                    if (data.getData() != null || data.getExtras() != null) { //防止没有返回结果
//                        Uri uri = data.getData();
//
//                        Log.d("print", "onActivityResult: dd--"+uri);
//                        if (uri != null) {
//                            Log.d("print", "onActivityResult: 照片不为空");
//                            photo = BitmapFactory.decodeFile(uri.getPath()); //拿到图片
//                            acListenUserimg.setImageBitmap(photo);
//                        }
//                        if (photo == null) {
//                            Bundle bundle = data.getExtras();
//                            if (bundle != null) {
//                                photo = (Bitmap) bundle.get("data");
//                            } else {
//                                Toast.makeText(getActivity(), "找不到图片", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        //处理图片
//                        //裁剪图片
//                    }
//                    break;
//
//            }
//        }


        switch (requestCode) {
            case 1://照相机页面返回过来的图片
                File file = new File(Environment.getExternalStorageDirectory(), "aaa.jpg");
//       // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//
//        /**缩小图片的二次采样*/
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        /**缩小到原图的   1/3    */
//        options.inSampleSize = 8;
                BitmapFactory.Options options = new BitmapFactory.Options();
                //只读信息，不取图片
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), options);
                int inSampleSize = options.outWidth / 10;
                //读取信息读取图片
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(file.getPath(), options);
                acListenUserimg.setImageBitmap(bitmap);

                break;
            case 2:
                if (data == null) return;
                Uri uri = data.getData(); // 得到Uri

                String path = getPaths(getContext(), uri);
                File file1 = new File(path);


                bitmap = BitmapFactory.decodeFile(file1.getAbsolutePath());
                acListenUserimg.setImageBitmap(bitmap);

//                File file2 = new File(Environment.getExternalStorageDirectory(),"aaa.jpg");
//                File file2 = file1;
//
//                BitmapFactory.Options options2 = new BitmapFactory.Options();
//                //只读信息，不取图片
//                options2.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(file2.getPath(),options2);
//                int inSampleSize2 = options2.outWidth / 10;
//                //读取信息读取图片
//                options2.inJustDecodeBounds = false;
//                Bitmap bitmap2 = BitmapFactory.decodeFile(file2.getPath(),options2);
//                if (bitmap2==null) return;
//                acListenUserimg.setImageBitmap(bitmap2);


                break;
        }
    }

    public static String getPaths(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}

package com.dlc.mp3demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.dlc.mp3demo.mP3s.SimpleExoPlayerHelper;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class MainActivity extends Activity {

    private Button btn_downFile;
    private Button btn_downMP3;
    private Button btn_downMPzt;
    private Button btn_downMPtingz;
    private Button btn_downMPjixu;

    PlayerView mExoPlay;
    private SimpleExoPlayerHelper mMySimpleExoPlayer;

    private List<String> mVideoSourseList = new ArrayList<>();

    private MediaPlayer mediaPlayer2;
    private static final float BEEP_VOLUME2 = 9.10f;
    private final MediaPlayer.OnCompletionListener beepListener2 = new MediaPlayer.OnCompletionListener() { // 声音
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void initBeepSound2() {
        if (mediaPlayer2 == null) {

            mediaPlayer2 = new MediaPlayer();
            mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer2.setOnCompletionListener(beepListener2);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.ruzuo);
            try {

                Uri mUri = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.ruzuo);
                mediaPlayer2.setDataSource(this, mUri);

//                mediaPlayer2.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//                file.close();

                mediaPlayer2.setVolume(BEEP_VOLUME2, BEEP_VOLUME2);
                mediaPlayer2.prepare();
            } catch (IOException e) {
                mediaPlayer2 = null;
            }
        }
    }

    private CustomVideoView logo_video;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExoPlay = findViewById(R.id.exo_play);
        logo_video = findViewById(R.id.logo_video);
        imageView = findViewById(R.id.img);
        String vds = "https://gzlxcdg.https.xiaozhuschool.com/public/uploads/video/20200504/fa530b8ae53f8d2e7698ea0fb22d22d5.mp4";

        String uri = "android.resource://" + getPackageName() + "/" + R.raw.default1;
//        logo_video.setVideoURI(Uri.parse((uri)));//播放本地视频mp4
        logo_video.setVideoPath(vds);//播放云端视频mp4
        logo_video.setMediaController(new MediaController(MainActivity.this));  //添加控制台
        logo_video.requestFocus();  //获取焦点         ／／监听播完了重播
        logo_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                logo_video.start();
            }
        });
//        setFirstFrameDrawable(vds);
//        setFirstFrameDrawable(uri);
        getNetVideoBitmap(vds);
//        logo_video.start();

        initBeepSound2();
//        mediaPlayer2.start();//播放本地MP3

        mMySimpleExoPlayer = SimpleExoPlayerHelper.createMySimpleExoPlayer(this, mExoPlay)
                .setRepeatMode(SimpleExoPlayerHelper.RepeatMode.REPEAT_MODE_OFF);

        btn_downFile = (Button) this.findViewById(R.id.button);
        btn_downMP3 = (Button) this.findViewById(R.id.button2);
        btn_downMPzt = (Button) this.findViewById(R.id.button3);
        btn_downMPtingz = (Button) this.findViewById(R.id.button4);
        btn_downMPjixu = (Button) this.findViewById(R.id.button5);
        btn_downFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_downMP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vds = "https://gzlxcdg.https.xiaozhuschool.com/public/uploads/video/20200504/fa530b8ae53f8d2e7698ea0fb22d22d5.mp4";

                String urlStr = "http://zsxyylsb.app.xiaozhuschool.com/public/uploads/imgs/20200902/b04b350875d486b6957973bf7ea56dfc.mp3";
                if (!mVideoSourseList.isEmpty()) {
                    mVideoSourseList.clear();
                }
//                mVideoSourseList.add(mUri.getPath());
                mVideoSourseList.add(urlStr);
                mVideoSourseList.add(vds);

                mMySimpleExoPlayer.prepare(mVideoSourseList);

                mMySimpleExoPlayer.setRepeatMode(SimpleExoPlayerHelper.RepeatMode.REPEAT_MODE_OFF);
                mMySimpleExoPlayer.start();//播放云MP3

            }
        });

        btn_downMPzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMySimpleExoPlayer.pause();
            }
        });
        btn_downMPtingz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMySimpleExoPlayer.stop();
            }
        });
        btn_downMPjixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMySimpleExoPlayer.start();
            }
        });
    }

    private Bitmap firstFrame;

    private void setFirstFrameDrawable(final String url){
//        Glide.with(this)
//                .load(url)
//                .into(imageView);


        MediaMetadataRetriever media = new MediaMetadataRetriever();

        media.setDataSource(url);

        firstFrame=  media.getFrameAtTime();

                mHandler.sendEmptyMessage(0);

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(firstFrame);
        }
    };

    //如果指定的视频的宽高都大于了MICRO_KIND的大小，那么你就使用MINI_KIND就可以了
    public static Bitmap getVideoThumbnail(String videoPath, int kind, int width, int height) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        if (width > 0 && height > 0) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }

        return bitmap;
    }


    public  static Bitmap getVideoThumb(String path) {

        MediaMetadataRetriever media = new MediaMetadataRetriever();

        media.setDataSource(path);

        return  media.getFrameAtTime();

    }

    public Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            firstFrame = retriever.getFrameAtTime();
            mHandler.sendEmptyMessage(0);
//            imageView.setImageBitmap(bitmap);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
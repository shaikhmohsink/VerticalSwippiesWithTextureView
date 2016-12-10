package com.mohsin.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.android.libraries.mediaframework.exoplayerextensions.Video;
import com.google.android.libraries.mediaframework.layeredvideo.PlaybackControlLayer;
import com.mohsin.AppController;
import com.mohsin.R;
import com.mohsin.adplayer.ImaPlayer;
import com.mohsin.model.VideoListItem;

import java.io.IOException;

import butterknife.InjectView;
import jp.satorufujiwara.player.VideoSource;
import jp.satorufujiwara.player.VideoTexturePresenter;
import jp.satorufujiwara.player.VideoTextureView;
import jp.satorufujiwara.player.hls.HlsVideoSource;

/**
 * Created by user on 30-07-2016.
 */
public class PopularCategoriesFragment extends Fragment implements ImaPlayer.PlaybackEnded {
    public PopularCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String videoURL;

    private View view;
    public String colorVaule;
    public VideoView videoView;
    public MediaPlayer mediaPlayer;
    public TextureView textureView;
    public int indexOfThis = 0;
    public int currentlySelected = 0;
    public String videoToPlayURL = "";

    public ProgressBar busyCursor;




    public VideoTexturePresenter videoTexturePresenter;
    //@InjectView(R.id.videoTextureView)
    public VideoTextureView videoTextureView;







    /**
     * The player which will be used to play the content videos and the ads.
     */
    public ImaPlayer imaPlayer;

    /**
     * The {@link android.widget.FrameLayout} that will contain the video player.
     */
    private FrameLayout videoPlayerContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro, container, false);
        view = inflater.inflate(R.layout.popular_categories_item, container, false);
        view.setBackgroundColor(Color.parseColor(getColorVaule()));
        busyCursor = (ProgressBar) view.findViewById(R.id.progressBar);
        videoTextureView = (VideoTextureView) view.findViewById(R.id.videoTextureView);
        //videoView = (VideoView) view.findViewById(R.id.videoView);
        /*textureView = (TextureView) view.findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);*/



        // This container will be the video player.
        /*videoPlayerContainer = (FrameLayout) view.findViewById(R.id.video_frame);
        startPlayingVideo();*/




        videoTexturePresenter = new VideoTexturePresenter(videoTextureView);
        videoTexturePresenter.onCreate();
        VideoSource source = HlsVideoSource
                //.newBuilder(Uri.parse("hls playlist url."), "UserAgent")
                .newBuilder(Uri.parse(videoToPlayURL), "UserAgent")
                .bufferSegmentSize(64 * 1024)
                .bufferSegmentCount(512)
                .build();
        videoTexturePresenter.setSource(source);
        videoTexturePresenter.prepare();


        /*videoTexturePresenter.play();
        videoTexturePresenter.seekTo(0);
        videoTexturePresenter.setMute(true);
        videoTexturePresenter.pause();*/


        return view;
    }
    @Override
    public void onDestroyView() {
        videoTexturePresenter.release();
        videoTexturePresenter.onDestroy();
        super.onDestroyView();
    }


    /*@Override
    public void playbackEnded() {
        System.out.println("INSIDE public void playbackEnded() {");
        startPlayingVideo();
    }*/

    public void startPlayingVideo() {
        if (imaPlayer != null) {
            imaPlayer.release();
        }

        // If there was previously a video player in the container, remove it.
        videoPlayerContainer.removeAllViews();

        String adTagUrl = "";
        //String adTagUrl = videoListItem.adUrl;
        //String adTagUrl = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpremidpostpodbumper&cmsid=496&vid=short_onecue&correlator=";
        //String adTagUrl = "http://shadow01.yumenetworks.com/dynamic_preroll_playlist.vast2xml?domain=1552hCkaKYjg";
        String videoTitle = "Random Video Title";
        /*VideoListItem videoListItem = new VideoListItem("Random Video Title",
                new Video("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/576047c797f8157108beb7c3/vod/576047c797f8157108beb7c3.m3u8", Video.VideoType.HLS),
                adTagUrl);*/
        VideoListItem videoListItem = new VideoListItem("Random Video Title",
                new Video(videoToPlayURL, Video.VideoType.HLS),
                adTagUrl);
        imaPlayer = new ImaPlayer(this,
                videoPlayerContainer,
                videoListItem.video,
                videoTitle,
                adTagUrl);
        //imaPlayer.play();
    }
    @Override
    public void playbackEnded() {
        System.out.println("INSIDE public void playbackEnded() {");
        try { startPlayingVideo(); } catch(Exception e) {}
        busyCursor.setVisibility(View.VISIBLE);
    }
    @Override
    public void playbackReady() {
        busyCursor.setVisibility(View.GONE);
        if(AppController.currentlySelectedPopularCategoriesPage == indexOfThis)
            imaPlayer.play();
    }
    @Override
    public void isBufferringStarted() {
        busyCursor.setVisibility(View.VISIBLE);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getColorVaule() {
        return colorVaule;
    }

    public void setColorVaule(String colorVaule) {
        this.colorVaule = colorVaule;
    }

    /*@Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface s = new Surface(surfaceTexture);

        try {
            mediaPlayer= new MediaPlayer();
            System.out.println("videoToPlayURLvideoToPlayURLvideoToPlayURL:"+videoToPlayURL);
            mediaPlayer.setDataSource(videoToPlayURL);
            mediaPlayer.setSurface(s);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
            //mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                    System.out.println("onBufferingUpdateonBufferingUpdateonBufferingUpdate:"+i);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    System.out.println(i1+"onErroronErroronError:"+i);
                    return true;
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    //System.out.println("");
                    return true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    *//*try {
                        mediaPlayer.seekTo(100);
                        if (currentlySelected == indexOfThis)
                            mediaPlayer.start();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }*//*
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    try {
                        System.out.println("onPreparedonPreparedonPrepared");
                        *//*mediaPlayer.seekTo(100);
                        mediaPlayer.pause();*//*

                        if (currentlySelected == indexOfThis)
                            mediaPlayer.start();*//*
                        else
                            mediaPlayer.pause();*//*
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            *//*mediaPlayer.setOnVideoSizeChangedListener(this);*//*
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //mediaPlayer.prepareAsync();
            //mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }*/
}
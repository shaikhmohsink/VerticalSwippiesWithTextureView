package com.mohsin.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.mohsin.R;

import java.io.IOException;

/**
 * Created by user on 30-07-2016.
 */
public class PopularCategoriesFragment extends Fragment implements TextureView.SurfaceTextureListener {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro, container, false);
        view = inflater.inflate(R.layout.popular_categories_item, container, false);
        view.setBackgroundColor(Color.parseColor(getColorVaule()));
        //videoView = (VideoView) view.findViewById(R.id.videoView);
        textureView = (TextureView) view.findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);

        return view;
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

    @Override
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
                    /*try {
                        mediaPlayer.seekTo(100);
                        if (currentlySelected == indexOfThis)
                            mediaPlayer.start();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }*/
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    try {
                        System.out.println("onPreparedonPreparedonPrepared");
                        /*mediaPlayer.seekTo(100);
                        mediaPlayer.pause();*/

                        if (currentlySelected == indexOfThis)
                            mediaPlayer.start();/*
                        else
                            mediaPlayer.pause();*/
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            /*mediaPlayer.setOnVideoSizeChangedListener(this);*/
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

    }
}
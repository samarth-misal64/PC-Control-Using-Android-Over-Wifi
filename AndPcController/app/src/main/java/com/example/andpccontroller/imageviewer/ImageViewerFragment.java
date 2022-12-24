package com.example.andpccontroller.imageviewer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


import com.example.andpccontroller.filetransfer.TransferFileToServer;
import com.example.andpccontroller.R;
import com.example.andpccontroller.MusicImageAvatarAdapter;
import com.example.andpccontroller.MusicImageAvatar;
import com.example.andpccontroller.MainActivity;

public class ImageViewerFragment extends Fragment {

    ListView mediaPlayerListView;
    ProgressBar avatarProgressBar;

    public ImageViewerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_image_viewer, container, false);
        avatarProgressBar = (ProgressBar) rootView.findViewById(R.id.musicImageAvatarProgressBar);
        mediaPlayerListView = (ListView) rootView.findViewById(R.id.mediaPlayerListView);
        new ImagesList(getActivity()) {
            public void receiveData(Object result) {
                avatarProgressBar.setVisibility(View.GONE);
                ArrayList<MusicImageAvatar> images = (ArrayList<MusicImageAvatar>) result;
                mediaPlayerListView.setAdapter(new MusicImageAvatarAdapter(getActivity(),
                        R.layout.music_image_avatar, images));
            }
        }.execute();
        mediaPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                MusicImageAvatar image = (MusicImageAvatar) parent.getItemAtPosition(position);
                String fileName = image.getHeading();
                String path = image.getData();
                transferFile(fileName, path);
            }

        });
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.image_viewer));
    }

    private void transferFile(final String name, String path) {
        if (MainActivity.clientSocket != null) {
            MainActivity.sendMessageToServer("FILE_TRANSFER_REQUEST");
            MainActivity.sendMessageToServer(name);
            new TransferFileToServer(getActivity()){

                @Override
                public void receiveData(Object result) {
                    MainActivity.sendMessageToServer("SHOW_IMAGE");
                    MainActivity.sendMessageToServer(name);
                }

            }.execute(new String[]{name, path});
        } else {
            Toast.makeText(getActivity(), "Not Connected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.sendMessageToServer("CLOSE_IMAGE_VIEWER");
    }

}

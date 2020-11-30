package com.example.notas.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.notas.Activity_agregar;
import com.example.notas.R;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class FragmentoAgregar extends Fragment implements View.OnClickListener {

    Button agregar, btnNuevo;
    private ImageView imageView;

    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private String mCurrentPhotoPath;
    private Uri photoURI;

    public FragmentoAgregar() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.agregar, container, false);

        agregar= root.findViewById(R.id.agregarfoto);
        btnNuevo = root.findViewById(R.id.btnNuevoRegistro);


        agregar.setOnClickListener(this);
        btnNuevo.setOnClickListener(this);

        return root;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.agregarfoto:

                break;
            case R.id.btnNuevoRegistro:
                Intent intent = new Intent(getContext(), Activity_agregar.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

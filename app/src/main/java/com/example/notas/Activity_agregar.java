package com.example.notas;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.DAOS.DAOMultimedia;
import com.example.notas.DAOS.DAONota;
import com.example.notas.DAOS.DAORecordatorio;
import com.example.notas.DAOS.DAOTarea;
import com.example.notas.adapters.AdapterMultimedia;
import com.example.notas.modelos.ModeloMultimedia;
import com.example.notas.modelos.ModeloNota;
import com.example.notas.modelos.ModeloRecordatorio;
import com.example.notas.modelos.ModeloTarea;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Activity_agregar extends AppCompatActivity
        implements View.OnClickListener,
        AdapterMultimedia.MultimediaClic {

    Boolean flag = true;
    MediaRecorder mediaRecorder = null;
    String audioNombre, audioRuta;
    Boolean Actualizar = false;
    Button btnRecordatorio, btnRealizacion, btnFoto, btnImagen, btnVideo, btnGaleria, btnAudio, btnVer;
    EditText txtDescMultimedia, txtTitulo, txtDescripcion, txtContenido;
    AdapterMultimedia adapter;
    RecyclerView rcvMultimedia;
    FloatingActionButton flbAgregar;
    String medioEstado = "";
    String date_time = "";
    int anio;
    int mes;
    int dia;
    int hora;
    int minuto;
    DAONota daoNota;
    DAOTarea daoTarea;
    DAOMultimedia daoMultimedia;
    DAORecordatorio daoRecordatorio;
    ModeloNota nota;
    ModeloTarea tarea;
    ArrayList<Date> recordatorios = new ArrayList<>();
    ArrayList<Date> actRecortadtorios = new ArrayList<>();
    ArrayList<ModeloMultimedia> actMultimedias = new ArrayList<>();
    String Es = "";

    public Activity_agregar() {
    }

    final Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuevo_agregar);

        btnRecordatorio = findViewById(R.id.btnRecordatorio);
        btnRealizacion = findViewById(R.id.btnRealizacion);
        btnFoto = findViewById(R.id.btnFoto);
        btnImagen = findViewById(R.id.btnImagen);
        btnVideo = findViewById(R.id.btnVideo);
        btnGaleria = findViewById(R.id.btnGaleria);
        btnAudio = findViewById(R.id.btnAudio);
        btnVer = findViewById(R.id.btnVerRecordatorios);
        txtDescMultimedia = findViewById(R.id.txtDescMultimedia);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtContenido = findViewById(R.id.txtContenido);
        rcvMultimedia = findViewById(R.id.rcvMultimedia);
        flbAgregar = findViewById(R.id.flbAgregar);

        btnRecordatorio.setOnClickListener(this);
        btnRealizacion.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        btnImagen.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnGaleria.setOnClickListener(this);
        btnAudio.setOnClickListener(this);
        btnVer.setOnClickListener(this);
        btnGaleria.setOnClickListener(this);
        flbAgregar.setOnClickListener(this);

        try {
            nota = (ModeloNota) getIntent().getExtras().getSerializable("nota");
            tarea = (ModeloTarea) getIntent().getExtras().getSerializable("tarea");
        } catch (Exception ex) {

        }

        if (nota != null) {
            Actualizar = true;
            Es = "nota";
            tarea = new ModeloTarea();
            txtTitulo.setText(nota.getTitulo());
            txtDescripcion.setText(nota.getDescripcion());
            txtContenido.setText(nota.getContenido());
            actualizaMultimedia();
        } else if (tarea != null) {
            Actualizar = true;
            Es = "tarea";
            nota = new ModeloNota();
            txtTitulo.setText(tarea.getTitulo());
            txtDescripcion.setText(tarea.getDescripcion());
            txtContenido.setText(tarea.getContenido());
            btnRealizacion.setText(tarea.getFecha_cumplir());
            nota.setNotas_multimedia(tarea.getMultimedias());
            actualizaMultimedia();
        } else {
            nota = new ModeloNota();
            tarea = new ModeloTarea();
        }
    }

    private void actualizaMultimedia() {
        adapter = new AdapterMultimedia(nota.getNotas_multimedia(), this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rcvMultimedia.setLayoutManager(manager);
        rcvMultimedia.setItemAnimator(new DefaultItemAnimator());
        rcvMultimedia.setAdapter(adapter);
    }

    final int PERMISSION_ALL = 1;


    private static boolean Permisos(Context context, String... permissions) {
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
        switch (view.getId()) {
            case R.id.btnFoto:
                String[] permisosCamara = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                };

                if (!Permisos(this, permisosCamara)) {
                    ActivityCompat.requestPermissions(this, permisosCamara, PERMISSION_ALL);
                }

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                } else {
                    try {
                        Foto();
                    } catch (IOException e) {
                        Toast.makeText(this, "Fallo al tomar la foto.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnImagen:
                String[] permisosImagen = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!Permisos(this, permisosImagen)) {
                    ActivityCompat.requestPermissions(this, permisosImagen, PERMISSION_ALL);
                }

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                } else {
                    nuevaImagen();
                }
                break;
            case R.id.btnVideo:
                String[] permisosVideo = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                };

                if (!Permisos(this, permisosVideo)) {
                    ActivityCompat.requestPermissions(this, permisosVideo, PERMISSION_ALL);
                }

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                } else {
                    try {
                        Video();
                    } catch (IOException e) {
                        Toast.makeText(this, "Algo pasó al tomar el video.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnGaleria:
                String[] permisosGaleria = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!Permisos(this, permisosGaleria)) {
                    ActivityCompat.requestPermissions(this, permisosGaleria, PERMISSION_ALL);
                }

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                } else {
                    Galeria();
                }
                break;
            case R.id.btnAudio:
                String[] permisosAudio = {
                        Manifest.permission.RECORD_AUDIO
                };

                if (!Permisos(this, permisosAudio)) {
                    ActivityCompat.requestPermissions(this, permisosAudio, PERMISSION_ALL);
                }

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                } else {
                    onGrabar(flag);
                }
                break;
            case R.id.btnRecordatorio:
                datePicker("recordatorio");
                break;
            case R.id.btnRealizacion:
                datePicker("realizacion");
                break;
            case R.id.btnVerRecordatorios:
                verRecordatorios();
                break;
            case R.id.flbAgregar:
                if (Actualizar == true) {
                    if (Es.equals("nota")) {
                        daoNota = new DAONota(getApplicationContext());
                        daoMultimedia = new DAOMultimedia(getApplicationContext());
                        try {
                            nota.setTitulo(txtTitulo.getText().toString());
                            nota.setDescripcion(txtDescripcion.getText().toString());
                            nota.setContenido(txtContenido.getText().toString());
                            daoNota.actualizarNota(nota);
                            for (int i = 0; i < actMultimedias.size(); i++) {
                                daoMultimedia.insertMultiNota(actMultimedias.get(i), nota.getId());
                            }
                            Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }

                    } else if (Es.equals("tarea")) {
                        daoTarea = new DAOTarea(getApplicationContext());
                        daoMultimedia = new DAOMultimedia(getApplicationContext());
                        daoRecordatorio = new DAORecordatorio(getApplicationContext());
                        try {
                            tarea.setTitulo(txtTitulo.getText().toString());
                            tarea.setDescripcion(txtDescripcion.getText().toString());
                            tarea.setContenido(txtContenido.getText().toString());
                            tarea.setFecha_cumplir(btnRealizacion.getText().toString());
                            daoTarea.actualizarTarea(tarea);
                            for (int i = 0; i < actMultimedias.size(); i++) {
                                daoMultimedia.insertMultiTareas(actMultimedias.get(i), tarea.getId());
                            }
                            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            ArrayList<ModeloRecordatorio> lista = new ArrayList<>();
                            for (int i = 0; i < actRecortadtorios.size(); i++) {
                                lista.add(new ModeloRecordatorio(f.format(actRecortadtorios.get(i))));
                            }
                            for (int i = 0; i < lista.size(); i++) {
                                daoRecordatorio.insert(lista.get(i), tarea.getId());
                            }
                            Toast.makeText(this, "Tarea actualizada :3", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "Algo salió mal :c", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    final CharSequence[] items = {"Guardar como nota", "Guardar como tarea"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                daoNota = new DAONota(getApplicationContext());
                                nota.setTitulo(txtTitulo.getText().toString());
                                nota.setDescripcion(txtDescripcion.getText().toString());
                                nota.setContenido(txtContenido.getText().toString());
                                nota.setFecha(fechaActual());
                                daoNota.insertaNota(nota);
                                Toast.makeText(Activity_agregar.this, "Se agregó la nota", Toast.LENGTH_SHORT).show();
                            } else if (item == 1) {
                                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                daoTarea = new DAOTarea(getApplicationContext());
                                tarea.setTitulo(txtTitulo.getText().toString());
                                tarea.setDescripcion(txtDescripcion.getText().toString());
                                tarea.setContenido(txtContenido.getText().toString());
                                tarea.setFecha(fechaActual());
                                tarea.setFecha_cumplir(btnRealizacion.getText().toString());
                                for (Date fecha : recordatorios) {
                                    tarea.addRecordatorio(new ModeloRecordatorio(f.format(fecha)));
                                }
                                daoTarea.insertarTarea(tarea);
                                Toast.makeText(Activity_agregar.this, "Se agregó la tarea", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }
                break;
        }
    }

    private void verRecordatorios(){
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String r = "";
        for (int i = 0; i < recordatorios.size(); i++) {
            r += formato.format(recordatorios.get(i)) + "\n";
        }
        for (int i = 0; i < actRecortadtorios.size(); i++) {
            r += formato.format(actRecortadtorios.get(i)) + "\n";
        }
        if(r.equals("") == false){
            Toast.makeText(this, r, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No hay recordatorios", Toast.LENGTH_SHORT).show();
        }
    }

    private String fechaActual() {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date fechaActual = Calendar.getInstance().getTime();
        String actual = formato.format(fechaActual);
        return actual;
    }

    private void datePicker(final String caja) {
        anio = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        timePicker(caja);
                    }
                }, anio, mes, dia);
        datePickerDialog.show();
    }

    private void timePicker(final String caja) {
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora = hourOfDay;
                        minuto = minute;
                        String f = date_time + " " + hourOfDay + ":" + minute + ":00";
                        switch (caja) {
                            case "realizacion":
                                btnRealizacion.setText(f);
                                break;
                            case "recordatorio":
                                try {
                                    Date nuevafecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(f);
                                    if (Actualizar == true) {
                                        actRecortadtorios.add(nuevafecha);
                                    }
                                    recordatorios.add(nuevafecha);
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent notificationIntent = new Intent(getApplicationContext(), Alarma.class);
                                    notificationIntent.putExtra("nombre", txtTitulo.getText().toString());
                                    PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    Calendar calendario = Calendar.getInstance();
                                    calendario.setTime(nuevafecha);
                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(), broadcast);
                                    Toast.makeText(Activity_agregar.this, "Añadido recordatorio a la fecha " + f, Toast.LENGTH_SHORT).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                }, hora, minuto, false);
        timePickerDialog.show();
    }

    String nombreFoto, rutaFotoImagen;
    Uri uriDeLaFoto;
    static final int REQUEST_TAKE_PHOTO = 1;

    private void Foto() throws IOException {
        medioEstado = "foto";
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File archivo = creaBaseImagen();
            if (archivo != null) {
                uriDeLaFoto = FileProvider.getUriForFile(this,
                        "com.example.notas.fileprovider",
                        archivo);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriDeLaFoto);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String nombreVideo, rutaVideo;
    Uri uriDelVideo;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void Video() throws IOException{
        medioEstado = "video";
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private File creaBaseImagen() throws IOException {
        int numero = (int) (Math.random() * (999 - 1 + 1) + 1);
        String hora = new SimpleDateFormat("HHmmss").format(new Date());
        hora += "_" + numero;
        nombreFoto = "img_" + hora;
        File alm = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File base = File.createTempFile(nombreFoto, ".jpg", alm);
        rutaFotoImagen = base.getAbsolutePath();
        //Corta la ruta y obtiene el nombre
        nombreFoto = rutaFotoImagen.substring(rutaFotoImagen.lastIndexOf("/") + 1);
        return base;
    }

    private void creaVideo(AssetFileDescriptor descriptor) throws IOException {
        int numero = (int) (Math.random() * (999 - 1 + 1) + 1);
        String hora = new SimpleDateFormat("HHmmss").format(new Date());
        hora += "_" + numero;
        nombreVideo = "vidio_" + hora;
        FileInputStream entrada = descriptor.createInputStream();
        File alm = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File ruta = new File(alm.getAbsoluteFile() + "/");
        File base = new File(ruta, nombreVideo + ".mp4");
        rutaVideo = base.getAbsolutePath();
        //Corta la ruta y obtiene el nombre
        nombreVideo = rutaVideo.substring(rutaVideo.lastIndexOf("/") + 1);
        OutputStream salida = new FileOutputStream(base);
        byte[] buf = new byte[1024];
        int len;
        while ((len = entrada.read(buf)) > 0) {
            salida.write(buf, 0, len);
        }
        entrada.close();
        salida.close();
        nuevoMultimedia(nombreVideo, "video", txtDescMultimedia.getText().toString(), rutaVideo);
    }

    private static int RESULT_LOAD_IMAGE = 1;
    Uri imagenSeleccionada;

    private void nuevaImagen() {
        medioEstado = "imagen";
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    private static int RESULT_LOAD_VIDEO = 1;
    Uri videoSeleccionado;

    private void Galeria() {
        medioEstado = "galeria";
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_VIDEO);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 1;
    static final int REQUEST_LOAD_VIDEO = 1;
    AssetFileDescriptor descriptor = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (medioEstado.equals("foto")) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                String des = txtDescMultimedia.getText().toString();
                nuevoMultimedia(nombreFoto, medioEstado, des, rutaFotoImagen);
            }
        } else if (medioEstado.equals("imagen")) {
            if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK) {
                String des = txtDescMultimedia.getText().toString();
                imagenSeleccionada = data.getData();
                String[] patrones = {MediaStore.Images.Media.DATA};
                //Obtiene las posibles imagenes seleccionadas
                Cursor imagenes = getContentResolver().query(imagenSeleccionada, patrones, null, null, null);
                imagenes.moveToFirst();
                //Como solo es una, obtiene el índice 0
                int columnImageIndex = imagenes.getColumnIndex(patrones[0]);
                String ruta = imagenes.getString(columnImageIndex);
                imagenes.close();
                rutaFotoImagen = ruta;
                //Obtiene el nombre de la imagen
                nombreFoto = rutaFotoImagen.substring(rutaFotoImagen.lastIndexOf("/") + 1);
                nuevoMultimedia(nombreFoto, medioEstado, des, rutaFotoImagen);
            }
        } else if(medioEstado.equals("video")){
            if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
                uriDelVideo = data.getData();
                try {

                    descriptor = getContentResolver().openAssetFileDescriptor(data.getData(), "rw");
                    creaVideo(descriptor);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if(medioEstado.equals("galeria")){
            if (requestCode == REQUEST_LOAD_VIDEO && resultCode == RESULT_OK) {
                String des = txtDescMultimedia.getText().toString();
                videoSeleccionado = data.getData();
                String[] patrones = {MediaStore.Video.Media.DATA};

                Cursor videos = getContentResolver().query(videoSeleccionado, patrones, null, null, null);
                videos.moveToFirst();

                int columnImageIndex = videos.getColumnIndex(patrones[0]);
                String ruta = videos.getString(columnImageIndex);
                videos.close();
                rutaVideo = ruta;

                nombreVideo = rutaVideo.substring(rutaVideo.lastIndexOf("/") + 1);
                nuevoMultimedia(nombreVideo, medioEstado, des, rutaVideo);
            }
        }
    }


    private void nuevoMultimedia(String nombre, String tipo, String descripcion, String ruta) {
        if (Actualizar == true) {
            ModeloMultimedia m = new ModeloMultimedia(nombre, tipo, descripcion, ruta);
            actMultimedias.add(m);
        }
        ModeloMultimedia m = new ModeloMultimedia(nombre, tipo, descripcion, ruta);
        nota.addNotas_multimedia(m);
        tarea.addMultimedia(m);
        adapter = new AdapterMultimedia(nota.getNotas_multimedia(), this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rcvMultimedia.setLayoutManager(manager);
        rcvMultimedia.setItemAnimator(new DefaultItemAnimator());
        rcvMultimedia.setAdapter(adapter);
    }

    @Override
    public void ClicMulti(int position) {
        ArrayList<ModeloMultimedia> recycler = nota.getNotas_multimedia();
        File archivo = new File(recycler.get(position).ruta);
        Uri uri = FileProvider.getUriForFile(this, "com.example.notas.fileprovider", archivo);
        String archivostring = getContentResolver().getType(uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, archivostring);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private void onGrabar(boolean action) {
        if (action) {
            Toast.makeText(this, "GRABANDO. Pulse de nuevo para detener.", Toast.LENGTH_SHORT).show();
            flag = false;
            grabar();
        } else {
            Toast.makeText(this, "GRABACIÓN FINALIZADA", Toast.LENGTH_SHORT).show();
            flag = true;
            this.detenter();
            nuevoMultimedia(audioNombre, "audio", txtDescMultimedia.getText().toString(), audioRuta);
        }

    }

    private void grabar() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        int numero = (int) (Math.random() * (999 - 1 + 1) + 1);
        String hora = new SimpleDateFormat("HHmmss").format(new Date());
        hora += "_" + numero;
        audioNombre = "audio_" + hora + ".3gp";
        File audio = new File(dir, audioNombre);
        audioRuta = dir.getAbsolutePath() + "/" + audioNombre;
        mediaRecorder.setOutputFile(audio.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void detenter() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }


}
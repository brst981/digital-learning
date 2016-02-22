package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.com.digitallearning.Constant.AppConstant;
import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSAdapter;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class ChangePictureFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    ImageView img_edit_icon, img_edit_picture;
    int j;
    AlertDialog alert;
    File destination = null;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static int RESULT_LOAD_IMAGE = 1;
    String picturePath, filePath;
    Uri selectedImage;
    RippleView ripple_change_save;
    ProgressDialog dlg;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_change_pic, container, false);
        dlg=new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);
        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
        img_edit_icon = (ImageView) rootview.findViewById(R.id.img_edit_icon);
        img_edit_picture = (ImageView) rootview.findViewById(R.id.img_edit_picture);
        ripple_change_save=(RippleView)rootview.findViewById(R.id.ripple_change_save);
        ripple_change_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                new Class_image().execute();
            }
        });
        img_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] items = {"Camera", "Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // builder.setTitle("Select");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        // alert.dismiss();
                        if (item == 0) {
                            j = 1;
                            captureImage();

                        }


                        if (item == 1) {
                            j = 2;

                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);

                        } else if (item == 2) {
                            alert.dismiss();
                        }
                    }
                });
                alert = builder.create();
                alert.show();

            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Change Picture");
        return rootview;
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        picturePath = String.valueOf(new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg"));

        destination = new File(picturePath);
        //   long lenght= destination.length();
        //    strlenght = Long.toString(lenght);

        Log.e("FileDestination", "" + destination);
        Log.e("picturePath", "" + picturePath);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            selectedImage = data.getData();

          /*  long lenght= destination.length();
            lenght = lenght/1024;
            Log.e("lenghtgal", "" + lenght);
*/

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);

            picturePath = cursor.getString(columnIndex);
            Log.e("PICTUREPATHgallery", "" + picturePath);

            cursor.close();

            try {

                BitmapFactory.Options options;

                options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Log.e("PICTUREPATHgallery1", "" + picturePath);

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
                bitmap.getScaledHeight(bitmap.getDensity());
                bitmap.getDensity();
                Log.e("bitmap", "" + bitmap.getScaledHeight(bitmap.getDensity()));
                Log.e("getDensity", "" + bitmap.getDensity());
                String filepath = Environment.getExternalStorageDirectory() + picturePath;
                destination = new File(filepath);
                Log.e("filepath", "" + filepath);
                img_edit_picture.setImageBitmap(bitmap);


            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            BitmapFactory.Options options;

            options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap bitmap = BitmapFactory.decodeFile(
                    destination.getAbsolutePath(), options);


            img_edit_picture.setImageBitmap(bitmap);
            Log.e("destinationCamera", "" + destination);

        }
    }

    class Class_image extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return Class_image();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("Class_imageAPI", "" + result);

            if (result.contains("true")) {




            } else if (result.contains("false")) {


            }
        }
    }
    public  String Class_image() {

        String url = AppConstant.class_image;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", cla_classid));
        pairs.add(new BasicNameValuePair("userid", Sch_Mem_id));
        pairs.add(new BasicNameValuePair("cls_image", picturePath));

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }
}
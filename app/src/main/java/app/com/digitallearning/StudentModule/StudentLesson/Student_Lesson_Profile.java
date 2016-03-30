package app.com.digitallearning.StudentModule.StudentLesson;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import app.com.digitallearning.Constant.AppConstant;
import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.PostAPi;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/9/16.
 */
public class Student_Lesson_Profile extends Fragment {
    View rootview;
    TextView headerTitle,txtDate;
    String textHeader;
    ImageButton imageButtonZoomIn, imageButtonZoomOut,back;
    RelativeLayout rel;
    RippleView rippleupdate;
    SharedPreferences preferences;
    String Sch_Mem_id,name;
    ProgressDialog dlg;
    ImageView imgprofilepic;
    String firstname,lastName,email,phonenumber,photo,wechat,whatsapp,description,social_id;
    EditText edtemailid,edtfirstname,edtlastname,edtphonenumber,edtwhatsapp,edtdescription,edtwechat;
    int j;
    public static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_OK = -1;
    Bitmap bitmap = null;
    File destination = null;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String picturePath, filePath;
    File file = null;
    Uri selectedImage;
    AlertDialog alert;
    String updatefirstname,updatelastname,updateemail,updatedescription,updatephonenumber,updatewhatsapp,updatewechat;
    public static Student_Lesson_Profile newInstance() {
        Student_Lesson_Profile mFragment = new Student_Lesson_Profile();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_lesson_profile, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);
        name=preferences.getString("name","");
        Log.e("name",""+name);
        dlg=new ProgressDialog(getActivity());
        rippleupdate=(RippleView)rootview.findViewById(R.id.rippleupdate);
        edtemailid=(EditText)rootview.findViewById(R.id.edtemailid);
        edtfirstname=(EditText)rootview.findViewById(R.id.edtfirstname);
        edtlastname=(EditText)rootview.findViewById(R.id.edtlastname);
        edtphonenumber=(EditText)rootview.findViewById(R.id.edtphonenumber);
        edtwhatsapp=(EditText)rootview.findViewById(R.id.edtwhatsapp);
        edtwechat=(EditText)rootview.findViewById(R.id.edtwechat);
        imgprofilepic=(ImageView)rootview.findViewById(R.id.imgprofilepic);
        edtdescription=(EditText)rootview.findViewById(R.id.edtdescription);

        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton)rootview. findViewById(R.id.img_zoom_out);
        rel=(RelativeLayout)rootview.findViewById(R.id.rel);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        txtDate=(TextView)rootview.findViewById(R.id.txt_date_lesson) ;
        headerTitle.setText("Profile");
        initData();
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1.5f, 1.5f, new PointF(0, 0));
            }
        });

        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
            }
        });

        imgprofilepic.setOnClickListener(new View.OnClickListener() {
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


        new Student_Profile().execute(Sch_Mem_id,name,name);


        rippleupdate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                updatefirstname=edtfirstname.getText().toString();
                updatelastname =edtlastname.getText().toString();
                updateemail=edtemailid.getText().toString();
                updatedescription =edtdescription.getText().toString();
                updatewhatsapp=edtwhatsapp.getText().toString();
                updatewechat=edtwechat.getText().toString();
                updatephonenumber=edtphonenumber.getText().toString();
                new Update_student_profile().execute();
            }
        });
        return rootview;

    }

    private void initData() {
        textHeader ="Profile";




    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rel.setPivotX(pivot.x);
        rel.setPivotY(pivot.y);
        rel.setScaleX(scaleX);
        rel.setScaleY(scaleY);
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
        file = new File(picturePath);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            cursor.close();

            try {
                // hide video preview
                BitmapFactory.Options options;

                options = new BitmapFactory.Options();
                options.inSampleSize = 2;

                bitmap = BitmapFactory.decodeFile(picturePath,options);


                imgprofilepic.setImageBitmap(bitmap);
                Log.e("bitmapmedia",""+bitmap);

               /* File file = new File(picturePath);
                Log.e("file: ", "" + file);*/


                // Bitmap mBitmap_send = StringToBitMap(picturePath);
                // Log.e("mBitmap_send: ", ""+mBitmap_send);
                file = new File(picturePath);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }




        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            BitmapFactory.Options options;

            options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap bitmap = BitmapFactory.decodeFile(
                    destination.getAbsolutePath(), options);

            imgprofilepic.setImageBitmap(bitmap);
            Log.e("bitmapcamera",""+bitmap);

        }
    }



    class Student_Profile extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Student_Info(params[0], params[1]);
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
            Log.e("Student_Profile", "" + result);

            if (result.contains("true")) {
                updateTeacher_getProfile(result);

            } else if (result.contains("false")) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                               /* Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*/

                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
            }
        }
        private void updateTeacher_getProfile(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.getJSONArray("data");

                JSONObject obj;
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);


                    firstname = obj.getString("firstname");
                    lastName = obj.getString("lastName");
                    email = obj.getString("email");
                    photo = obj.getString("photo");
                    description = obj.getString("description");
                    phonenumber = obj.getString("phonenumber");
                    JSONArray arr1 = obj.getJSONArray("social");
                    JSONObject obj1;
                    for (int j = 0; j < arr1.length(); j++) {
                        obj1 = arr1.getJSONObject(j);
                        social_id=obj1.getString("social_id");
                        wechat=obj1.getString("wechat");
                        whatsapp=obj1.getString("whatsapp");
                        edtfirstname.setText(firstname);//+" "+ lastName);
                        edtemailid.setText(email);
                        edtlastname.setText(lastName);
                        edtphonenumber.setText(phonenumber);
                        edtwechat.setText(wechat);
                        edtwhatsapp.setText(whatsapp);
                        edtdescription.setText(description);
                        try {
                            Picasso.with(getActivity()).load(photo).into(imgprofilepic);
                        }
                        catch (Exception e){}


                    }}


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }









    class Update_student_profile extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {


            return uploadFile();
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

            if (result.contains("true")) {

                Toast.makeText(getActivity(),"Profile pic uploaded",Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }

            else  if (result.contains("false")){

                Toast.makeText(getActivity(),"Wrong user",Toast.LENGTH_SHORT).show();

            }
        }
    }


    private String uploadFile() {

        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(AppConstant.student_info);

        PostAPi entity = new PostAPi(getActivity());
//userid, first_name, last_name, email, phone_number, description, mem_image.
        try {

            entity.addPart("userid", new StringBody(Sch_Mem_id));
            Log.e("classid",Sch_Mem_id);
            entity.addPart("first_name", new StringBody(updatefirstname));
            Log.e("first_name",updatefirstname);
            entity.addPart("last_name", new StringBody(updatelastname));
            Log.e("last_name",updatelastname);
            entity.addPart("email", new StringBody(updateemail));
            Log.e("email",updateemail);
            entity.addPart("phone_number", new StringBody(updatephonenumber));
            Log.e("phone_number",updatephonenumber);
            entity.addPart("description", new StringBody(updatedescription));
            Log.e("description",updatedescription);
            entity.addPart("wechat", new StringBody(updatewechat));
            Log.e("wechat",updatewechat);
            entity.addPart("whatsapp", new StringBody(updatewhatsapp));
            Log.e("whatsapp",updatewhatsapp);
            entity.addPart("social_id", new StringBody(social_id));
            Log.e("social_id",social_id);
            entity.addPart("mem_image", new FileBody(file));
            Log.e("mem_image", String.valueOf(file));

        } catch (Exception e) {

        }

        httppost.setEntity(entity);
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                Log.e("statusCode", "" + statusCode);

                responseString = EntityUtils.toString(r_entity);
            } else {
                Log.e("else", "" + statusCode);
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            responseString = e.toString();
        } catch (IOException e) {
            Log.e("Io", "" + e.toString());
            responseString = e.toString();
        }

        Log.e("responseString", "" + responseString);
        return responseString;
    }







}
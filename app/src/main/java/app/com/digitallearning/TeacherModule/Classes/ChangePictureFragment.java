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
import java.util.ArrayList;

import app.com.digitallearning.Constant.AppConstant;
import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

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
    File file = null;
    private static int RESULT_OK = -1;
    Bitmap bitmap = null;
    ArrayList<String> arrimage;
    Uri myUri;
    Boolean b;
    String asd,class_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_change_pic, container, false);
        dlg=new ProgressDialog(getActivity());

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);
        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);


      //  img_edit_picture.setImageResource(R.drawable.no_image_icon);

        arrimage=new ArrayList<>();
        //new Get_Class_image().execute(cla_classid);
        img_edit_icon = (ImageView) rootview.findViewById(R.id.img_edit_icon);
        img_edit_picture = (ImageView) rootview.findViewById(R.id.img_edit_picture);

        try {
            class_image=getArguments().getString("class_image");
            Log.e("receivedclass_image",""+class_image);
            Picasso.with(getActivity()).load(class_image).into(img_edit_picture);
        }
        catch (Exception e){}


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


                img_edit_picture.setImageBitmap(bitmap);
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

            img_edit_picture.setImageBitmap(bitmap);
            Log.e("bitmapcamera",""+bitmap);
            if(bitmap==null) {
                Log.e("vbfh","vdvdhn");
                Picasso.with(getActivity()).load(class_image).into(img_edit_picture);
            }

        }
    }

    /*class Class_image extends AsyncTask<String, Integer, String> {


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

    }*/






    class Class_image extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            // String url = WSConnector.Register(params[0] ,params[1], params[2], params[3], params[4]);
            Log.e("DoinBackground","DoinBackground");

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

// {"result":true,"message":"Registered_Login_successfully~Congratulations. You have Signed up Successfully.","Object":{"id":140102,"full_name":"kiran","profile_pic_small":null,"location":"khanna"}}
//jittigrewal12@gmail.com
//{"result":true,"message":"Registered_Login_successfully~Congratulations. You have Signed up Successfully.","Object":{"id":140103,"full_name":"jot","profile_pic_small":null,"location":"gjs"}}
//jotgrewal131994@gmail.com




                Toast.makeText(getActivity(),"Profile pic uploaded",Toast.LENGTH_SHORT).show();

       /*       try {

                    jsonObject = new JSONObject(result);
                    uid = jsonObject.getString("id");
                    Log.e("result",""+result);
                    Log.e("id",""+uid);
                    String res=jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_SHORT).show();
                    Intent intentGotologin1=new Intent(RegisterActivity.this,LoginwithEmailActivity.class);
                    startActivity(intentGotologin1);

                }catch (Exception e){

                }*/
            }

            else  if (result.contains("false")){

                Toast.makeText(getActivity(),"Email address already exists !!!",Toast.LENGTH_SHORT).show();


            }
        }
    }



    private String uploadFile() {

        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(AppConstant.class_image);

        PostAPi entity = new PostAPi(getActivity());

        try {


            entity.addPart(" classid", new StringBody(cla_classid));
            Log.e("classid",cla_classid);
            entity.addPart("userid", new StringBody(Sch_Mem_id));
            Log.e("userid",Sch_Mem_id);
            entity.addPart("cls_image", new FileBody(file));
      ;  Log.e("cls_image", String.valueOf(file));

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




    class Get_Class_image extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_image(params[0]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("REsulTinAddSchedule", "" + result);
            if (result.contains("true")) {
                updateTeacherLogIn(result);

            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);


                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    Log.e("i",""+i);
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);
                    asd= String.valueOf(obj.isNull("class_image"));
                    Log.e("asd",""+obj.isNull("class_image"));
                     b = Boolean.valueOf(asd);
                    Log.e("b",""+b);
                    if (b==false){
                      // asd= String.valueOf((arrimage.contains("[]")));
                        Log.e("asd",""+obj.isNull("class_image"));
                        img_edit_picture.setImageResource(R.drawable.no_image_icon);
                    }
                    else{
                    String class_image = obj.getString("class_image");
                    Log.e("class_image", "" + class_image);

                        if(class_image!=null){
                     myUri = Uri.parse(class_image);
                    arrimage.add(class_image);
                    Log.e("arrimage",""+arrimage);
                    Log.e("myUri",""+myUri);
                            b=true;
                            Log.e("btrue",""+b);


                    Picasso.with(getActivity()).load(class_image).into(img_edit_picture);}}

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }



}
package com.punuo.sys.app.agedcare.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.punuo.sys.app.agedcare.R;
import com.punuo.sys.app.agedcare.R2;
import com.punuo.sys.app.agedcare.model.Device;
import com.punuo.sys.app.agedcare.sip.BodyFactory;
import com.punuo.sys.app.agedcare.sip.SipDev;
import com.punuo.sys.app.agedcare.sip.SipInfo;
import com.punuo.sys.app.agedcare.sip.SipMessageFactory;
import com.punuo.sys.app.agedcare.sip.SipUser;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.punuo.sys.app.agedcare.sip.SipInfo.devId;
import static com.punuo.sys.app.agedcare.sip.SipInfo.devName;
import static com.punuo.sys.app.agedcare.sip.SipInfo.devices;
import static com.punuo.sys.app.agedcare.sip.SipInfo.groupid;
import static com.punuo.sys.app.agedcare.sip.SipInfo.isanswering;
import static com.punuo.sys.app.agedcare.sip.SipInfo.netuserdevid;
import static com.punuo.sys.app.agedcare.sip.SipInfo.serverIp;
import static com.punuo.sys.app.agedcare.sip.SipInfo.url;
import static com.punuo.sys.app.agedcare.sip.SipInfo.userId;
import static com.punuo.sys.app.agedcare.ui.ChsChange.clusterAdapter;


public class FarmilyFragment extends Fragment implements SipDev.NumberUpdateListener,SipUser.ClusterNotifyListener
{
    private static final String TAG = "FarmilyFragment";
    @BindView(R2.id.icon1)
    ImageView icon1;
    @BindView(R2.id.nickName1)
    TextView nickName1;
    @BindView(R2.id.icon2)
    ImageView icon2;
    @BindView(R2.id.nickName2)
    TextView nickName2;
    @BindView(R2.id.icon3)
    ImageView icon3;
    @BindView(R2.id.nickName3)
    TextView nickName3;
    @BindView(R2.id.icon4)
    ImageView icon4;
    @BindView(R2.id.nickName4)
    TextView nickName4;
    @BindView(R2.id.icon5)
    ImageView icon5;
    @BindView(R2.id.nickName5)
    TextView nickName5;
    @BindView(R2.id.icon6)
    ImageView icon6;
    @BindView(R2.id.nickName6)
    TextView nickName6;
    @BindView(R2.id.icon7)
    ImageView icon7;
    @BindView(R2.id.nickName7)
    TextView nickName7;
    @BindView(R2.id.icon8)
    ImageView icon8;
    @BindView(R2.id.nickName8)
    TextView nickName8;
    int i;
    int a;

//    private ProgressDialog inviting;
    public  String ip = "http://"+serverIp+":8000/static/xiaoyupeihu/";
    ImageView[] icons;
    TextView[] nicknames;

//    String[] url;

//    Bitmap bitmap;
//    Object obj = new Object();
//    public static NameAddress toDev;
//    List<Device> devices;
//    public PictureAdapter pictureAdapter;


        public Handler handler=new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(android.os.Message msg) {


                switch (msg.what) {

                    case 0X222:
                        Log.d(TAG, "handleMessage: " + devices.size());

                        for (i = 0; i < devices.size(); i++) {
                            final int iconorder = i;
                            Glide.with(getActivity()).asBitmap().load(url[iconorder])
                                    .apply(new RequestOptions()
                                    .override(150, 150))
                                    .listener(new RequestListener<Bitmap>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                            devices.get(iconorder).setBitmap(resource);
                                            return false;
                                        }
                                    }).into(icons[iconorder]);

                            icons[iconorder].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isanswering = true;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Looper.prepare();
                                                OkHttpClient client = new OkHttpClient();
                                                final Request request1 = new Request.Builder()
                                                        .url("http://" + serverIp + ":8000/xiaoyupeihu/public/index.php/devs/getUserDevId?id=" + devices.get(iconorder).getId() + "&groupid=" + groupid)
                                                        .build();
                                                netuserdevid = client.newCall(request1).execute().body().string().substring(10, 28);
                                                Log.d("1111", "run: " + netuserdevid);
                                                netuserdevid = netuserdevid.substring(0, netuserdevid.length() - 4).concat("0160");//设备id后4位替换成0160
                                                SipURL sipURL = new SipURL(netuserdevid, serverIp, SipInfo.SERVER_PORT_USER);
                                                SipInfo.toDev = new NameAddress(devName, sipURL);
                                                new Thread() {
                                                    @Override
                                                    public void run() {

                                                        org.zoolu.sip.message.Message request = SipMessageFactory.createNotifyRequest(SipInfo.sipUser, SipInfo.toDev,
                                                                SipInfo.user_from, BodyFactory.createCallRequest("operate", devId, userId));
                                                        SipInfo.sipUser.sendMessage(request);

                                                        Intent intent = new Intent(getActivity(), VedioRequest.class);
                                                        intent.putExtra("iconorder", iconorder);
                                                        startActivity(intent);
                                                    }
                                                }.start();
                                                Looper.loop();
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
//                                       SipCallMananger.getInstance().call(getActivity(), SipInfo.devList.get(iconorder).getId(), true);
                                }
                            });
                            Log.e(TAG,"name:"+i+devices.get(i).getNickname());
                            nicknames[i].setText(devices.get(i).getNickname());
                        }
                        break;
                    case 0X111:
//                        Log.e(TAG,"name:"+i+devices.get(i).getNickname());

                        break;
                    default:
                        break;
                }
                return false;
            }

        });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_farmily, container, false);
        view.setClickable(true);
        ButterKnife.bind(this, view);
        icons=new ImageView[]{icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8};
        nicknames=new TextView[]{nickName1,nickName2,nickName3,nickName4,nickName5,nickName6,nickName7,nickName8};
//        sipUser.setClusterNotifyListener(this);
        SipInfo.sipDev.setNumberUpdateListener(this);

        showPicture();

        return view;
    }

    @Override
    public void onNotify() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!SipInfo.cacheClusters.isEmpty()) {
                    clusterAdapter.appendData(SipInfo.cacheClusters);
                }
            }
        });
    }
    public void showPicture() {
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
//                    Log.d("1111", "run: ");
                    OkHttpClient client = new OkHttpClient();
                    Request request1 = new Request.Builder()
                            .url("http://"+serverIp+":8000/xiaoyupeihu/public/index.php/devs/getDevInfo?devid=" + SipInfo.devId)
                            .build();
                    Log.d("1111", "run: "+client.newCall(request1).execute().body().string());
                    if (client.newCall(request1).execute().body().string().split("\"groupid\":").length>=2) {
                        groupid = client.newCall(request1).execute().body().string().split("\"groupid\":")[1].split(",\"password\"")[0];
                        Log.d("1111", "run: "+groupid);
                    }
                    Request request2 = new Request.Builder()
                            .url("http://"+serverIp+":8000/xiaoyupeihu/public/index.php/groups/getAllUserFromGroup?groupid=" + groupid)
                            .build();
                    Response response = client.newCall(request2).execute();
                    String responseData = response.body().string();
//                    Log.d("1111", "run:1 ");
                    parseJSONWithGSON(responseData);
//                    Log.d("1111", "run:2 "+responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void parseJSONWithGSON(String responseData) {
//        Log.d("1111", "run:3 ");
        String jsonData = "[" + responseData.split("\\[")[1].split("\\]")[0] + "]";

        Gson gson = new Gson();
        devices = gson.fromJson(jsonData, new TypeToken<List<Device>>(){}.getType());
        Log.e("showPicture", devices+"");
        SipInfo.devList.addAll(devices);
        url = new String[devices.size()];
        int i=0;
        for ( Device device : devices) {
            a=0;
            url[i++] = ip + device.getId() + "/" + device.getAvatar();

            Log.d(TAG,url[i-1]);
            handler.sendEmptyMessage(0X222);
        }
        if (!devices.isEmpty()){
            handler.sendEmptyMessage(0X111);
        }
    }
    @Override
    public void numberUpdate() {
          showPicture();

    }
}
package com.bolink;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.bolink.base.BaseActivity;
import com.bolink.bean.BannerUrls;
import com.bolink.bean.Download;
import com.bolink.bean.Messages;
import com.bolink.bean.PrintMsg;
import com.bolink.bean.UpdateInfo;
import com.bolink.bean.VideoResult;
import com.bolink.customview.DownloadDialog;
import com.bolink.customview.ToastDialog;
import com.bolink.hardware.AndroidRom;
import com.bolink.hardware.MoneyPaperUtil;
import com.bolink.hardware.PrintComUtil;
import com.bolink.hardware.PrintUtil;
import com.bolink.hardware.ScanUtilUSB;
import com.bolink.method.AndroidMethod;
import com.bolink.method.JsMethod;
import com.bolink.receiver.IncomingCallReceiver;
import com.bolink.retrofit.HttpApi;
import com.bolink.retrofit.ToByteConvertFactory;
import com.bolink.retrofit.ToStringConverterFactory;
import com.bolink.retrofit.UpdataInfoParser;
import com.bolink.rx.RxBus;
import com.bolink.service.DownloadService;
import com.bolink.utils.CommontUtils;
import com.bolink.utils.SipUtil;
import com.bolink.utils.StringUtils;
import com.bolink.utils.TimeUtils;
import com.bolink.utils.ViewUtils;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import device.itl.sspcoms.DeviceEvent;
import device.itl.sspcoms.DeviceEventType;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.bolink.bean.Messages.CALL_ENDED;
import static com.bolink.bean.Messages.CALL_ESTABLISHED;
import static com.bolink.bean.Messages.CALL_HOST;
import static com.bolink.bean.Messages.CALL_RECEIVED;
import static com.bolink.bean.Messages.CHECK_UPDATE;
import static com.bolink.bean.Messages.DOWNLOAD_PROGRESS_APK;
import static com.bolink.bean.Messages.IDENTIFY_PWD_N;
import static com.bolink.bean.Messages.IDENTIFY_PWD_Y;
import static com.bolink.bean.Messages.MacAddress;
import static com.bolink.bean.Messages.PAPER_MONEY;
import static com.bolink.bean.Messages.PRINT_MSG;
import static com.bolink.bean.Messages.PRINT_TICKET;
import static com.bolink.bean.Messages.QUERRY_ADVANCE;
import static com.bolink.bean.Messages.QUERRY_ADVANCE_ABNORMAL;
import static com.bolink.bean.Messages.RELOAD_WEBVIEW;
import static com.bolink.bean.Messages.SCAN_CLOSE;
import static com.bolink.bean.Messages.SCAN_MSG;
import static com.bolink.bean.Messages.SCAN_OPEN;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public static final String MESSAGE_PROGRESS = "message_progress";
    //    @BindView(R.id.main_web)
    WebView webView;
    Button button;
    VideoView videoView;
//    CircleProgressBar progressbar;
    AndroidMethod androidMethod;
    JsMethod jsMethod;
    public static final int WRITE_EXTERNAL_STORAGE = 0x600001;
    ToastDialog dialog;
    DownloadDialog downloadDialog;
    public static String MacAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        MacAdress = CommontUtils.getMacAddress(this);
        webView = ((WebView) findViewById(R.id.webView));
        videoView = ((VideoView) findViewById(R.id.videoView));
        FrameLayout.LayoutParams params = ((FrameLayout.LayoutParams) videoView.getLayoutParams());
        params.width = ViewUtils.getScreenWidth(this);
        params.height = ViewUtils.getVideoHeight(this);
        videoView.setLayoutParams(params);
//        progressbar = ((CircleProgressBar) findViewById(R.id.progressbar));
        button = ((Button) findViewById(R.id.button));
        button.setOnClickListener(v -> {
//            jsMethod.NotifyNetState(false);
//            RxBus.get().post(new Messages(Messages.NET_DENY, null));
//            initVideo();
            CheckUpdate(CommontUtils.getVersion(this));
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                //没有授予权限，需要申请
//                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    Toast.makeText(MainActivity.this, "程序运行需要写内存权限，否则将推出程序!", Toast.LENGTH_LONG).show();
//                } else {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
//                }
//            }
//        }
        SQLiteDatabase db = LitePal.getDatabase();
        androidMethod = new AndroidMethod(MainActivity.this, db);
        jsMethod = new JsMethod(webView);

        String a = "123";
        Integer.parseInt(a);
//        Orders order1 = new Orders();
//        order1.setOrderId("1");
//        order1.setCarNumber("11111");
//        order1.setCash1(1);
//        order1.setFinishPay(true);
//        order1.save();
//
//        Orders order2 = new Orders();
//        order2.setOrderId("2");
//        order2.setCarNumber("2222");
//        order2.setCash1(2);
//        order2.setFinishPay(false);
//        order2.save();
//
//        Orders order3 = new Orders();
//        order3.setOrderId("3");
//        order3.setCarNumber("3333");
//        order3.setCash1(3);
//        order3.setFinishPay(false);
//        order3.save();

//        List<Orders> orders = DataSupport.findAll(Orders.class);
//        List<Orders> orders = DataSupport.where("finishpay like ?", "0").find(Orders.class);
//        Log.e(TAG, "orders.size=" + orders.size());


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setAppCacheEnabled(true); //启用应用缓存
//        settings.setDomStorageEnabled(true); //启用或禁用DOM缓存。
//        settings.setDatabaseEnabled(true); //启用或禁用DOM缓存。
//        settings.setAppCacheMaxSize(1024*1024*100);
//        settings.setAppCachePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        if (SystemUtil.isNetworkConnected()) { //判断是否联网
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT); //默认的缓存使用模式
//        } else {
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY); //不从网络加载数据，只从缓存加载数据。
//        }
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        if (Build.VERSION.SDK_INT < 8) {
//            settings.setPluginsEnabled(true);
        } else {
            settings.setPluginState(WebSettings.PluginState.ON);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:onPageFinished();");

//                jsMethod.getMacAddress(CommontUtils.getMacAddress(MainActivity.this));
            }

//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                return super.shouldInterceptRequest(view, request);
//            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }


        });
        webView.addJavascriptInterface(androidMethod, "AndroidMethod");
        webView.loadUrl(getResources().getString(R.string.web_url));
//        webView.loadUrl("file:///android_asset/index.html");
//        webView.loadUrl("https://tryit.jssip.net/");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initRxbus();
        initVideo();
//        initSip();
//        AndroidRom.get().LockBar(MainActivity.this);


        findViewById(R.id.currentversion).setOnClickListener(v -> Toast.makeText(MainActivity.this, "当前版本:" + CommontUtils.getVersion(this), Toast.LENGTH_LONG).show());

        findViewById(R.id.buttoncash).setOnClickListener(v -> MoneyPaperUtil.get().init(MainActivity.this));
        findViewById(R.id.buttoncashno).setOnClickListener(v -> MoneyPaperUtil.get().Close());

        findViewById(R.id.buttonprint).setOnClickListener(v -> PrintComUtil.get().init(MainActivity.this));
        findViewById(R.id.buttonprint2).setOnClickListener(v -> PrintComUtil.get().print());

        findViewById(R.id.buttonscan).setOnClickListener(v -> ScanUtilUSB.get().init());
        findViewById(R.id.buttonscanopen).setOnClickListener(v -> ScanUtilUSB.get().open());

        findViewById(R.id.buttonhidestatus).setOnClickListener(v -> AndroidRom.get().LockBar(MainActivity.this));
        findViewById(R.id.buttonshowstatus).setOnClickListener(v -> AndroidRom.get().UnLockBar(MainActivity.this));

        textView = ((EditText) findViewById(R.id.sweepcode));
        textView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e(TAG, "onKey: keycode=" + keyCode);
                return false;
            }
        });
        findViewById(R.id.btnsweepcode).setOnClickListener(v -> {
            String code = textView.getText().toString().trim();
            jsMethod.ScanResult(code);
        });
        List<BannerUrls> lists = new ArrayList<>();
        lists.add(new BannerUrls("https://www.bolink.club/PaymentMachine/img/left_1_c.png"));
        lists.add(new BannerUrls("https://www.bolink.club/PaymentMachine/img/left_2_c.png"));
        lists.add(new BannerUrls("https://www.bolink.club/PaymentMachine/img/left_3_c.png"));
        lists.add(new BannerUrls("https://www.bolink.club/PaymentMachine/img/left_4_c.png"));
        findViewById(R.id.banner).setOnClickListener(v -> jsMethod.updateSwiper(lists));


        dialog = new ToastDialog(MainActivity.this, R.style.toastdialog);
        dialog.setCancelable(false);//点击其他区域不消失
        downloadDialog = new DownloadDialog(MainActivity.this, R.style.downloaddialog);
        downloadDialog.setCancelable(false);

        PrintComUtil.get().init(MainActivity.this);
        ScanUtilUSB.get().init();

        CommontUtils.writeSDFile("clear", null);

    }


    private void initLogin() {
        //登录接口，写好了待用
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(new ToStringConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpApi httpApi = retrofit.create(HttpApi.class);
        httpApi.rxLogin("2003", "123")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {

                });
    }

    public void CheckUpdate(String versionCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.update_url))
                .addConverterFactory(new ToByteConvertFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpApi httpApi = retrofit.create(HttpApi.class);
        httpApi.rxCheckUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> {
                    if (bytes != null) {
                        InputStream inputStream = new ByteArrayInputStream(bytes);
                        UpdateInfo info = UpdataInfoParser.getUpdataInfo(inputStream);
                        inputStream.close();
                        if (Integer.parseInt(versionCode) < Integer.parseInt(info.getVersion())) {
//                            RxBus.get().post(new Messages(PRINT_MSG, "needupdate"));
                            File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getResources().getString(R.string.apk_name));
//                            Log.e(TAG, "download: updateFile=" + updateFile);
                            if (updateFile.exists()) {
                                install(updateFile);
                            } else {
                                startDownLoadUpdate(info.getApkurl());
                            }
                        } else {
                            RxBus.get().post(new Messages(PRINT_MSG, getResources().getString(R.string.msg_alreadylatest)));
                        }
                    }
                });
    }

    // 安装apk；
    private void install(File file) {
//        Constant.ISNEEDBACKUP = false;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void initRxbus() {
        RxBus.get().toFlowable(Messages.class).onBackpressureDrop().observeOn(AndroidSchedulers.mainThread()).subscribe(messages -> {
            switch (messages.getCode()) {
                case Messages.NET_ACCESS:
                    //网络畅通
                    jsMethod.NotifyNetState(true);
                    //webView.reload();
                    break;
                case Messages.NET_DENY:
                    //网络堵塞
                    jsMethod.NotifyNetState(false);
                    break;
                case DOWNLOAD_PROGRESS_APK:

                    break;
                case Messages.DOWNLOAD_PROGRESS:
                    //获取下载进度
                    Download download = (Download) messages.getMsg();
//                    progressbar.setProgress(download.getProgress());

                    if (download.getProgress() == 100 && download.getCurrentFileSize() == download.getTotalFileSize()) {
//                        progressbar.setVisibility(View.GONE);
                        if (download.getURI().contains(".apk")) {
                            //下载的apk
                            downloadDialog.dismiss();
                            install(new File(download.getURI()));
                        } else {
                            //下载的视频
                            Uri videopath = Uri.parse("file://" + download.getURI());
                            if (!PlayList.contains(videopath)) {
                                PlayList.add(videopath);
                                if (PlayList.size() < 2)
                                    startPlay();
                                downLoadIndex++;
                                startDownLoad();
                            }
                        }

                    } else {
                        if (download.getURI().contains(".apk")) {
                            //下载的apk
                            downloadDialog.setProgress(download.getProgress());
                        } else {
                            button.setText(
                                    StringUtils.getDataSize(download.getCurrentFileSize())
                                            + "/" +
                                            StringUtils.getDataSize(download.getTotalFileSize()));
                        }

                    }
                    break;
                case QUERRY_ADVANCE:
                    //高级登录显示钱
                    jsMethod.QuerryAdavance((String) messages.getMsg());
                    break;
                case QUERRY_ADVANCE_ABNORMAL:
                    //查询异常订单-零钱未充值
                    jsMethod.QuerryAdavanceAbNormal((String) messages.getMsg());
                    break;
                case CALL_HOST:
                    //呼叫总机
                    sipUtil.sipAddress = getResources().getString(R.string.sip_host);
                    sipUtil.initiateCall();
                    break;
                case CALL_ESTABLISHED:
                case CALL_RECEIVED:
                    jsMethod.NotifyCallStatus("通话中");
                    IsCalling = true;
                    if (CallCount <= 0) {
                        compositeDisposable = new CompositeDisposable();
                        compositeDisposable.add(Flowable.interval(1, TimeUnit.SECONDS)
                                .onBackpressureDrop()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> {
                                    if (!IsCalling)
                                        compositeDisposable.dispose();
                                    else
                                        jsMethod.NotifyCallStatus("通话中" + TimeUtils.Sec2Time(aLong));
                                }));
                    }
                    CallCount++;
                    break;
                case CALL_ENDED:
                    IsCalling = false;
                    jsMethod.NotifyCallStatus("呼叫总机");
                    CallCount = 0;
                    break;
                case IDENTIFY_PWD_Y:
                    jsMethod.NotifyCheckPwd(1);
                    break;
                case IDENTIFY_PWD_N:
                    jsMethod.NotifyCheckPwd(0);
                    break;
                case PAPER_MONEY:
                    DeviceEvent ev = (DeviceEvent) messages.getMsg();
                    if (DeviceEventType.BillCredit.equals(ev.event)) {
                        jsMethod.getMoney(ev.value);
                        Toast.makeText(MainActivity.this, "get money:" + ev.value, Toast.LENGTH_LONG).show();
                    }
                    break;
                case SCAN_MSG:
//                    Toast.makeText(MainActivity.this, "scan result:" + messages.getMsg(), Toast.LENGTH_LONG).show();
                    jsMethod.ScanResult((String) messages.getMsg());
                    break;
                case PRINT_MSG:
//                    Toast.makeText(MainActivity.this, "print msg:" + messages.getMsg(), Toast.LENGTH_LONG).show();
                    dialog.show();
                    dialog.setText((String) messages.getMsg());
                    new Handler().postDelayed(() -> {
                        dialog.cancel();
                    }, 2500);
                    break;
                case RELOAD_WEBVIEW:
                    webView.reload();
                    break;
                case PRINT_TICKET:
                    PrintMsg msg = (PrintMsg) messages.getMsg();
                    PrintComUtil.get().PrintTicket(msg.getTitle(), msg.getContent(), msg.getTale());
                    break;
                case SCAN_OPEN:
                    ScanUtilUSB.get().open();
                    break;
                case SCAN_CLOSE:
                    ScanUtilUSB.get().close();
                    break;
                case MacAddress:
                    jsMethod.getMacAddress((String) messages.getMsg());
                    break;
                case CHECK_UPDATE:
                    CheckUpdate(CommontUtils.getVersion(this));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AndroidRom.get().LockBar(MainActivity.this);
//        startPlay();
        videoView.setOnCompletionListener(mediaPlayer -> {
            Log.e(TAG, "VVVideo播放完了: " + current);
            startPlay();
        });
        if (!videoView.isPlaying()) {
            startPlay();
        }
    }

    private void initVideo() {
        Gson g = new Gson();
        videoResult = g.fromJson(requestResult, VideoResult.class);
        if (videoResult.getVideoList().size() > 0) {
            for (VideoResult.videoDetail detail : videoResult.getVideoList()) {
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), detail.getFileName());
                if (!f.exists() && !detail.isDel()) {
                    details.add(detail);
                } else {
                    if (detail.isDel()) {
                        f.delete();
                    } else {
                        Uri uri = Uri.parse("file://" + f.getAbsolutePath());
                        if (!PlayList.contains(uri)) {
                            PlayList.add(uri);
                        }
                    }
                }
            }
            startPlay();
            startDownLoad();

        }
//        startPlay();
//        videoView.setOnCompletionListener(mediaPlayer -> {
//            Log.e(TAG, "VVVideo播放完了: " + current);
//            startPlay();
//        });
    }

    EditText textView;
    private CompositeDisposable compositeDisposable;
    private boolean IsCalling;
    private int CallCount = 0;
    List<Uri> PlayList = new ArrayList<>();
    int current = 0;
    //    String requestResult = "{\n" +
//            "    \"state\":1,\n" +
//            "    \"errmsg\":\"\",\n" +
//            "    \"videoList\":[\n" +
//            "        {\n" +
////            "            \"videoUrl\":\"http://vjs.zencdn.net/v/oceans.mp4\",\n" +
//            "            \"videoUrl\":\"http://pcvideogs.titan.mgtv.com/c1/2017/09/15_0/300367A09FF6BAD65454D1869EBFA066_20170915_1_1_443.mp4?arange=0&pm=fTuEYhE3xHJHL4J0tyx8NTxC9wvTaxPdMaEi8q8fm~W0rzk80438RKzxPAdK~CoQ39mGgb9gAGDH1lhDQ2AfdoEhLAlfVi1~2AZ7G3i96KP_V8C49eRj6aD6rXYStArhUoI_L7a3~SGOjYVVBv~t8GWiK4HCPTnAjj4YNCFshS1IEPvoD8gH4NkGW3Y0qmDyKeqX~0LtvTmeU36LGXFa2uKiwPZ7teynVqhXioeNUbL6~ai0cieVDrR7Bo2KNR90C~28iFl_eon8Y~gHtTsoWeeeVrm_npPWxsdvNufxWSheXQmmGERO4e7KNraj~gxxksemdT0iw4jAaouO2GbIYdScdSm_DmgDIYPYr7fZKQR6cwIqQFD3DYuvb_npi4f_9ac~HKjx_d5XQQ6q\",\n" +
//            "            \"fileName\":\"video3.mp4\",\n" +
//            "            \"del\":false\n" +
//            "        },\n" +
//            "        {\n" +
////            "            \"videoUrl\":\"http://www.w3school.com.cn/example/html5/mov_bbb.mp4\",\n" +
//            "            \"videoUrl\":\"http://vjs.zencdn.net/v/oceans.mp4\",\n" +
//            "            \"fileName\":\"video4.mp4\",\n" +
//            "            \"del\":false\n" +
//            "        }\n" +
//            "    ]\n" +
//            "}";
    String requestResult = "{\n" +
            "    \"state\":1,\n" +
            "    \"errmsg\":\"\",\n" +
            "    \"videoList\":[\n" +
            "        {\n" +
            "            \"videoUrl\":\"https://www.bolink.club/data/video/001.mp4\",\n" +
            "            \"fileName\":\"001.mp4\",\n" +
            "            \"del\":false\n" +
            "        },\n" +
            "        {\n" +
            "            \"videoUrl\":\"https://www.bolink.club/data/video/002.mp4\",\n" +
            "            \"fileName\":\"002.mp4\",\n" +
            "            \"del\":false\n" +
            "        },\n" +
            "        {\n" +
            "            \"videoUrl\":\"https://www.bolink.club/data/video/003.mp4\",\n" +
            "            \"fileName\":\"003.mp4\",\n" +
            "            \"del\":false\n" +
            "        },\n" +
            "        {\n" +
            "            \"videoUrl\":\"https://www.bolink.club/data/video/004.mp4\",\n" +
            "            \"fileName\":\"004.mp4\",\n" +
            "            \"del\":false\n" +
            "        },\n" +
            "        {\n" +
            "            \"videoUrl\":\"https://www.bolink.club/data/video/005.mp4\",\n" +
            "            \"fileName\":\"005.mp4\",\n" +
            "            \"del\":false\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    VideoResult videoResult;
    List<VideoResult.videoDetail> details = new ArrayList<>();
    int downLoadIndex = 0;

    private void startPlay() {
        Log.e(TAG, "VVVideo-startPlay: " + PlayList.size() + videoView.isPlaying());
        if (PlayList.size() > 0) {
//            if (videoView.isPlaying()) {
//                return;
//            }
            if (current < PlayList.size() - 1) {
                current++;
            } else {
                current = 0;
            }
//            File file = new File(String.valueOf(PlayList.get(current)));
//            Log.e(TAG, "startPlay: file.exists()"+file.exists()+"   file.length()"+file.length());
            videoView.setVideoURI(Uri.parse("file://" + PlayList.get(current)));
            Log.e(TAG, "VVVideo开始播放: " + current);
            videoView.start();
        }
    }

    private void startDownLoad() {
        if (details.size() > 0 && downLoadIndex < details.size()) {
//            progressbar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, DownloadService.class);
            intent.putExtra("type", "video");
            intent.putExtra("videoUrl", details.get(downLoadIndex).getVideoUrl());
            intent.putExtra("fileName", details.get(downLoadIndex).getFileName());
            startService(intent);
        }
    }

    private void startDownLoadUpdate(String url) {
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.putExtra("type", "apk");
        intent.putExtra("videoUrl", url);
        intent.putExtra("fileName", getResources().getString(R.string.apk_name));
        startService(intent);
        downloadDialog.show();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case WRITE_EXTERNAL_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    Toast.makeText(MainActivity.this, "未获取写内存权限！", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//                break;
//        }
//    }

    public static SipUtil sipUtil;

    private void initSip() {

        sipUtil = new SipUtil(MainActivity.this);
        //动态注册的目的是让 reveiver 中获取的context可以强转为 MainActivity
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        sipUtil.callReceiver = new IncomingCallReceiver();
        this.registerReceiver(sipUtil.callReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        AndroidRom.get().UnLockBar(MainActivity.this);
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
//        sipUtil.destroy();
//        sipUtil = null;
        MoneyPaperUtil.get().Close();
        MoneyPaperUtil.get().Destroy();
        PrintUtil.get().Destroy();
//        ScanUtil.get().Destroy();
        ScanUtilUSB.get().Destroy();
    }

    long currentmil = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentmil > 1000) {
                currentmil = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

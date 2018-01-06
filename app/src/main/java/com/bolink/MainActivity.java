package com.bolink;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
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
import com.bolink.bean.IntevalResult;
import com.bolink.bean.Messages;
import com.bolink.bean.PrintMsg;
import com.bolink.bean.Users;
import com.bolink.bean.VideoUrls;
import com.bolink.customview.DownloadDialog;
import com.bolink.customview.ToastDialog;
import com.bolink.customview.mCycleViewPager;
import com.bolink.hardware.AndroidRom;
import com.bolink.hardware.LinePhoneUtil;
import com.bolink.hardware.MoneyPaperUtil;
import com.bolink.hardware.PrintComUtil;
import com.bolink.hardware.PrintUtil;
import com.bolink.hardware.ScanUtilUSB;
import com.bolink.hardware.Sip.SipManager;
import com.bolink.method.AndroidMethod;
import com.bolink.method.JsMethod;
import com.bolink.process.ProcessService;
import com.bolink.retrofit.HttpApi;
import com.bolink.retrofit.ToStringConverterFactory;
import com.bolink.rx.RxBus;
import com.bolink.service.DownloadMethod;
import com.bolink.utils.CommontUtils;
import com.bolink.utils.SharedPreferenceUtil;
import com.bolink.utils.SipUtil;
import com.bolink.utils.StringUtils;
import com.bolink.utils.TimeUtils;
import com.bolink.utils.UpdateUtil;
import com.bolink.utils.ViewUtils;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import static com.bolink.bean.Messages.CALL_HOST_LINEPHONE;
import static com.bolink.bean.Messages.CALL_RECEIVED;
import static com.bolink.bean.Messages.CHECK_UPDATE;
import static com.bolink.bean.Messages.CLEAR_CACHE;
import static com.bolink.bean.Messages.DOWNLOAD_PROGRESS_APK;
import static com.bolink.bean.Messages.IDENTIFY_PWD_N;
import static com.bolink.bean.Messages.IDENTIFY_PWD_Y;
import static com.bolink.bean.Messages.JS_LOADING_CANCEL;
import static com.bolink.bean.Messages.JS_LOADING_SHOW;
import static com.bolink.bean.Messages.LINEPHONE_MSG;
import static com.bolink.bean.Messages.LOADING_SHOW;
import static com.bolink.bean.Messages.MacAddress;
import static com.bolink.bean.Messages.NOT_REPORT_ORDERS;
import static com.bolink.bean.Messages.PAPER_MONEY;
import static com.bolink.bean.Messages.PAPER_MONEY_OPEN;
import static com.bolink.bean.Messages.PRINT_MSG;
import static com.bolink.bean.Messages.PRINT_TICKET;
import static com.bolink.bean.Messages.QUERRY_ADVANCE;
import static com.bolink.bean.Messages.QUERRY_ADVANCE_ABNORMAL;
import static com.bolink.bean.Messages.RELOAD_WEBVIEW;
import static com.bolink.bean.Messages.SCAN_CLOSE;
import static com.bolink.bean.Messages.SCAN_MSG;
import static com.bolink.bean.Messages.SCAN_OPEN;
import static com.bolink.bean.Messages.SCAN_OPEN_RESULT;
import static com.bolink.bean.Messages.SCAN_OPEN_RESULT_BEFORE;
import static com.bolink.bean.Messages.VERSION_CODE;
import static com.bolink.utils.CommontUtils.setVolume;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public static final String MESSAGE_PROGRESS = "message_progress";
    private int SCAN_OPEN_RESULT_int = 0;
    private int PRINT_MSG_int = 0;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.videocontain)
    FrameLayout videocontain;
    @BindView(R.id.viewpager)
    FrameLayout viewPager;

    //    CircleProgressBar progressbar;
    AndroidMethod androidMethod;
    JsMethod jsMethod;

    Button button;
    public static final int WRITE_EXTERNAL_STORAGE = 0x600001;
    DownloadDialog downloadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Timeline", "before super " + System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        Log.i("Timeline", "after super " + System.currentTimeMillis());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        startActivity(new Intent());
        ViewUtils.setLayoutParams(videocontain, this);
        ViewUtils.setLayoutParams(viewPager, this);

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

        LinePhoneUtil.get().init(this);
        androidMethod = new AndroidMethod(MainActivity.this);
        jsMethod = new JsMethod(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 8) {
//            settings.setPluginsEnabled(true);
        } else {
            settings.setPluginState(WebSettings.PluginState.ON);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                initPost();
                jsMethod.getCheckStatus(SharedPreferenceUtil.get(MainActivity.this).getBoolean("BarStatus", false));
                jsMethod.CurrentVersion(CommontUtils.getVersionName(MainActivity.this));
                RxBus.get().post(new Messages(VERSION_CODE, null));
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String url = request.getUrl().toString();
//                return super.shouldOverrideUrlLoading(view, request);
//            }
        });
        webView.addJavascriptInterface(androidMethod, "AndroidMethod");
        webView.loadUrl(getResources().getString(R.string.web_url));

//        webView.loadUrl("file:///android_asset/index.html");
//        webView.loadUrl("https://tryit.jssip.net/");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initRxbus();
        initVideo();

        //116版本注释了，117版本又不注释了。忘了为什么这么做了。。。
        downloadDialog = new DownloadDialog(MainActivity.this, R.style.downloaddialog);
        downloadDialog.setCancelable(false);
//        initSip();
//        AndroidRom.get().LockBar(MainActivity.this);
        initTests();
        Log.i("Timeline", "crate finish " + System.currentTimeMillis());
    }


    private void initPost() {
        //不太着急的初始化放这里
//        initVideo();

        new Thread(() -> {
            MoneyPaperUtil.get().init(MainActivity.this);
            PrintComUtil.get().init(MainActivity.this);
            CommontUtils.writeSDFile("clear", null);
            File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getResources().getString(R.string.apk_name));
            if (updateFile.exists()) {
                updateFile.delete();
            }
            MoneyPaperUtil.get().Close();

            String msg = ScanUtilUSB.get().init(MainActivity.this, null);
            if (msg.contains("success")) {
                ScanUtilUSB.get().open();
                ScanUtilUSB.get().close();
            } else {
                msg = ScanUtilUSB.get().init(MainActivity.this, null);
                if (msg.contains("success")) {
                    ScanUtilUSB.get().open();
                    ScanUtilUSB.get().close();
                } else {
                    msg = ScanUtilUSB.get().init(MainActivity.this, null);
                    if (msg.contains("success")) {
                        ScanUtilUSB.get().open();
                        ScanUtilUSB.get().close();
                    } else {
                        //尝试开启3次，再不行也就不管了

                    }
                }
            }
            RxBus.get().post(new Messages(SCAN_OPEN_RESULT, msg));

            if (!CommontUtils.IsProcessRunning(getResources().getString(R.string.target_package_name), MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, ProcessService.class);
                startService(intent);
            }
        }).start();

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


    private void initRxbus() {
        RxBus.get().toFlowable(Messages.class).onBackpressureDrop().observeOn(AndroidSchedulers.mainThread()).subscribe(messages -> {
            switch (messages.getCode()) {
                case Messages.NET_ACCESS:
                    //网络畅通
                    jsMethod.NotifyNetState(true);
                    CommontUtils.writeSDFile("nnnnnnnnnnnnnnnnnnetstate", "NET_ACCESS");
                    //webView.reload();
                    break;
                case Messages.NET_DENY:
                    //网络堵塞
                    jsMethod.NotifyNetState(false);
                    CommontUtils.writeSDFile("nnnnnnnnnnnnnnnnnnetstate", "NET_DENY");
                    break;
                case DOWNLOAD_PROGRESS_APK:

                    break;
                case Messages.DOWNLOAD_PROGRESS:
                    //获取下载进度
                    DownLoadProcess(messages);
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
//                case REGIS_LINEPHONE:
//                    break;
                case CALL_HOST_LINEPHONE:
                    new Thread(() -> {
                        LinePhoneUtil.callLinePhone();
                    }).start();

                    break;
                case LINEPHONE_MSG:
                    String linephonemsg = ((String) messages.getMsg());
                    switch (linephonemsg) {
                        case SipManager.SIP_RINGING:
                            jsMethod.NotifyCallStatus(getResources().getString(R.string.Calling));
                            break;
                        case SipManager.SIP_INCOMMING:
//                            break;
                        case SipManager.SIP_TALKING:
                            IsCalling = true;
                            LinePhoneUtil.LinePhoneTimeCount(IsCalling, jsMethod, getResources().getString(R.string.Talking));
                            CommontUtils.setVolumePhoneType(this);
                            break;
                        case SipManager.SIP_TERMINATE:
                            IsCalling = false;
                            LinePhoneUtil.LinePhoneTimeCount(IsCalling, jsMethod, getResources().getString(R.string.Talking));
                            jsMethod.NotifyCallStatus(getResources().getString(R.string.CallHost));
                            LinePhoneUtil.initLinePhone();
                            CommontUtils.setVolumeNormalType(this);
                            break;
                    }
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
//                        Toast.makeText(MainActivity.this, "get money:" + ev.value, Toast.LENGTH_LONG).show();
                    }
                    break;
                case SCAN_MSG:
                    jsMethod.ScanResult((String) messages.getMsg());
                    break;
                case PRINT_MSG:
//                    Toast.makeText(MainActivity.this,"PPPPPPPPrint>>>"+messages.getMsg(),Toast.LENGTH_LONG).show();
                    //这个方法也用来做js里的toast
                    ToastDialog.makeText(this, (String) messages.getMsg(), 0).show_D();
                    break;
                case RELOAD_WEBVIEW:
                    webView.reload();
                    break;
                case PRINT_TICKET:
                    PrintComUtil.get().PrintTicket((PrintMsg) messages.getMsg());
                    break;
                case SCAN_OPEN:
                    if (openScanResult.contains("success")) {
                        ScanUtilUSB.get().open();
                    } else {
//                        ScanUtilUSB.get().init(MainActivity.this);
//                        ScanUtilUSB.get().open();
                        ScanUtilUSB.get().initopen(MainActivity.this);
                    }
                    break;
                case SCAN_OPEN_RESULT:
                    openScanResult = (String) messages.getMsg();
                    if (SCAN_OPEN_RESULT_int > 0 && !openScanResult.contains("success")) {
                        ToastDialog.makeText(this, openScanResult, 0).show_D();
                    }
                    SCAN_OPEN_RESULT_int++;
                    jsMethod.HideLoading(openScanResult);
                    break;
                case SCAN_OPEN_RESULT_BEFORE:
//                    Toast.makeText(this,"发送消息，开始loading",Toast.LENGTH_LONG).show();
                    if (SCAN_OPEN_RESULT_int > 0)
                        jsMethod.ShowLoading("");
                    break;
                case PAPER_MONEY_OPEN:
                    ToastDialog.makeText(this, (String) messages.getMsg(), 0).show_D();
                    break;
                case SCAN_CLOSE:
                    ScanUtilUSB.get().close();
                    break;
                case MacAddress:
                    String macadress = (String) messages.getMsg();
                    jsMethod.getMacAddress(macadress);
                    //自动登录
                    List<Users> usersList = DataSupport.findAll(Users.class);
//                    CommontUtils.writeSDFile("userlist size ", usersList.size() + "");
                    if (null != usersList && usersList.size() > 0) {
                        Users users = usersList.get(0);
                        CommontUtils.writeSDFile("user content", users.toString());
                        jsMethod.AutoLogin(users.getAccount(), users.getPassword(), macadress);
                    }
                    break;
                case CHECK_UPDATE:
                    UpdateUtil.CheckUpdate(CommontUtils.getVersion(this), this);
                    break;
                case LOADING_SHOW:
                    downloadDialog.show();
                case JS_LOADING_SHOW:
                    jsMethod.ShowLoading("");
                    break;
                case JS_LOADING_CANCEL:
                    jsMethod.HideLoading("");
                    break;
                case CLEAR_CACHE:
                    webView.clearCache(true);
                    webView.clearHistory();
                    break;
                case NOT_REPORT_ORDERS:
                    String json = ((String) messages.getMsg());
                    jsMethod.NotReportOrders(json);
                    break;
                case VERSION_CODE:
                    String vc = CommontUtils.getVersion(this);
                    if (vc != null && !vc.equals(""))
                        jsMethod.getVersionCode(Integer.parseInt(vc));
                    break;
                default:
                    break;
            }
        });
    }

    String openScanResult = "";

    @Override
    protected void onResume() {
        super.onResume();
//        AndroidRom.get().LockBar(MainActivity.this);
        videoView.setOnCompletionListener(mediaPlayer -> {
            Log.e(TAG, "VVVideo play over : " + current);
            startPlay();
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                CommontUtils.writeSDFile("Media Error:", "waht:" + what + ",extra:" + extra);
                startPlay();
                return true;
            }
        });
        if (!videoView.isPlaying()) {
            startPlay();
        }
    }

    List<File> imgPlayList = new ArrayList<>();//图片播放列表
    List<BannerUrls> imgDownLoadList = new ArrayList<>();//图片下载列表

    private void initVideo() {
        Gson g = new Gson();
        intevalResult = g.fromJson(Messages.requestResult, IntevalResult.class);
        if (intevalResult.getImgList().size() > 0) {
            //初始化图片轮播
            for (BannerUrls detail : intevalResult.getImgList()) {
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), detail.getFileName());
                if (!f.exists() && !detail.isDel()) {
                    imgDownLoadList.add(detail);
                } else {
                    if (detail.isDel()) {
                        f.delete();
                        imgPlayList.remove(f);
                    } else {
                        if (!imgPlayList.contains(f)) {
                            imgPlayList.add(f);
                        }
                    }
                }
            }
            if (imgDownLoadList.size() == 0) {
                //下载列表为0，则初始化。否则就不初始化，等待下载完成
                startPlayimg();
            } else {
                DownloadMethod.startDownLoadimg(imgDownLoadList, dowdLoadImgIndex, MainActivity.this);
            }
        }
        if (intevalResult.getVideoList().size() > 0) {
            //初始化视频轮播
            for (VideoUrls detail : intevalResult.getVideoList()) {
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), detail.getFileName());
                if (!f.exists() && !detail.isDel()) {
                    videoDownLoadList.add(detail);
                } else {
                    Uri uri = Uri.parse("file://" + f.getAbsolutePath());
                    if (detail.isDel()) {
                        f.delete();
                        videoPlayList.remove(uri);
                    } else {
                        if (!videoPlayList.contains(uri)) {
                            videoPlayList.add(uri);
                        }
                    }
                }
            }
            if (imgDownLoadList.size() == 0) {
                //如果不下载图片，则开始准备下载视频
                startPlay();
                DownloadMethod.startDownLoad(videoDownLoadList, downLoadIndex, MainActivity.this);
            }

        }
    }

    EditText textView;
    private CompositeDisposable compositeDisposable;
    private boolean IsCalling;
    private int CallCount = 0;

    int current = 0;
    IntevalResult intevalResult;
    List<VideoUrls> videoDownLoadList = new ArrayList<>();//视频的下载列表
    List<Uri> videoPlayList = new ArrayList<>();//视频的播放列表
    int downLoadIndex = 0;//下载视频的index
    int dowdLoadImgIndex = 0;//下载图片的index

    private void startPlayimg() {
//        List<String> urlsremote = new ArrayList<>();
//        urlsremote.add("https://www.bolink.club/PaymentMachine/img/left_1_c.png");
//        urlsremote.add("https://www.bolink.club/PaymentMachine/img/left_2_c.png");
//        urlsremote.add("https://www.bolink.club/PaymentMachine/img/left_3_c.png");
//        urlsremote.add("https://www.bolink.club/PaymentMachine/img/left_4_c.png");
//        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"));
//        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "2.jpg"));
//        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "3.jpg"));
//        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "4.jpg"));
//        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "5.jpg"));

//        findViewById(R.id.banner).setOnClickListener(v -> jsMethod.updateSwiper(lists));

//        ImageLoader.loadBigImages(viewPager,urls);
//        viewPager.loadRemoteImage(urlsremote, this);

        viewPager.addView(mCycleViewPager.get(MainActivity.this, imgPlayList));

//        viewPager.loadLocalImage(imgPlayList, this);
//        viewPager.startAutoRotation(0);
    }

    private void startPlayimgClear() {
        imgPlayList.clear();

        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"));
        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"));
        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"));
        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"));
        imgPlayList.add(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"));

        viewPager.removeAllViews();
        viewPager.addView(mCycleViewPager.get(MainActivity.this, imgPlayList));
        viewPager.invalidate();
    }

    private void startPlay() {
        if (videoPlayList.size() > 0) {
//            if (videoView.isPlaying()) {
//                return;
//            }

//            File file = new File(String.valueOf(videoPlayList.get(current)));
//            Log.e(TAG, "startPlay: file.exists()"+file.exists()+"   file.length()"+file.length());
            videoView.setVideoURI(Uri.parse("file://" + videoPlayList.get(current)));
            Log.e(TAG, "VVVideo开始播放: " + current);
            videoView.start();

            if (current >= 0 && current < videoPlayList.size() - 1) {
                current++;
            } else {
                current = 0;
            }
        }
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

    @Deprecated
    private void initSip() {
//        sipUtil = new SipUtil(MainActivity.this);
//        //动态注册的目的是让 reveiver 中获取的context可以强转为 MainActivity
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.SipDemo.INCOMING_CALL");
//        sipUtil.callReceiver = new IncomingCallReceiver();
//        this.registerReceiver(sipUtil.callReceiver, filter);
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(MainActivity.this, ProcessService.class);
        stopService(intent);
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
//                CrashReport.testJavaCrash();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void DownLoadProcess(Messages messages) {
        Download download = (Download) messages.getMsg();
//                    progressbar.setProgress(download.getProgress());
        if (download.getProgress() == 100 && download.getCurrentFileSize() == download.getTotalFileSize()) {
//                        progressbar.setVisibility(View.GONE);

            if (download.getURI().contains(".apk")) {
                //下载的apk
                downloadDialog.dismiss();
                UpdateUtil.installAPK(new File(download.getURI()), this);
            } else if (download.getURI().contains(".jpg")) {
                //先下载图片，再下载视频，最后是apk
                if (download.isSuccess()) {
                    //下载成功，继续下一步
                    File file = new File(download.getURI());
                    if (!imgPlayList.contains(file)) {
                        imgPlayList.add(file);
                        //如果index小于list大小，则继续下载
                        if (++dowdLoadImgIndex < imgDownLoadList.size()) {
                            DownloadMethod.startDownLoadimg(imgDownLoadList, dowdLoadImgIndex, this);
                        } else {
                            startPlayimg();
                            dowdLoadImgIndex = 0;
                            imgDownLoadList.clear();
                            //下载完成后，开始下载视频
                            DownloadMethod.startDownLoad(videoDownLoadList, downLoadIndex, MainActivity.this);
                        }
                    }
                } else {
                    //下载失败，重新下载，不计数
                    DownloadMethod.startDownLoadimg(imgDownLoadList, dowdLoadImgIndex, this);
                }
            } else {
                //下载的视频
                if (download.isSuccess()) {
                    Uri videopath = Uri.parse("file://" + download.getURI());
                    if (!videoPlayList.contains(videopath)) {
                        videoPlayList.add(videopath);
                        if (videoPlayList.size() < 2)
                            startPlay();
                        downLoadIndex++;
                        if (downLoadIndex >= videoDownLoadList.size()) {
                            downLoadIndex = 0;
                            videoDownLoadList.clear();
                        } else {
                            DownloadMethod.startDownLoad(videoDownLoadList, downLoadIndex, MainActivity.this);
                        }
                    }
                } else {
                    //下载失败，重新下载，不计数
                    DownloadMethod.startDownLoad(videoDownLoadList, downLoadIndex, MainActivity.this);
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
    }

    private void initTests() {
        //        progressbar = ((CircleProgressBar) findViewById(R.id.progressbar));
        button = ((Button) findViewById(R.id.button));
        button.setOnClickListener(v -> {
//            jsMethod.NotifyNetState(false);
//            RxBus.get().post(new Messages(Messages.NET_DENY, null));
//            initVideo();
            startPlayimgClear();
            UpdateUtil.CheckUpdate(CommontUtils.getVersion(this), this);
        });
        findViewById(R.id.currentversion).setOnClickListener(v -> Toast.makeText(MainActivity.this, "当前版本:" + CommontUtils.getVersion(this), Toast.LENGTH_LONG).show());

        findViewById(R.id.buttoncash).setOnClickListener(v -> MoneyPaperUtil.get().init(MainActivity.this));
        findViewById(R.id.buttoncashno).setOnClickListener(v -> MoneyPaperUtil.get().Close());

        findViewById(R.id.buttonprint).setOnClickListener(v -> PrintComUtil.get().init(MainActivity.this));
        findViewById(R.id.buttonprint2).setOnClickListener(v -> PrintComUtil.get().print());

        findViewById(R.id.buttonscan).setOnClickListener(v -> ScanUtilUSB.get().init(MainActivity.this));
        findViewById(R.id.buttonscanopen).setOnClickListener(v -> ScanUtilUSB.get().initTest());

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
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int amMax = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        CommontUtils.writeSDFile("======amMax", "" + amMax);
        findViewById(R.id.btn_vol_sys_2).setOnClickListener(v -> {
            am.setStreamVolume(AudioManager.STREAM_SYSTEM, amMax / 2, AudioManager.FLAG_ALLOW_RINGER_MODES);
        });
        findViewById(R.id.btn_vol_sys_0).setOnClickListener(v -> {
            am.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_ALLOW_RINGER_MODES);
        });
        findViewById(R.id.btn_vol_vodeo_2).setOnClickListener(v -> {
            setVolume(amMax / 2, videoView);
        });
        findViewById(R.id.btn_vol_vodeo_0).setOnClickListener(v -> {
//            setVolume(0, videoView);
            LinePhoneUtil.get().initLinePhone();
        });
    }


}

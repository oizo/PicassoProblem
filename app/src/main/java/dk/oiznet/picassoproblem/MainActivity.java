package dk.oiznet.picassoproblem;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.UrlConnectionDownloader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String[] mAkamai = new String[]{
            "https://shopgun-a.akamaihd.net/img/catalog/view/a45dOem-1.jpg?m=nwtw5p",
            "https://shopgun-a.akamaihd.net/img/catalog/view/a45dOem-2.jpg?m=nwtw5p",
            "https://shopgun-a.akamaihd.net/img/catalog/view/a45dOem-3.jpg?m=nwtw5p"
    };

    private static final String[] mHttp2 = new String[]{
            "https://http2.golang.org/gophertiles?x=11&y=8&cachebust=1457957102914159299&latency=0",
            "https://http2.golang.org/gophertiles?x=12&y=8&cachebust=1457957102914159299&latency=0",
            "https://http2.golang.org/gophertiles?x=13&y=8&cachebust=1457957102914159299&latency=0"
    };

    private static final String[] mHttp1 = new String[]{
            "http://http2.golang.org/gophertiles?x=11&y=8&cachebust=1457957102914159299&latency=0",
            "http://http2.golang.org/gophertiles?x=12&y=8&cachebust=1457957102914159299&latency=0",
            "http://http2.golang.org/gophertiles?x=13&y=8&cachebust=1457957102914159299&latency=0"
    };

    Picasso mPicassoUrlConnection;
    Picasso mPicassoOkHttp;

    @Bind(R.id.image1)
    ImageView mImageOne;

    @Bind(R.id.image2)
    ImageView mImageTwo;

    @Bind(R.id.image3)
    ImageView mImageThree;

    @Bind(R.id.urlsActive)
    TextView mUrlsActive;

    int mErrorId = android.support.design.R.drawable.abc_ic_menu_share_mtrl_alpha;

    private String[] mUrls = mAkamai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageLoader.getInstance().init(
                new ImageLoaderConfiguration.Builder(MainActivity.this)
                        .writeDebugLogs().build());

        mPicassoUrlConnection = new Picasso.Builder(this).loggingEnabled(true).downloader(new UrlConnectionDownloader(this)).build();
        mPicassoOkHttp = new Picasso.Builder(this).loggingEnabled(true).downloader( new OkHttpDownloader(this)).build();

        onClickUrlsChange();
        onClickReset();

    }

    public void onClickReset() {

        // Clear caches, to force network
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();

        // Reset images
        int resId = android.support.design.R.drawable.abc_ic_menu_selectall_mtrl_alpha;
        mImageOne.setImageResource(resId);
        mImageTwo.setImageResource(resId);
        mImageThree.setImageResource(resId);
    }

    @OnClick(R.id.auil)
    public void onClickAuil() {
        onClickReset();
        snack("Using: Universal Image Loader");
        ImageLoader i = ImageLoader.getInstance();
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .showImageOnFail(mErrorId)
                .build();
        i.displayImage(mUrls[0], mImageOne, displayImageOptions);
        i.displayImage(mUrls[1], mImageTwo, displayImageOptions);
        i.displayImage(mUrls[2], mImageThree, displayImageOptions);
    }

    int mNext = 0;
    @OnClick(R.id.urlsChange)
    public void onClickUrlsChange() {
        switch (mNext%3) {
            case 0: mUrls = mAkamai; mUrlsActive.setText("Akamai"); break;
            case 1: mUrls = mHttp1; mUrlsActive.setText("Http1"); break;
            case 2: mUrls = mHttp2; mUrlsActive.setText("Http2"); break;
        }
        mNext++;
    }

    @OnClick(R.id.picassoOkHttp)
    public void onClickPicassoOkHttp() {
        onClickReset();
        snack("Using: Picasso OkHttp");
        picasso(mPicassoOkHttp);
    }

    @OnClick(R.id.picassoUrlConnection)
    public void onClickPicassoUrlConnection() {
        onClickReset();
        snack("Using: Picasso UrlConnection");
        picasso(mPicassoUrlConnection);
    }

    private void picasso(Picasso p) {
        loadPicasso(p.load(mUrls[0]), mImageOne);
        loadPicasso(p.load(mUrls[1]), mImageTwo);
        loadPicasso(p.load(mUrls[2]), mImageThree);
    }

    private void loadPicasso(RequestCreator rc, ImageView iv) {
        rc.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .error(mErrorId)
                .into(iv);
    }

    private void snack(String text) {
        Snackbar.make(mImageOne, text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}

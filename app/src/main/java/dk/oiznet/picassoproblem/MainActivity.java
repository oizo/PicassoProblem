package dk.oiznet.picassoproblem;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.image1)
    ImageView mImageOne;

    @Bind(R.id.image2)
    ImageView mImageTwo;

    @Bind(R.id.image3)
    ImageView mImageThree;

    private static final String[] mUrls = new String[]{
            "https://shopgun-a.akamaihd.net/img/catalog/view/a45dOem-1.jpg?m=nwtw5p",
            "https://shopgun-a.akamaihd.net/img/catalog/view/a45dOem-2.jpg?m=nwtw5p",
            "https://shopgun-a.akamaihd.net/img/catalog/view/a45dOem-3.jpg?m=nwtw5p"
    };

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

        Picasso.with(this);

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
        DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.cacheInMemory(false);
        b.cacheOnDisk(false);
        i.displayImage(mUrls[0], mImageOne, b.build());
        i.displayImage(mUrls[1], mImageTwo, b.build());
        i.displayImage(mUrls[2], mImageThree, b.build());
    }

    @OnClick(R.id.picasso)
    public void onClickPicasso() {
        onClickReset();
        snack("Using: Picasso");
        Picasso p = Picasso.with(this);
        p.load(mUrls[0])
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(mImageOne);

        p.load(mUrls[1])
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(mImageTwo);

        p.load(mUrls[2])
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(mImageThree);
    }

    private void snack(String text) {
        Snackbar.make(mImageOne, text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}

package dk.oiznet.picassoproblem;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

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

        Picasso p = Picasso.with(MainActivity.this);
        p.setLoggingEnabled(true);
        p.setIndicatorsEnabled(true);

        onClickReset();

    }

    @OnClick(R.id.reset)
    public void onClickReset() {

        snack("Reset and clear caches");

        // Clear caches, to force network
        PicassoTools.clearCache(Picasso.with(MainActivity.this));
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();

        // Reset images
        mImageOne.setImageResource(android.support.design.R.drawable.abc_ic_menu_selectall_mtrl_alpha);
        mImageTwo.setImageResource(android.support.design.R.drawable.abc_ic_menu_selectall_mtrl_alpha);
        mImageThree.setImageResource(android.support.design.R.drawable.abc_ic_menu_selectall_mtrl_alpha);
    }

    @OnClick(R.id.auil)
    public void onClickAuil() {
        snack("Using: Universal Image Loader");
        ImageLoader.getInstance().displayImage(mUrls[0], mImageOne);
        ImageLoader.getInstance().displayImage(mUrls[1], mImageTwo);
        ImageLoader.getInstance().displayImage(mUrls[2], mImageThree);
    }

    @OnClick(R.id.picasso)
    public void onClickPicasso() {
        snack("Using: Picasso");
        Picasso.with(MainActivity.this).load(mUrls[0]).into(mImageOne);
        Picasso.with(MainActivity.this).load(mUrls[1]).into(mImageTwo);
        Picasso.with(MainActivity.this).load(mUrls[2]).into(mImageThree);
    }

    private void snack(String text) {
        Snackbar.make(mImageOne, text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}

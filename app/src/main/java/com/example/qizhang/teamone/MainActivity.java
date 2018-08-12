package com.example.qizhang.teamone;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qizhang.teamone.adapter.ItemAdapter;
import com.example.qizhang.teamone.model.ApplicationToken;
import com.example.qizhang.teamone.model.BannerImage;
import com.example.qizhang.teamone.model.broswe.ItemSummary;
import com.example.qizhang.teamone.model.broswe.SearchResponse;
import com.example.qizhang.teamone.network.EbayAPI;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private static String APPLICATION_TOKEN_KEY = "application_token_key";


    private RecyclerView computerRecyclerView;
    private RecyclerView sportRecyclerView;
    private RecyclerView newArrivalRecyclerView;
    private String applictionToken;

    private TextView computerMore;

    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

//        Fresco.initialize(this);

        viewPager = findViewById(R.id.view_pager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);

        computerMore = findViewById(R.id.computer_more);
        computerMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // defince source
                Intent intent = MorePageAcitvity.newIntent("computer source", MainActivity.this);
                startActivity(intent);
            }
        });


        List<BannerImage> images = new ArrayList<>();
        images.add(new BannerImage(R.drawable.banner_one));
        images.add(new BannerImage(R.drawable.banner_two));
        images.add(new BannerImage(R.drawable.banner_three));

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(images);
        viewPager.setAdapter(myPagerAdapter);
        viewPagerIndicator.setupWithViewPager(viewPager);
        viewPagerIndicator.addOnPageChangeListener(mOnPageChangeListener);

        LinearLayoutManager computerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        computerLayoutManager.setStackFromEnd(true);

        LinearLayoutManager sportLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        sportLayoutManager.setStackFromEnd(true);

        LinearLayoutManager newLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        newLayoutManager.setStackFromEnd(true);

        computerRecyclerView = findViewById(R.id.computer_recycler_view);
        computerRecyclerView.setLayoutManager(computerLayoutManager);

        sportRecyclerView = findViewById(R.id.sport_recycler_view);
        sportRecyclerView.setLayoutManager(sportLayoutManager);

        newArrivalRecyclerView = findViewById(R.id.newest_recycler_view);
        newArrivalRecyclerView.setLayoutManager(newLayoutManager);

        getApplicationToken();


//        Uri uri = getIntent().getData();
//
//        if (uri != null) {
//            Log.d(TAG, "the full string: " + uri.toString());
//            String code = uri.getQueryParameter("code");
//            Log.d(TAG, "data code: " + code);
//        }
//
//        if (uri == null) {
//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://auth.sandbox.ebay.com/oauth2/authorize?" +
//                                    "client_id=" + EbayAPI.APP_ID
//                                    + "&redirect_uri=" + EbayAPI.RuName + "&response_type=code"
//
//
//                          //  "&scope=https://api.ebay.com/oauth/api_scope/sell.inventory https://api.ebay.com/oauth/api_scope/commerce.catalog.readonly"
//                    ));
//
//            startActivity(intent);
//        }
    }

    private void getApplicationToken() {

        Call<ApplicationToken> call = EbayAPI.getClientTokenService().getApplicationToken("client_credentials",
                EbayAPI.RuName, EbayAPI.APPLICATION_ACCESS_TOKEN_SCOPE);

        call.enqueue(new Callback<ApplicationToken>() {
            @Override
            public void onResponse(Call<ApplicationToken> call, Response<ApplicationToken> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "token: " + response.body().getAccess_token());
                applictionToken = response.body().getAccess_token();
                saveApplicationToken(response.body().getAccess_token());

                String authorization = "Bearer " + applictionToken;

                Call<SearchResponse> callComputer = EbayAPI.getService().getSearchResult(authorization, "Computer", 12);
                callComputer.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        SearchResponse searchResponse = response.body();
                        List<ItemSummary> list = searchResponse.getItemSummaries();

                    //    ItemAdapter adapter = new ItemAdapter(list);
                        ItemAdapter adapter = new ItemAdapter(MainActivity.this, list, Constant.HORIZONTAL);
                        computerRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "call fail", Toast.LENGTH_SHORT).show();

                    }
                });

                Call<SearchResponse> callSport = EbayAPI.getService().getSearchResult(authorization, "Sport", 12);
                callSport.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        SearchResponse searchResponse = response.body();
                        List<ItemSummary> list = searchResponse.getItemSummaries();

                        ItemAdapter adapter = new ItemAdapter(MainActivity.this, list, Constant.HORIZONTAL);
                        sportRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });

                Call<SearchResponse> callNew = EbayAPI.getService().getSearchResult(authorization, "New Arrival", 12);
                callNew.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        SearchResponse searchResponse = response.body();
                        List<ItemSummary> list = searchResponse.getItemSummaries();

                        ItemAdapter adapter = new ItemAdapter(MainActivity.this, list, Constant.HORIZONTAL);
                        newArrivalRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ApplicationToken> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "fail: " + t.getCause().getLocalizedMessage());
            }
        });
    }

    public void saveApplicationToken(String token) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(APPLICATION_TOKEN_KEY, token)
                .apply();
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<BannerImage> bannerImageList;
        LayoutInflater inflater;

        public MyPagerAdapter(List<BannerImage> bannerImageList) {
            inflater = MainActivity.this.getLayoutInflater();
            this.bannerImageList = bannerImageList;
        }

        @Override
        public int getCount() {
            return bannerImageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

//            View itemView = inflater.inflate(R.layout.banner_item, container, false);
//       //     ImageView imageView = itemView.findViewById(R.id.banner_image);
//        //    imageView.setImageResource(bannerImageList.get(position).getImageId());
//            container.addView(itemView);
//            return itemView;

            final SimpleDraweeView imageView = new SimpleDraweeView(MainActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            Uri uri = Uri.parse(bannerList.get(position).getImagePath());
//            imageView.setImageURI(uri);
            imageView.setActualImageResource(bannerImageList.get(position).getImageId());


            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    @NonNull
    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {

        }

        @Override
        public void onPageScrollStateChanged(final int state) {

        }
    };

}

package com.bilinedev.ikasmariagitma.util;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilinedev.ikasmariagitma.R;
import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alex on 11/12/2015.
 */
public class LinkPreview extends RelativeLayout {
    private static String TAG = LinkPreview.class.getSimpleName();
    private boolean destroy = false;
    private RelativeLayout viewPreview;
    private ImageView mImgViewImage;
    private TextView mTxtViewTitle;
    private TextView mTxtViewDescription;
    private TextView mTxtViewSiteName;
    private ProgressBar pbLoading;
    private Context mContext;
    private Handler mHandler;
    private String mTitle=null;
    private String mDescription=null;
    private String mImageLink=null;
    private String mSiteName=null;
    private String mSite;
    private String mLink;
    private ImageView btnRemove;
    private FrameLayout mFrameLayout;
    private PreviewListener mListener;

    public LinkPreview(Context context) {
        super(context);
        initialize(context);
    }

    public LinkPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public LinkPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context){
        mContext=context;
        inflate(context, R.layout.view_preview_link, this);
        viewPreview= findViewById(R.id.view_preview);
        mImgViewImage= findViewById(R.id.imgViewImage);
        mTxtViewTitle= findViewById(R.id.txtViewTitle);
        mTxtViewDescription= findViewById(R.id.txtViewDescription);
        mTxtViewSiteName= findViewById(R.id.txtViewSiteName);
        pbLoading = findViewById(R.id.pb_loading);
        btnRemove = findViewById(R.id.btn_remove);

        mFrameLayout= findViewById(R.id.frameLoading);
        mFrameLayout.setVisibility(GONE);
        mHandler = new Handler(mContext.getMainLooper());
    }

    public void setListener(PreviewListener listener)
    {
        this.mListener=listener;
    }

    public void setData(String title, String description, String image, String site)
    {
        clear();
        mTitle=title;
        mDescription=description;
        mImageLink=image;
        mSiteName=site;
        if (getTitle() != null) {
            if(getTitle().length()>=50)
                mTitle= getTitle().substring(0,49)+"...";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTxtViewTitle.setText(getTitle());
                }
            });
        }
        if (getDescription() != null) {
            if(getDescription().length()>=100)
                mDescription= getDescription().substring(0,99)+"...";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTxtViewDescription.setText(getDescription());
                }
            });

        }
        if (getImageLink() != null&&!getImageLink().equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(mContext)
                            .load(getImageLink())
                            .into(mImgViewImage);
                }
            });

        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(mContext)
                            .load(R.drawable.img_placeholder_landscape)
                            .into(mImgViewImage);
                }
            });
        }
        if (getSiteName() != null) {
            if(getSiteName().length()>=30)
                mSiteName= getSiteName().substring(0,29)+"...";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTxtViewSiteName.setText(getSiteName());
                }
            });
        }
    }

    public void setData(final String url)
    {
        if(!TextUtils.isEmpty(url)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mFrameLayout.setVisibility(VISIBLE);
                    pbLoading.setVisibility(VISIBLE);
                }
            });
            clear();

            OkHttpClient client = new OkHttpClient();
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if(!TextUtils.isEmpty(e.getMessage())) {
                            Log.e(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        Elements titleElements;
                        Elements descriptionElements;
                        Elements imageElements;
                        Elements siteElements;
                        Elements linkElements;
                        String site = "";
                        Document doc = null;
                        doc = Jsoup.parse(response.body().string());
                        titleElements = doc.select("title");

                        if (url.contains("bhphotovideo")) {
                            imageElements = doc.select("image[id=mainImage]");
                            site = "bhphotovideo";
                        } else if (url.contains("www.amazon.com")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.com";
                        } else if (url.contains("www.amazon.co.uk")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.co.uk";
                        } else if (url.contains("www.amazon.de")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.de";
                        } else if (url.contains("www.amazon.fr")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.fr";
                        } else if (url.contains("www.amazon.it")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.it";
                        } else if (url.contains("www.amazon.es")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.es";
                        } else if (url.contains("www.amazon.ca")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.ca";
                        } else if (url.contains("www.amazon.co.jp")) {
                            imageElements = doc.select("img[data-old-hires]");
                            site = "www.amazon.co.jp";
                        } else if (url.contains("m.clove.co.uk")) {
                            imageElements = doc.select("img[id]");
                            site = "m.clove.co.uk";
                        } else if (url.contains("www.clove.co.uk")) {
                            imageElements = doc.select("li[data-thumbnail-path]");
                            site = "www.clove.co.uk";
                        } else if (url.contains("www.google")) {
                            imageElements = doc.select("meta[itemprop=image]");
                            site = "www.google";
                        } else {
                            imageElements = doc.select("meta[property=og:image]");
                        }


                        descriptionElements = doc.select("meta[name=description]");
                        mImageLink = getImageLinkFromSource(imageElements, site);
                        siteElements = doc.select("meta[property=og:site_name]");
                        linkElements = doc.select("meta[property=og:url]");

                        if (titleElements != null && titleElements.size() > 0) {
                            mTitle = titleElements.get(0).text();
                        }
                        if (descriptionElements != null && descriptionElements.size() > 0) {
                            mDescription = descriptionElements.get(0).attr("content");
                        }
                        if (linkElements != null && linkElements.size() > 0) {
                            mLink = linkElements.get(0).attr("content");
                        } else {
                            linkElements = doc.select("link[rel=canonical]");
                            if (linkElements != null && linkElements.size() > 0) {
                                mLink = linkElements.get(0).attr("href");
                            }
                        }
                        if (siteElements != null && siteElements.size() > 0) {
                            mSiteName = siteElements.get(0).attr("content");
                        }

                        if (getTitle() != null && mTxtViewTitle != null) {
                            if (getTitle().length() >= 50)
                                mTitle = getTitle().substring(0, 49) + "...";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtViewTitle.setText(getTitle());
                                }
                            });
                        }
                        if (getDescription() != null && mTxtViewDescription != null) {
                            if (getDescription().length() >= 100)
                                mDescription = getDescription().substring(0, 99) + "...";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtViewDescription.setText(getDescription());
                                }
                            });

                        }
                        if (imageElements.size() > 0) {
                            //Log.e("TAG", imageElements.get(0).attr("content"));
                        }

                        if (getImageLink() != null && !getImageLink().equals("") && mImgViewImage != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(mContext)
                                            .load(getImageLink())
                                            .into(mImgViewImage);
                                }
                            });

                        } else {
                            if (mImgViewImage != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(mContext)
                                                .load(R.drawable.img_placeholder_landscape)
                                                .into(mImgViewImage);
                                    }
                                });
                            }
                        }
                        if (url.toLowerCase().contains("amazon"))
                            if (getSiteName() == null || getSiteName().equals(""))
                                mSiteName = "Amazon";
                        if (getSiteName() != null) {
                            Log.v(TAG, getSiteName());
                            if (getSiteName().length() >= 30)
                                mSiteName = getSiteName().substring(0, 29) + "...";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtViewSiteName.setText(getSiteName());
                                }
                            });
                        }


                        if (pbLoading != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (pbLoading.getVisibility() == VISIBLE)
                                        pbLoading.setVisibility(GONE);
                                    mFrameLayout.setVisibility(GONE);
                                }
                            });
                        }


                        mListener.onDataReady(LinkPreview.this);
                    }

                });
            }
            catch (Exception ex) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pbLoading.getVisibility() == VISIBLE)
                            pbLoading.setVisibility(GONE);
                        mFrameLayout.setVisibility(GONE);
                    }
                });


            }

        }

        btnRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPreview.setVisibility(GONE);
                mListener.onDataRemove(LinkPreview.this);
            }
        });
    }



    private String getImageLinkFromSource(Elements elements, String site)
    {
        String imageLink=null;
        if (elements != null && elements.size() > 0) {
            switch (site)
            {
                case "m.clove.co.uk":
                case "bhphotovideo":
                    imageLink = elements.get(0).attr("src");
                    break;
                case "www.amazon.com":
                case "www.amazon.co.uk":
                case "www.amazon.de":
                case "www.amazon.fr":
                case "www.amazon.it":
                case "www.amazon.es":
                case "www.amazon.ca":
                case "www.amazon.co.jp":
                    imageLink = elements.get(0).attr("data-old-hires");
                    if(TextUtils.isEmpty(imageLink)) {
                        imageLink=elements.get(0).attr("src");
                        if(imageLink.contains("data:image/jpeg;base64,")) {
                            imageLink=elements.get(0).attr("data-a-dynamic-image");
                            if(!TextUtils.isEmpty(imageLink)) {
                                String[] array=imageLink.split(":\\[");
                                if(array.length>1) {
                                    imageLink=array[0];
                                    if(!TextUtils.isEmpty(imageLink)) {
                                        imageLink=imageLink.replace("{\"","");
                                        imageLink=imageLink.replace("\"","");
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "www.clove.co.uk":
                    imageLink="https://www.clove.co.uk"+elements.get(0).attr("data-thumbnail-path");
                    break;
                default:
                    imageLink = elements.get(0).attr("content");
                    break;
            }

        }
        return imageLink;
    }

    private void clear()
    {
        mImgViewImage.setImageResource(0);
        mTxtViewTitle.setText("");
        mTxtViewDescription.setText("");
        mTxtViewSiteName.setText("");
        mTitle=null;
        mDescription=null;
        mImageLink=null;
        mSiteName=null;
        mSite=null;
        mLink=null;
    }

    public interface PreviewListener {
        void onDataReady(LinkPreview preview);
        void onDataRemove(LinkPreview preview);
    }


    public void hideRemove(){
        btnRemove.setVisibility(GONE);
    }

    private void runOnUiThread(Runnable r) {
        mHandler.post(r);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public String getSiteName() {
        return mSiteName;
    }

    public String getSite() {
        return mSite;
    }

    public String getLink() {
        return mLink;
    }
}

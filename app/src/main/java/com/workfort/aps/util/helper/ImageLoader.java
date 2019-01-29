package com.workfort.aps.util.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.workfort.aps.assigmetintent.R;

public class ImageLoader {
    public static void load(Context context, ImageView imageView, int imageResource){
        Glide.with(context)
                .load(imageResource)
                .into(imageView);
    }

    public static void load(Context context, ImageView imageView, String imgUrl) {
        RequestOptions requestOptions = RequestOptions
                .placeholderOf(R.drawable.ic_user)
                .error(R.drawable.ic_user);
        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }
}

package com.punuo.sys.sdk;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by han.chen.
 * Date on 2021/2/18.
 **/
@GlideModule
public class PnAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .error(R.drawable.default_error)
                        .placeholder(R.drawable.default_loading)
        );
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

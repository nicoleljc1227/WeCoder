package com.harlan.jxust.ui.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.harlan.jxust.bean.Friend;
import com.harlan.jxust.ui.adapter.listener.OnRVClickListener;
import com.harlan.jxust.utils.ScreenUtil;
import com.harlan.jxust.wecoder.R;

import butterknife.Bind;

public class ContactViewHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    public ImageView avatar;
    @Bind(R.id.tv_name)
    public TextView name;
    @Bind(R.id.topc)
    public TextView topc;
    @Bind(R.id.topc_divider)
    public View topc_divider;

    private boolean withTopc;

    public ContactViewHolder(Context context, ViewGroup root, OnRVClickListener listener, boolean withTopc) {
        super(context, root, R.layout.item_contact_list, listener);
        this.withTopc = withTopc;
    }

    @Override
    public void bindData(Object o) {
        final Friend user = (Friend) o;

        if (withTopc) {
            topc.setVisibility(View.VISIBLE);
            topc_divider.setVisibility(View.VISIBLE);
            topc.setText(user.getFriendUser().getTopc());
        } else {
            topc.setVisibility(View.GONE);
            topc_divider.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(user.getFriendUser().getAvatar())
                .error(R.drawable.default_avatar)
                .override(ScreenUtil.dip2px(context, 32f), ScreenUtil.dip2px(context, 32f))
                .into(avatar);
        name.setText(user.getFriendUser().getUsername());
    }
}
package com.harlan.jxust.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.harlan.jxust.model.UserModel;
import com.harlan.jxust.ui.adapter.ColorsListAdapter;
import com.harlan.jxust.utils.DialogUtil;
import com.harlan.jxust.utils.PreferencesUtil;
import com.harlan.jxust.utils.ScreenUtil;
import com.harlan.jxust.utils.ThemeUtil;
import com.harlan.jxust.wecoder.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;

/**
 * Created by Harlan on 2016/4/10.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.rl_switch_notification)
    RelativeLayout rl_switch_notification;
    @Bind(R.id.rl_switch_sound)
    RelativeLayout rl_switch_sound;
    @Bind(R.id.rl_switch_vibrate)
    RelativeLayout rl_switch_vibrate;
    @Bind(R.id.rl_switch_speaker)
    RelativeLayout rl_switch_speaker;
    @Bind(R.id.rl_switch_theme)
    RelativeLayout rl_switch_theme;
    @Bind(R.id.btn_logout)
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar.setBackgroundColor(getColorPrimary());
        mToolbar.setTitle("设置");
        mToolbar.setTitleTextAppearance(this, android.R.style.TextAppearance_Medium);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.collapseActionView();
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.rl_switch_theme)
    public void onThemeChoose(View view) {
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setContentView(R.layout.colors_panel_layout);
        Integer[] res = new Integer[]{
                R.drawable.round_red,
                R.drawable.round_brown,
                R.drawable.round_blue_grey,
                R.drawable.round_green,
                R.drawable.round_indigo,
                R.drawable.round_purple,
                R.drawable.round_teal,
                R.drawable.round_orange};
        List<Integer> list = Arrays.asList(res);
        ColorsListAdapter adapter = new ColorsListAdapter(this, list);
        adapter.setCheckItem(ThemeUtil.getCurrentTheme(this).getIntValue());
        GridView gridView = (GridView) window.findViewById(R.id.theme_grid_view);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDialog.dismiss();
                onThemeChoose(position);
            }
        });
    }

    private void onThemeChoose(int position) {
        int value = ThemeUtil.getCurrentTheme(this).getIntValue();
        if (value != position) {
            PreferencesUtil.getInstance(this).setCurTheme(getString(R.string.change_theme_key), position);
            reload();
        }
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick(View view) {
        UserModel.getInstance().logout();
        //可断开连接
        BmobIM.getInstance().disConnect();
        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);
        super.onDestroy();
    }
}
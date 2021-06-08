package com.example.plugin_core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * " 插件 " 模块中的 Activity 类都继承该类
 * 具体的 Activity 业务类的父类
 */
public class BaseActivity extends AppCompatActivity implements PluginActivityInterface {

    /**
     * 注入的 Activity , 代理该 Activity 类作为上下文
     */
    private Activity proxyActivity;

    /**
     * 注入代理 Activity
     * 在 ProxyActivity 中将代理 Activity 组件注入进来
     * @param proxyActivity
     */
    @Override
    public void attach(Activity proxyActivity) {
        this.proxyActivity = proxyActivity;
    }

    @Override
    public void setContentView(int layoutResID) {
        // 调用代理 Activity 中的 setContentView 方法
        if (proxyActivity == null) {
            proxyActivity.setContentView(layoutResID);
        }else{
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        // 调用代理 Activity 中的 setContentView 方法
        if (proxyActivity == null) {
            proxyActivity.setContentView(view);
        }else{
            super.setContentView(view);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (proxyActivity == null) {
            return proxyActivity.findViewById(id);
        }else{
            return super.findViewById(id);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (proxyActivity == null) {
            intent.putExtra("className", intent.getComponent().getClassName());
            proxyActivity.startActivity(intent);
        }else{
            super.startActivity(intent);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}

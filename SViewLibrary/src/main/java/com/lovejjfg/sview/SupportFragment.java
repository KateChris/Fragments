package com.lovejjfg.sview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Joe on 2016/10/13.
 * Email lovejjfg@gmail.com
 */

public abstract class SupportFragment extends Fragment implements ISupportFragment {
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_IS_HIDDEN = "ARG_IS_HIDDEN";
    public static final String ARG_CONTAINER = "ARG_CONTAINER_ID";
    public static final String TAG = "TAG";
    public String tagName;
    @Nullable
    private SupportActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: " + tagName);
        super.onCreate(savedInstanceState);
        initFragments(savedInstanceState, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ARG_IS_HIDDEN, isHidden());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        activity = (SupportActivity) context;
        if (getArguments() != null) {
            tagName = getArguments().getString(ARG_SECTION_NUMBER);
        }
        Log.e(TAG, "onAttach: " + tagName);
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.e(TAG, "onAttachFragment: " + tagName);
        super.onAttachFragment(childFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: " + tagName);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " + tagName);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView: " + tagName);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach: " + tagName);
        activity = null;
        super.onDetach();

    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause: " + tagName);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume: " + tagName);
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart: " + tagName);
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop: " + tagName);
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, "setUserVisibleHint: " + tagName + ";isVisible:" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: " + tagName + (hidden ? "不可见了！" : "可见了！！"));
        super.onHiddenChanged(hidden);
    }

    public int getContainerId() {
        return getArguments().getInt(ARG_CONTAINER);
    }


    @Override
    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        if (activity != null) {
            activity.initFragments(savedInstanceState, this);
        }
    }

    @Nullable
    @Override
    public SupportFragment getTopFragment() {
        if (activity != null) {
            return activity.getTopFragment();
        }
        return null;
    }

    @Nullable
    @Override
    public SupportFragment findFragment(String className) {
        if (activity != null) {
            return activity.findFragment(className);
        }
        return null;
    }

    @Override
    public void loadRoot(int containerViewId, SupportFragment root) {
        if (activity != null) {
            activity.loadRoot(containerViewId, root);
        }
    }

    @Override
    public void addToShow(SupportFragment from, SupportFragment to) {
        if (activity != null) {
            activity.addToShow(from, to);
        }
    }

    @Override
    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        return activity != null && activity.popTo(target, includeSelf);
    }

    @Override
    public void replaceToShow(SupportFragment from, SupportFragment to) {
        if (activity != null) {
            activity.replaceToShow(from, to);
        }
    }


    @Override
    public void showToast(String toast) {
        if (activity != null) {
            activity.showToast(toast);
        }
    }

    @Override
    public void showToast(int stringId) {
        if (activity != null) {
            activity.showToast(stringId);
        }
    }

    @Override
    public void showLoadingDialog(String msg) {
        if (activity != null) {
            activity.showLoadingDialog(msg);
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (activity != null) {
            activity.closeLoadingDialog();
        }
    }

    @Override
    public void openKeyBoard() {
        if (activity != null) {
            activity.openKeyBoard();
        }
    }

    @Override
    public void openKeyBoard(View focusView) {
        if (activity != null) {
            activity.openKeyBoard(focusView);
        }
    }

    @Override
    public void closeKeyBoard() {
        if (activity != null) {
            activity.closeKeyBoard();
        }
    }


    @Override
    public  boolean finishSelf() {
        return activity != null && activity.popSelf();
    }


    //    @Override
//    public void saveToSharedPrefs(String key, Object value) {
//        if (activity != null) {
//            activity.saveToSharedPrefs(key, value);
//        }
//    }

    @Override
    public void saveViewData(Bundle bundle) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            setArguments(bundle);
        } else {
            arguments.putAll(bundle);
        }
    }

    @Override
    public void saveViewData(String key, Bundle bundle) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            Bundle b = new Bundle();
            b.putBundle(key, bundle);
            setArguments(b);
        } else {
            arguments.putBundle(key, bundle);
            setArguments(arguments);
        }
    }
}
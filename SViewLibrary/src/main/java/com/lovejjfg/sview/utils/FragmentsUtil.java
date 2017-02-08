package com.lovejjfg.sview.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lovejjfg.sview.SupportFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public class FragmentsUtil {
    private FragmentManager manager;

    public FragmentsUtil(FragmentManager manager) {
        this.manager = manager;
    }

    public void addToShow(SupportFragment from, SupportFragment to) {
        bindContainerId(from.getContainerId(), to);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = to.getClass().getSimpleName();
        transaction.add(from.getContainerId(), to, tag)
                .addToBackStack(tag)
                .hide(from)
                .show(to)
                .commit();

    }

    public void addToParent(int containerViewId, @NonNull SupportFragment parent, int pos, SupportFragment... childs) {
        FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
        if (childs != null && childs.length > 0) {
            addFragmentsToStack(parent, containerViewId, pos, transaction, false, childs);
        }
    }

    public void replaceToParent(int containerViewId, @NonNull SupportFragment parent, SupportFragment... childs) {
        FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
        if (childs != null && childs.length > 0) {
            for (SupportFragment child : childs) {
                child.parentFragment = parent;
                bindContainerId(containerViewId, child);
                String tag = child.getClass().getSimpleName();
                transaction.replace(containerViewId, child, tag)
                        .addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    public void replaceToShow(SupportFragment from, SupportFragment to) {
        bindContainerId(from.getContainerId(), to);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = to.getClass().getSimpleName();
        transaction.replace(from.getContainerId(), to, tag)
                .addToBackStack(tag)
                .commit();

    }

    public void loadRoot(int containerViewId, int pos, SupportFragment... roots) {
        FragmentTransaction transaction = manager.beginTransaction();
        addFragmentsToStack(null, containerViewId, pos, transaction, true, roots);
    }

    private void addFragmentsToStack(SupportFragment parent, int containerViewId, int pos, FragmentTransaction transaction, boolean isRoot, SupportFragment[] fragments) {
        if (fragments != null && fragments.length > 0) {
            if (pos >= fragments.length || pos < 0) {
                throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + fragments.length);
            }
            for (int i = 0; i < fragments.length; i++) {
                SupportFragment f = fragments[i];
                f.isRoot = isRoot;
                if (parent != null && !isRoot) {
                    f.parentFragment = parent;
                }
                bindContainerId(containerViewId, f);
                String tag = f.getClass().getSimpleName();
                transaction.add(containerViewId, f, tag)
                        .addToBackStack(tag);

                if (i == pos) {
                    transaction.show(f);
                } else {
                    transaction.hide(f);
                }
            }
            transaction.commit();
        }
    }

    private void bindContainerId(int containerId, SupportFragment to) {
        Bundle args = to.getArguments();
        if (args == null) {
            args = new Bundle();
            to.setArguments(args);
        }
        args.putInt(SupportFragment.ARG_CONTAINER, containerId);
    }

    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        if (savedInstanceState == null) {
            return;
        }
        boolean isSupportHidden = savedInstanceState.getBoolean(SupportFragment.ARG_IS_HIDDEN);

        FragmentTransaction ft = manager.beginTransaction();
        if (isSupportHidden) {
            ft.hide(fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }


    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        int flag;
        if (includeSelf) {
            flag = FragmentManager.POP_BACK_STACK_INCLUSIVE;
        } else {
            flag = 0;
        }
        return manager.popBackStackImmediate(target.getSimpleName(), flag);
    }

    public boolean popSelf() {
        return manager.popBackStackImmediate();
    }

    @Nullable
    public SupportFragment findFragment(@NonNull String className) {
        Fragment tagFragment = manager.findFragmentByTag(className);
        if (tagFragment instanceof SupportFragment) {
            return (SupportFragment) tagFragment;
        }
        return null;
    }

    @Nullable
    public List<Fragment> getTopFragment() {
        List<Fragment> fragments = manager.getFragments();
        List<Fragment> topFragments = new ArrayList<>();
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            if (!f.isHidden()) {
                Fragment t = getTTopFragment(f.getFragmentManager());
                if (t != null) {
                    topFragments.add(t);
                }
            }
        }
        return topFragments;
    }

    @Nullable
    private Fragment getTTopFragment(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            // TODO: 2017/2/8 每个层级可能都有几个显示的
            if (!f.isHidden() && f instanceof SupportFragment) {
                if (((SupportFragment) f).isRoot) {
                    return getTTopFragment(f.getChildFragmentManager());
                }
                return f;
            }
        }
        return null;
    }
}

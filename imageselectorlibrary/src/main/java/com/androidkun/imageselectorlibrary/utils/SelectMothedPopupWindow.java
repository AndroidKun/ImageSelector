package com.androidkun.imageselectorlibrary.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidkun.imageselectorlibrary.R;
import com.androidkun.imageselectorlibrary.bean.ImageFloder;


public class SelectMothedPopupWindow extends BasePopupWindowForListView<ImageFloder> {

    public SelectMothedPopupWindow(final Activity activity) {
        super(LayoutInflater.from(activity.getApplicationContext())
                .inflate(R.layout.pop_select_method, null), ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(activity, 156), true);
        this.setAnimationStyle(R.style.anim_popup_dir);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }

        });
    }

    @Override
    public void initViews() {
    }

    public interface OnMothedSelected {
        void mothedSelected(int mothed);
    }

    private OnMothedSelected onMothedSelected;

    public void setOnImageDirSelected(OnMothedSelected onMothedSelected) {
        this.onMothedSelected = onMothedSelected;
    }

    @Override
    public void initEvents() {

        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMothedSelected != null) {
                    onMothedSelected.mothedSelected(1);
                }
                dismiss();
            }
        });
        findViewById(R.id.btn_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMothedSelected != null) {
                    onMothedSelected.mothedSelected(2);
                }
                dismiss();
            }
        });
        findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
        // TODO Auto-generated method stub
    }

}

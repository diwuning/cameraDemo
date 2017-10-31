package com.example.castedemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.castedemo.R;

/**
 * Created by wangchm on 2017/10/27 0027.
 */

public class DialogHelper {
    public static Dialog getBaseDialogWithText(Context context,
                                               String info, final View.OnClickListener onClickListener,
                                               final View.OnClickListener cancelOnClickListener) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_base_text_info, null);

        view.findViewById(R.id.btn_close).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancelOnClickListener != null)
                            cancelOnClickListener.onClick(v);
                        dialog.dismiss();
                    }
                });

        view.findViewById(R.id.btn_ok).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null)
                            onClickListener.onClick(v);
                        dialog.dismiss();
                    }
                });
        if (info != null)
            ((TextView) view.findViewById(R.id.tv_info)).setText(info);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view, params);
        /*Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int)(display.getWidth()); //设置宽度
		dialog.getWindow().setAttributes(lp); */
        return dialog;
    }
}

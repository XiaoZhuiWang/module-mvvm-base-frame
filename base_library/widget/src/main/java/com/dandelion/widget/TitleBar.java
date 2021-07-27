package com.dandelion.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;


public class TitleBar extends RelativeLayout {

    private LinearLayout lltitle;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private LinearLayout llCenter;
    private ImageView ivNavigation;
    private LinearLayout llLeft;
    private LinearLayout llRight;

    private Context mContext;

    private int menuTextSize;
    private ColorStateList menuTextColor = ColorStateList.valueOf(0xFF000000);

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View.inflate(context, R.layout.widget_title_bar, this);
        initView();
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            initAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.TitleBar_title_text) {
            tvTitle.setText(typedArray.getString(attr));

        } else if (attr == R.styleable.TitleBar_title_text_size) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(attr, dp2px(mContext, 17)));

        } else if (attr == R.styleable.TitleBar_title_text_color) {
            tvTitle.setTextColor(typedArray.getColorStateList(attr));

        } else if (attr == R.styleable.TitleBar_sub_title_text) {
            tvSubTitle.setText(typedArray.getString(attr));
            tvSubTitle.setVisibility(View.VISIBLE);

        } else if (attr == R.styleable.TitleBar_sub_title_text_size) {
            tvSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(attr, dp2px(mContext, 10)));

        } else if (attr == R.styleable.TitleBar_sub_title_text_color) {
            tvSubTitle.setTextColor(typedArray.getColorStateList(attr));

        } else if (attr == R.styleable.TitleBar_menu_text_size) {
            menuTextSize = typedArray.getDimensionPixelSize(attr, dp2px(mContext, 15));

        } else if (attr == R.styleable.TitleBar_menu_text_color) {
            menuTextColor = typedArray.getColorStateList(attr);

        }
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    private void initView() {
        lltitle = getViewById(R.id.ll_title);
        tvTitle = getViewById(R.id.tv_title);
        tvSubTitle = getViewById(R.id.tv_subtitle);
        llCenter = getViewById(R.id.ll_center);
        ivNavigation = getViewById(R.id.iv_navigation);
        llLeft = getViewById(R.id.ll_left);
        llRight = getViewById(R.id.ll_right);
    }

    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    public void setTitle(String title) {

        tvTitle.setText(title);
        llCenter.setVisibility(View.GONE);
    }

    public void setTitleWidth(int dpValue) {
        ViewGroup.LayoutParams layoutParams = lltitle.getLayoutParams();
        layoutParams.width = dp2px(mContext, dpValue);
        lltitle.setLayoutParams(layoutParams);
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getResources().getString(resId));
    }

    public void setTitleTextSize(int spValue) {
        tvTitle.setTextSize(spValue);
    }

    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setSubTitle(String title) {
        tvSubTitle.setText(title);
        tvSubTitle.setVisibility(View.VISIBLE);
    }

    public void setSubTitle(@StringRes int resId) {
        setTitle(getResources().getString(resId));
    }

    public void setSubTitleTextSize(int spValue) {
        tvSubTitle.setTextSize(spValue);
    }

    public void setMenuTextSize(int spValue) {
        menuTextSize = dp2px(mContext, spValue);
    }

    public void setMenuTextColor(int color) {
        menuTextColor = ColorStateList.valueOf(color);
    }

    public void showNavigation() {
        ivNavigation.setVisibility(View.VISIBLE);
    }

    public void hideNavigation() {
        ivNavigation.setVisibility(View.GONE);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvSubTitle() {
        return tvSubTitle;
    }


    /************************************************************/

    /**
     * 导航View
     *
     * @param resId
     */
    public void setNavigationIcon(@DrawableRes int resId) {
        ivNavigation.setImageResource(resId);
    }

    public void setNavigationOnClickListener(OnClickListener onClickListener) {
        ivNavigation.setOnClickListener(onClickListener);
    }


    /**
     * 中间菜单
     *
     * @param view
     */
    public void setCenterView(View view) {
        llCenter.removeAllViews();
        llCenter.addView(view);
        llCenter.setVisibility(View.VISIBLE);
        lltitle.setVisibility(View.GONE);
    }


    /**
     * 左边菜单
     *
     * @param view
     * @return
     */
    public View addLeftMenu(View view) {
        ViewGroup.LayoutParams oldParams = view.getLayoutParams();
        LinearLayout.LayoutParams layoutParams;
        if (oldParams != null) {
            layoutParams = new LinearLayout.LayoutParams(oldParams);
        } else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.leftMargin = dp2px(mContext, 8);
        view.setLayoutParams(layoutParams);

        llLeft.addView(view);
        return view;
    }

    public View addLeftMenu(View view, int leftMargin) {
        ViewGroup.LayoutParams oldParams = view.getLayoutParams();
        LinearLayout.LayoutParams layoutParams;
        if (oldParams != null) {
            layoutParams = new LinearLayout.LayoutParams(oldParams);
        } else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.leftMargin = dp2px(mContext, leftMargin);
        view.setLayoutParams(layoutParams);

        llLeft.addView(view);
        return view;
    }


    public View addLeftMenu(View view, LinearLayout.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        llLeft.addView(view);
        return view;
    }


    public TextView addLeftTextMenu(String text, OnClickListener onClickListener) {
        TextView tvMenu = new TextView(mContext);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(text);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = dp2px(mContext, 8);

        tvMenu.setLayoutParams(layoutParams);
        tvMenu.setOnClickListener(onClickListener);

        llLeft.addView(tvMenu);
        return tvMenu;
    }

    public TextView addLeftTextMenu(String text, OnClickListener onClickListener, int leftMargin) {
        TextView tvMenu = new TextView(mContext);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(text);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = dp2px(mContext,  leftMargin);

        tvMenu.setLayoutParams(layoutParams);
        tvMenu.setOnClickListener(onClickListener);

        llLeft.addView(tvMenu);
        return tvMenu;
    }

    public TextView addLeftTextMenu(String text, LinearLayout.LayoutParams layoutParams, OnClickListener onClickListener) {
        TextView tvMenu = new TextView(mContext);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(text);

        tvMenu.setLayoutParams(layoutParams);
        tvMenu.setOnClickListener(onClickListener);

        llLeft.addView(tvMenu);
        return tvMenu;
    }

    public ImageView addLeftImageMenu(@DrawableRes int resId, OnClickListener onClickListener) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = dp2px(mContext, 8);
        ivMenu.setLayoutParams(layoutParams);

        ivMenu.setOnClickListener(onClickListener);

        llLeft.addView(ivMenu);
        return ivMenu;
    }

    public ImageView addLeftImageMenu(@DrawableRes int resId, OnClickListener onClickListener, int leftMargin) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = dp2px(mContext, leftMargin);
        ivMenu.setLayoutParams(layoutParams);

        ivMenu.setOnClickListener(onClickListener);

        llLeft.addView(ivMenu);
        return ivMenu;
    }

    public ImageView addLeftImageMenu(@DrawableRes int resId, LinearLayout.LayoutParams layoutParams, OnClickListener onClickListener) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);

        ivMenu.setLayoutParams(layoutParams);
        ivMenu.setOnClickListener(onClickListener);

        llLeft.addView(ivMenu);
        return ivMenu;
    }


    /**
     * 右边菜单
     *
     * @param view
     * @return
     */
    public View addRightMenu(View view) {

        ViewGroup.LayoutParams oldParams = view.getLayoutParams();
        LinearLayout.LayoutParams layoutParams;
        if (oldParams != null) {
            layoutParams = new LinearLayout.LayoutParams(oldParams);
        } else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.rightMargin = dp2px(mContext, 16);
        view.setLayoutParams(layoutParams);

        llRight.addView(view);
        return view;
    }

    public View addRightMenu(View view, int rightMargin) {
        ViewGroup.LayoutParams oldParams = view.getLayoutParams();
        LinearLayout.LayoutParams layoutParams;
        if (oldParams != null) {
            layoutParams = new LinearLayout.LayoutParams(oldParams);
        } else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.rightMargin = dp2px(mContext, rightMargin);
        view.setLayoutParams(layoutParams);

        llRight.addView(view);
        return view;
    }

    public View addRightMenu(View view, LinearLayout.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        llRight.addView(view);
        return view;
    }

    public TextView addRightTextMenu(String text, OnClickListener onClickListener) {
        TextView tvMenu = new TextView(mContext);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(text);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = dp2px(mContext, 16);

        tvMenu.setLayoutParams(layoutParams);
        tvMenu.setOnClickListener(onClickListener);
        llRight.addView(tvMenu);
        return tvMenu;
    }

    public TextView addRightTextMenu(String text, OnClickListener onClickListener, int rightMargin) {
        TextView tvMenu = new TextView(mContext);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(text);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = dp2px(mContext, rightMargin);

        tvMenu.setLayoutParams(layoutParams);
        tvMenu.setOnClickListener(onClickListener);
        llRight.addView(tvMenu);
        return tvMenu;
    }

    public TextView addRightTextMenu(String text, LinearLayout.LayoutParams layoutParams, OnClickListener onClickListener) {
        TextView tvMenu = new TextView(mContext);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(text);

        tvMenu.setLayoutParams(layoutParams);
        tvMenu.setOnClickListener(onClickListener);
        llRight.addView(tvMenu);
        return tvMenu;
    }

    public ImageView addRightImageMenu(@DrawableRes int resId, OnClickListener onClickListener) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = dp2px(mContext, 16);
        ivMenu.setLayoutParams(layoutParams);

        ivMenu.setOnClickListener(onClickListener);

        llRight.addView(ivMenu);
        return ivMenu;
    }

    public ImageView addRightImageMenu(@DrawableRes int resId, OnClickListener onClickListener, int rightMargin) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = dp2px(mContext, rightMargin);
        ivMenu.setLayoutParams(layoutParams);

        ivMenu.setOnClickListener(onClickListener);

        llRight.addView(ivMenu);
        return ivMenu;
    }

    public ImageView addRightImageMenu(@DrawableRes int resId, LinearLayout.LayoutParams layoutParams, OnClickListener onClickListener) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);

        ivMenu.setLayoutParams(layoutParams);

        ivMenu.setOnClickListener(onClickListener);
        llRight.addView(ivMenu);
        return ivMenu;
    }


    /************************************************************/
    public View getLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        return linearLayout;
    }

    public View getImageView(@DrawableRes int resId) {
        ImageView ivMenu = new ImageView(mContext);
        ivMenu.setImageResource(resId);
        ivMenu.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return ivMenu;
    }

    /**
     * 添加右边自定义的View
     *
     * @param view
     * @param marginLeft
     * @param marginRight
     * @param onClickListener
     */
    public void addRightCustomView(View view, int marginLeft, int marginRight, int bg, OnClickListener onClickListener) {
        if (view != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(marginRight);
            params.setMarginStart(marginLeft);
            view.setLayoutParams(params);
            if (onClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
            view.setBackground(ContextCompat.getDrawable(mContext, bg));
            llRight.addView(view, 0);
        }
    }

}

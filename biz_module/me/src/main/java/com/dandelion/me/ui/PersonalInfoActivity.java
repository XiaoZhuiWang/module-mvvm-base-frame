package com.dandelion.me.ui;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.common.base_ui.activity.LoadBaseActivity;
import com.dandelion.communication.me.router.MeRouterTable;
import com.dandelion.me.data.bean.PersonalInfoBean;
import com.dandelion.me.databinding.MeActivityPersonalInfoBinding;
import com.dandelion.me.vm.PersonalInfoVM;

import org.jetbrains.annotations.NotNull;

@Route(path = MeRouterTable.PATH_PAGE_PERSONAL_INFO_ACTIVITY, extras = 0x00000001)
public class PersonalInfoActivity extends LoadBaseActivity<PersonalInfoVM> {
    private MeActivityPersonalInfoBinding mBinding;

    @Autowired(name = "name")
    String name;

    public static void start(String name){
        ARouter.getInstance()
                .build(MeRouterTable.PATH_PAGE_PERSONAL_INFO_ACTIVITY)
                .withString("name", name)
                .navigation();
    }

    @Override
    protected void objectInject() {

    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = MeActivityPersonalInfoBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {
        titleBar.setTitle("个人信息");
        mBinding.tvNickName.setText(name);
    }

    @Override
    protected void addListener() {
        viewModel.getPersonalInfoBeanMutableLiveData().observe(this, new Observer<PersonalInfoBean>() {
            @Override
            public void onChanged(PersonalInfoBean personalInfoBean) {
                mBinding.tvContent.setText(personalInfoBean.getContent());
            }
        });
    }

    @NotNull
    @Override
    protected PersonalInfoVM initViewModel() {
        return new ViewModelProvider(this).get(PersonalInfoVM.class);
    }

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected boolean isDialogLoading() {
        return true;
    }
}
package com.dandelion.me.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dandelion.common.base_vm.LoadBaseVM;
import com.dandelion.me.data.bean.PersonalInfoBean;
import com.dandelion.me.data.remote.MeApi;
import com.dandelion.network.bean.ResponseBean;
import com.dandelion.network.constant.LoadType;
import com.dandelion.network.http.HttpMainClient;
import com.dandelion.network.http.HttpRxObserver;
import com.dandelion.network.scheduler.IoMainScheduler;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by lin.wang on 2021.07.24
 */
public class PersonalInfoVM extends LoadBaseVM{
    private MutableLiveData<PersonalInfoBean> personalInfoBeanMutableLiveData = new MutableLiveData<>();

    public PersonalInfoVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onLoadInitData() {
        queryPersonInfo();
    }

    public void queryPersonInfo(){
        HttpMainClient.getService(MeApi.class)
                .queryPersonInfo("111")
                .compose(new IoMainScheduler<ResponseBean<PersonalInfoBean>>())
                .subscribe(new HttpRxObserver<ResponseBean<PersonalInfoBean>>(loadStatusLiveData, LoadType.LOAD_TYPE_DEFAULT) {
                    @Override
                    public void onStart(@NotNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(ResponseBean<PersonalInfoBean> response) {
                        PersonalInfoBean personalInfoBean = response.getData();
                        if (personalInfoBean != null) {
                            personalInfoBeanMutableLiveData.setValue(personalInfoBean);
                        }
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        super.onFail(errorCode, errorMsg);
                        // 测试数据
                        PersonalInfoBean personalInfoBean = new PersonalInfoBean();
                        personalInfoBean.setContent("我是从服务端获取的数据：张阿三是个小王八蛋");
                        personalInfoBeanMutableLiveData.setValue(personalInfoBean);

                    }
                });

    }

    public MutableLiveData<PersonalInfoBean> getPersonalInfoBeanMutableLiveData() {
        return personalInfoBeanMutableLiveData;
    }
}

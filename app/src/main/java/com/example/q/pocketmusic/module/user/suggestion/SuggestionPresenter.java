package com.example.q.pocketmusic.module.user.suggestion;

import android.content.Context;
import android.text.TextUtils;

import com.example.q.pocketmusic.callback.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.util.MyToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by Cloud on 2016/11/14.
 */

public class SuggestionPresenter extends BasePresenter {
    private IView activity;
    private Context context;
    private MyUser user;

    public SuggestionPresenter(IView activity, Context context, MyUser user) {
        this.activity = activity;
        this.context = context;
        this.user = user;
    }

    public void sendSuggestion(String suggestion) {
        if (TextUtils.isEmpty(suggestion)) {
            return;
        }
        final UserSuggestion userSuggestion = new UserSuggestion(user);
        userSuggestion.setSuggestion(suggestion);
        userSuggestion.save(new ToastSaveListener<String>(context, activity) {
            @Override
            public void onSuccess(String s) {
                user.increment("contribution", Constant.ADD_CONTRIBUTION_SUGGESTION);
                user.update(new ToastUpdateListener(context, activity) {
                    @Override
                    public void onSuccess() {
                        MyToast.showToast(context, CommonString.ADD_CONTRIBUTION_BASE + Constant.ADD_CONTRIBUTION_SUGGESTION);
                        activity.sendSuggestionResult(userSuggestion);
                    }
                });
            }
        });
    }

    public void getSuggestionList() {
        BmobQuery<UserSuggestion> query = new BmobQuery<>();
        query.addWhereEqualTo("user", new BmobPointer(user));
        query.findObjects(new ToastQueryListener<UserSuggestion>(context, activity) {
            @Override
            public void onSuccess(final List<UserSuggestion> list) {
                activity.getSuggestionListResult(list);
            }
        });
    }


    public interface IView extends IBaseList {

        void sendSuggestionResult(UserSuggestion userSuggestion);

        void getSuggestionListResult(List<UserSuggestion> list);

    }
}

package com.example.q.pocketmusic.module.home.ask.list;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;

import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.home.ask.comment.AskSongCommentActivity;
import com.example.q.pocketmusic.module.home.ask.publish.AskSongActivity;

import org.json.JSONArray;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import rx.Observable;

/**
 * Created by Cloud on 2017/1/26.
 */

public class HomeAskListFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;
    private int mPage;

    public HomeAskListFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    //得到帖子列表
    public void getPostList() {
        BmobQuery<AskSongPost> queryPost = new BmobQuery<>();
        queryPost.setLimit(10);
        queryPost.order("-updatedAt");//逆序
        queryPost.include("user");//把User表也查出来
        queryPost.findObjects(new ToastQueryListener<AskSongPost>(context, fragment) {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                fragment.setPostList(list);
            }
        });
    }

    //加载更多
    public void getMore() {
        mPage++;
        BmobQuery<AskSongPost> queryPost = new BmobQuery<>();
        queryPost.setLimit(10);
        queryPost.setSkip(mPage * 10);
        queryPost.order("-updatedAt");//逆序
        queryPost.include("user");//把User表也查出来
        queryPost.findObjects(new ToastQueryListener<AskSongPost>(context, fragment) {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                fragment.setPostList(list);
            }
        });

    }

    //跳转到其他人的个人界面
    public void enterOtherProfileActivity(AskSongPost askSongPost) {
//        MyUser other = askSongPost.getUser();
//        Intent intent = new Intent(context, OtherProfileActivity.class);
//        intent.putExtra(OtherProfileActivity.PARAM_USER, other);
//        context.startActivity(intent);
    }

    //跳转到评论CommentActivity(
    public void enterCommentActivity(AskSongPost askSongPost) {
        Intent intent = new Intent(context, AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, askSongPost);
        context.startActivity(intent);

    }

    //跳转到AskSongActivity
    public void enterAskSongActivity() {
        Intent intent = new Intent(context, AskSongActivity.class);
        //注意这里使用的是Fragment的方法，而不能用Activity的方法
        ((BaseFragment) fragment).startActivityForResult(intent, AskSongActivity.REQUEST_ASK);
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }


    public interface IView extends IBaseView {
        void setPostList(List<AskSongPost> list);
    }
}

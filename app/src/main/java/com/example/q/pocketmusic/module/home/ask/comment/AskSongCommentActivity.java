package com.example.q.pocketmusic.module.home.ask.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.dialog.PicDialog;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2016/11/14.
 */

public class AskSongCommentActivity extends AuthActivity implements AskSongCommentPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_pic)
    ImageView addPic;
    @BindView(R.id.user_input_edt)
    EditText userInputEdt;
    @BindView(R.id.send_comment_btn)
    Button sendCommentBtn;
    @BindView(R.id.input_ll)
    LinearLayout inputLl;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.number_pic_tv)
    TextView numberPicTv;
    private AskSongCommentPresenter presenter;
    private AskSongCommentAdapter adapter;
    private PicDialog picDialog;
    public static final String PARAM_POST = "param_post";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_song_comment);
        ButterKnife.bind(this);
        adapter = new AskSongCommentAdapter(AskSongCommentActivity.this);
        final AskSongPost post = (AskSongPost) getIntent().getSerializableExtra(PARAM_POST);
        presenter = new AskSongCommentPresenter(AskSongCommentActivity.this, AskSongCommentActivity.this, user, post);
        initToolbar(toolbar, presenter.getPost().getTitle());
        initRecyclerView(recycler, adapter);
        initView();
    }


    private void initView() {
        //评论者列表
        adapter.addHeader(new PostHeadView(context,
                presenter.getPost().getContent(),
                presenter.getPost().getUser().getNickName(),
                presenter.getPost().getTitle(),
                presenter.getPost().getUser().getHeadImg(),
                presenter.getPost().getCreatedAt()));
        recycler.setRefreshing(true);
        presenter.getCommentList();
        setListener();
    }

    private void setListener() {
        //回复
        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = userInputEdt.getText().toString().trim();
                presenter.sendComment(comment);
            }
        });
        //添加图片
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addPic();
            }
        });

        //adapter点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.alertPicDialog(adapter.getItem(position));  //弹出简略图
            }
        });
    }


    //加载评论列表
    @Override
    public void setCommentList(List<AskSongComment> list) {
        recycler.setRefreshing(false);
        adapter.addAll(list);
    }

    //发送评论返回
    @Override
    public void sendCommentResult(String s, AskSongComment askSongComment) {
        adapter.add(askSongComment);
        numberPicTv.setText(0 + " 张");
    }


    //添加图片返回,最好有个列表可以展示
    @Override
    public void addPicResult(List<String> picUrls) {
        MyToast.showToast(context, "已经添加" + picUrls.size());
        numberPicTv.setText(picUrls.size() + " 张");
    }

    //发送评论后，输入框为空
    @Override
    public void setCommentInput(String s) {
        userInputEdt.setText(s);
    }

    @Override
    public void showPicDialog(final Song song, final AskSongComment askSongComment) {
        picDialog = new PicDialog.Builder(context)
                .setList(song.getIvUrl())
                .setOnSelectListener(new PicDialog.Builder.OnSelectListener() {
                    @Override
                    public void onSelectOk() {
                        //进入SongActivity
                        presenter.enterSongActivity(song, askSongComment);
                    }
                })
                .create();
        picDialog.show();
    }

}
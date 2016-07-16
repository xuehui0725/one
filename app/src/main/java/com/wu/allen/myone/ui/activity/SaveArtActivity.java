package com.wu.allen.myone.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wu.allen.myone.R;
import com.wu.allen.myone.adapter.SaveAdapter;
import com.wu.allen.myone.injector.components.AppComponent;
import com.wu.allen.myone.model.ArticleSave;
import com.wu.allen.myone.view.ISaveView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.List;

import static com.wu.allen.myone.R.id.recyclerView;

/**
 * Created by allen on 2016/7/15.
 */

public class SaveArtActivity extends BaseActivity implements ISaveView {

    private static final String TAG = "SaveArtActivity";
    private EasyRecyclerView mRecyclerView;
    private SaveAdapter mSaveAdapter;
    private Handler handler = new Handler();
    private int page = 0;
    private Realm mRealm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articlesave_layout);
        initView();
        QueryArticle();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (EasyRecyclerView) findViewById(recyclerView);
        mSaveAdapter = new SaveAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapterWithProgress(mSaveAdapter);
        mSaveAdapter.setNoMore(R.layout.view_nomore);
        mSaveAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    private void QueryArticle() {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ArticleSave> query = realm.where(ArticleSave.class);
                RealmResults<ArticleSave> result1 = query.findAll();
                Log.d(TAG,result1.size()+"");
                fillData(result1);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void fillData(List<ArticleSave> list) {
        Log.d(TAG,list.size()+"");
        mSaveAdapter.addAll(list);
        mSaveAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }
}
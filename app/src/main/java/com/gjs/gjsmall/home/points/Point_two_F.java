package com.gjs.gjsmall.home.points;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.detail.DemoLoadMoreView;
import com.gjs.gjsmall.home.detail.DividerItemDecoration;
import com.gjs.gjsmall.home.detail.PtrrvBaseAdapter;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

/**
 *
 * @author
 *
 */
public class Point_two_F
		extends Fragment {
	private ImageView btn_back;

	private PullToRefreshRecyclerView mPtrrv;
	private PtrrvAdapter              mAdapter;
	private static final int DEFAULT_ITEM_SIZE = 20;
	private static final int ITEM_SIZE_OFFSET = 20;

	private static final int MSG_CODE_REFRESH = 0;
	private static final int MSG_CODE_LOADMORE = 1;

	private static final int TIME = 1000;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.point_two_f, null);
		initView(view);
		findViews(view);
		return view;
	}

	private void initView(View view) {}

	private void findViews(View view) {
		mPtrrv = (PullToRefreshRecyclerView) view.findViewById(R.id.ptrrv);
		mPtrrv.setSwipeEnable(true);//open swipe
		DemoLoadMoreView loadMoreView = new DemoLoadMoreView(getActivity(), mPtrrv.getRecyclerView());
		loadMoreView.setLoadmoreString(getString(R.string.demo_loadmore));
		loadMoreView.setLoadMorePadding(100);
		mPtrrv.setLayoutManager(new LinearLayoutManager(getActivity()));
		mPtrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
			@Override
			public void onLoadMoreItems() {
				mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
			}
		});
		mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
			}
		});
		mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(),
																			 DividerItemDecoration.VERTICAL_LIST));
		mPtrrv.addHeaderView(View.inflate(getActivity(), R.layout.header, null));
		mPtrrv.setEmptyView(View.inflate(getActivity(),R.layout.empty_view,null));
		mPtrrv.removeHeader();
		mPtrrv.setLoadMoreFooter(loadMoreView);
		mAdapter = new PtrrvAdapter(getActivity());
		mAdapter.setCount(0);
		mAdapter.setCount(DEFAULT_ITEM_SIZE);
		mPtrrv.setAdapter(mAdapter);
		mPtrrv.onFinishLoading(true, false);
	}


	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MSG_CODE_REFRESH) {
				mAdapter.setCount(DEFAULT_ITEM_SIZE);
				mAdapter.notifyDataSetChanged();
				mPtrrv.setOnRefreshComplete();
				mPtrrv.onFinishLoading(true, false);
			} else if (msg.what == MSG_CODE_LOADMORE) {
				if(mAdapter.getItemCount() == DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET){
					//over
					Toast.makeText(getActivity(), R.string.nomoredata, Toast.LENGTH_SHORT).show();
					mPtrrv.onFinishLoading(false, false);
				}else {
					mAdapter.setCount(DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET);
					mAdapter.notifyDataSetChanged();
					mPtrrv.onFinishLoading(true, false);
				}
			}
		}
	};

	private class PtrrvAdapter extends PtrrvBaseAdapter<PtrrvAdapter.ViewHolder> {
		public PtrrvAdapter(Context context) {
			super(context);
		}


		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = mInflater.inflate(R.layout.ptrrv_item2, null);
			return new ViewHolder(view);
		}


		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {

		}

		class ViewHolder extends RecyclerView.ViewHolder{

			public ViewHolder(View itemView) {
				super(itemView);
			}
		}
	}



}

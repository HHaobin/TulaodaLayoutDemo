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
import android.widget.TextView;
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
public class Point_one_F
		extends Fragment implements View.OnClickListener{
	private ImageView btn_back;

	private PullToRefreshRecyclerView mPtrrv;
	private PtrrvAdapter              mAdapter;
	private static final int DEFAULT_ITEM_SIZE = 20;
	private static final int ITEM_SIZE_OFFSET = 20;

	private static final int MSG_CODE_REFRESH = 0;
	private static final int MSG_CODE_LOADMORE = 1;

	private static final int TIME = 1000;

	private ImageView point_back_1;
	private TextView	point_1_menu_1,point_1_menu_2,point_1_menu_3,point_1_menu_4,point_1_menu_5,
			point_1_index_1,point_1_index_2,point_1_index_3,point_1_index_4,point_1_index_5;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.point_one_f, null);
		initView(view);
		findViews(view);

		return view;
	}
	public void onClick(View v){
		switch (v.getId()) {
			case R.id.point_back_1:
				getActivity().finish();
				break;
			case R.id.point_1_menu_1:
				initIndex();
				point_1_index_1.setVisibility(View.VISIBLE);
				break;
			case R.id.point_1_menu_2:
				initIndex();
				point_1_index_2.setVisibility(View.VISIBLE);
				break;
			case R.id.point_1_menu_3:
				initIndex();
				point_1_index_3.setVisibility(View.VISIBLE);
				break;
			case R.id.point_1_menu_4:
				initIndex();
				point_1_index_4.setVisibility(View.VISIBLE);
				break;
			case R.id.point_1_menu_5:
				initIndex();
				point_1_index_5.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}

	private void initView(View view) {
		point_back_1= (ImageView) view.findViewById(R.id.point_back_1);
		point_1_menu_1= (TextView) view.findViewById(R.id.point_1_menu_1);
		point_1_menu_2= (TextView) view.findViewById(R.id.point_1_menu_2);
		point_1_menu_3= (TextView) view.findViewById(R.id.point_1_menu_3);
		point_1_menu_4= (TextView) view.findViewById(R.id.point_1_menu_4);
		point_1_menu_5= (TextView) view.findViewById(R.id.point_1_menu_5);
		point_1_index_1= (TextView) view.findViewById(R.id.point_1_index_1);
		point_1_index_2= (TextView) view.findViewById(R.id.point_1_index_2);
		point_1_index_3= (TextView) view.findViewById(R.id.point_1_index_3);
		point_1_index_4= (TextView) view.findViewById(R.id.point_1_index_4);
		point_1_index_5= (TextView) view.findViewById(R.id.point_1_index_5);

		point_back_1.setOnClickListener(this);
		point_1_menu_1.setOnClickListener(this);
		point_1_menu_2.setOnClickListener(this);
		point_1_menu_3.setOnClickListener(this);
		point_1_menu_4.setOnClickListener(this);
		point_1_menu_5.setOnClickListener(this);
		initIndex();
		point_1_index_1.setVisibility(View.VISIBLE);
	}

	private void initIndex() {
		point_1_index_1.setVisibility(View.GONE);
		point_1_index_2.setVisibility(View.GONE);
		point_1_index_3.setVisibility(View.GONE);
		point_1_index_4.setVisibility(View.GONE);
		point_1_index_5.setVisibility(View.GONE);
	}


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
			View view = mInflater.inflate(R.layout.ptrrv_item1, null);
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

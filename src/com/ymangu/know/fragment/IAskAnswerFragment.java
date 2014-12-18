package com.ymangu.know.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.ymangu.know.R;
import com.ymangu.know.adapter.IAskAdapter;
import com.ymangu.know.bean.IAskBean;
import com.ymangu.know.ui.XListView;
import com.ymangu.know.ui.XListView.IXListViewListener;
import com.ymangu.know.utils.DateUtil;
/**
	 * .XListview
	支持下拉刷新，上拉加载更多，
	下拉刷新的动作回调，上拉加载更多的回调。
	 布局可以自定义
 **/
public class IAskAnswerFragment extends BaseFragment implements IXListViewListener {

	private XListView listView;
	private List<IAskBean> list;
	private SimpleDateFormat sdf;  //时间格式
	private IAskAdapter iAskAdapter;
	
	private Handler handler = new Handler();
	@Override
	protected View initView(LayoutInflater inflater) {
		rootView = inflater.inflate(R.layout.iask_answer_fragment,null);
		return rootView;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		listView = (XListView) rootView.findViewById(R.id.listview); //得到XListView
		listView.setPullLoadEnable(true);//开启加载更多
		listView.setPullRefreshEnable(true);//开启下拉刷新
		//注册下拉，上拉监听事件
		listView.setXListViewListener(this);
		
		sdf=DateUtil.getSimpleDateFormat();
				
		list=initListData();
		iAskAdapter = new IAskAdapter(getActivity(), list);
		listView.setAdapter(iAskAdapter);
		
	}

	private List<IAskBean> initListData() {
		 list= new ArrayList<IAskBean>();
		try {
			for (int i = 0; i < 15; i ++) {
				IAskBean ask = new IAskBean();
				ask.setTitle("文承武德，一统江湖，日出东方，唯我不败！" + i );
				ask.setDetails("不悔梦归处，只恨太匆匆？" + i);
				ask.setAvatarId(R.drawable.user_icon);
				String date = "2014-12-12 11:54:" + i;
				ask.setAskTime(sdf.parse(date));  //把String 解析成 Date
				ask.setScore(20 + i);
				ask.setAnswerCount(5 + i);
				list.add(ask);
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
		
		
		
		
	}
	
	//下拉刷新的时候会触发该方法
	@Override
	public void onRefresh() {
		//让它3秒后再执行
		handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				try {
					IAskBean ask = new IAskBean();
					ask.setTitle("下拉刷新加载的最新问题标题" );
					ask.setDetails("下拉刷新问题的详情" );
					ask.setAvatarId(R.drawable.user_icon);
					Random r = new Random();
					int s = r.nextInt(60);
					
					String date = "2014-12-12 14:45:" + s;
					ask.setAskTime(sdf.parse(date));
					ask.setScore(20);
					ask.setAnswerCount(5);
					
					list.add(ask);
					
			Collections.sort(list, comparator);
			//设置最后 一次刷新的时间
			listView.setRefreshTime(sdf.format(new Date()));
					iAskAdapter.notifyDataSetChanged();
					listView.stopRefresh();//让下拉刷新的圈圈消失
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}					
			
		}, 3000);
		
		
	}
	
	private Comparator<IAskBean> comparator = new Comparator<IAskBean>() {
		
		@Override
		public int compare(IAskBean i1, IAskBean i2) {
			//从小到大，从大到小
//			根据第一个参数小于、等于或大于第二个参数分别返回负整数、零或正整数。 
			if (i1.getAskTime().getTime() < i2.getAskTime().getTime()) {  //比较日期的ms值
				return 1;
			} else if (i1.getAskTime().getTime() > i2.getAskTime().getTime()) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	
	
	//上拉加载更多时触发,历史数据
	@Override
	public void onLoadMore() {
		IAskBean ask;
		try {
			ask = new IAskBean();
			ask.setTitle("加载更多的问题标题" );
			ask.setDetails("加载更多的问题详情" );
			ask.setAvatarId(R.drawable.user_icon);
			Random r = new Random();
			int s = r.nextInt(60);
			String date = "2014-12-12 11:52:" + s;
			ask.setAskTime(sdf.parse(date));
			ask.setScore(20 );
			ask.setAnswerCount(5);
			list.add(ask);
			iAskAdapter.notifyDataSetChanged();
			listView.stopLoadMore();//让加载更多的圈圈消失
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}

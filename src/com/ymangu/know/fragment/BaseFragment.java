package com.ymangu.know.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * . ① 继承自v4 包下的Fragment，不然只能在4.0以上用
 *   ②  类中有方法是抽象的，则类也必须是抽象的；抽象类不能被实例化，通过子类来实现抽象方法；
 **/
public abstract class BaseFragment extends Fragment {
	protected Context ctx;      //上下文对象
	public View rootView;
	
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 在这里得到slidingMenu对象，子类就都拥有了这个对象
		initData(savedInstanceState);
		
		
	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * .在onCreate 中得到上下文，通过 Fragment 的 getActivity()方法得到
		 **/
		ctx = getActivity();   
	}	
	
	//相当 于 setContentView()
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
		// 把 LayoutInflater对象传递给子类，在子类中实现得到View的方法
		rootView  = initView(inflater);	 	
		return rootView;
	}

	protected abstract View initView(LayoutInflater inflater);
	protected abstract void initData(Bundle savedInstanceState);
	
	
}

package com.ymangu.know.adapter;

import com.ymangu.know.fragment.IAskAnswerFragment;
import com.ymangu.know.fragment.IAskFragment;
import com.ymangu.know.fragment.IAskPersonFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class IAskFragmentAdapter extends FragmentPagerAdapter {

	public IAskFragmentAdapter(FragmentManager fm) {
		super(fm);
		
		
	}
	/**
	 * 向外返回当前要显示的fragment
	 */
	@Override
	public Fragment getItem(int position) {
		Fragment f=null;
		switch(position){
		case 0:
			f=new IAskFragment();
			break;
		case 1:
			f = new IAskAnswerFragment();
			break;
		case 2:
			f = new IAskPersonFragment();
			break;
		default:
			break;
		}		
		return f;
	}

	/**
	 * 返回一共多少个数量
	 */
	@Override
	public int getCount() {
		return 3;
	}
	//这两个方法，直接这么写就可以了
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}
	

}

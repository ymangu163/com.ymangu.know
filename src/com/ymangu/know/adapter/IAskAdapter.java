package com.ymangu.know.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymangu.know.R;
import com.ymangu.know.bean.IAskBean;
import com.ymangu.know.utils.DateUtil;

public class IAskAdapter extends BaseAdapter {
	private Context context;
	private List<IAskBean> list;
	private LayoutInflater inflater;
	
	public IAskAdapter(Context context,List<IAskBean> list) {
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = this.inflater.inflate(R.layout.iask_all_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.iask_question_title);
			holder.details = (TextView) convertView.findViewById(R.id.iask_question_detail);
			holder.askTime = (TextView) convertView.findViewById(R.id.iask_answer_time);
			holder.answerCount = (TextView) convertView.findViewById(R.id.iask_answer_count);
			holder.score = (TextView) convertView.findViewById(R.id.iask_reward);
			holder.avatar = (ImageView) convertView.findViewById(R.id.iask_user_icon);
			convertView.setTag(holder);			
		}else{
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		IAskBean ask = this.list.get(position);
		holder.title.setText(ask.getTitle());
		holder.details.setText(ask.getDetails());
		Date askTime = ask.getAskTime();
		String date = DateUtil.getSimpleDateFormat().format(askTime);
		holder.askTime.setText(date);
		holder.answerCount.setText(ask.getAnswerCount() + "");
		holder.score.setText(ask.getScore() + "");
		holder.avatar.setImageResource(ask.getAvatarId());
		
		return convertView;
	}
	
	private static final class ViewHolder {
		private TextView title;
		private TextView details;
		private TextView askTime;
		private TextView answerCount;
		private TextView score;
		private ImageView avatar;
	}
}

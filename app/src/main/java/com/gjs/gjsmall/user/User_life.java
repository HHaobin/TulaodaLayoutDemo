package com.gjs.gjsmall.user;

import android.app.Activity;
import android.os.Bundle;

import com.gjs.MyView.ScratchTextView;
import com.gjs.gjsmall.R;

import java.util.Random;

/**
 * 刮刮乐界面
 * @author http://yecaoly.taobao.com
 *
 */
public class User_life extends Activity {

	/**刮刮乐组件*/
	private ScratchTextView tv_Scratch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_praise);
		tv_Scratch=(ScratchTextView) findViewById(R.id.tv_Scratch);
		tv_Scratch.initScratchCard(0xFFCECED1, 15, 1f);
		tv_Scratch.setText(str_reward[getRandom()]);
	}


	private String[] str_reward ={"谢谢惠顾","没有奖", "恭喜！一等奖","没有奖", "鼓励奖，加油","没有奖",
								  "优秀奖","没有奖", "恭喜！三等奖","没有奖", "Srroy！再来吧！","没有奖",
								  "恭喜！二等奖","没有奖", "很抱歉", "Srroy！再来吧！","没有奖", "再买一次就有了"};


	/**随机生成一个数*/
	private int getRandom(){
		Random random =new Random();
		int    number =random.nextInt(10);

		return number;
	}

}

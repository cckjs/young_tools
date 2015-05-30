package com.young.tools.miner.hmm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 隐马尔科夫模型 隐马尔科夫模型:有5个要素 模型分成5个要素 下面已天气为例
 * 你一个异地的朋友只做三种活动：散步、看书、做清洁。每天只做一种活动。假设天气只有两种状态
 * ：晴和兩。每天只有一种天气。你的朋友每天告诉你他做了什么，但是不告诉你他那里的天气。 1.状态序列S(隐藏的状态)例如(晴雨晴晴晴)是我们要算的
 * 2.观察序列O(可以观察到的)例如(读书,做清洁,散步,做清洁,散步)
 * 3.初始状态矩阵P,根据长时间统计某天晴的概率是0.6,雨的概率是0.4.则P=[0.6,0.4]
 * 4.状态转移矩阵A从晴转晴的概率是0.7，从晴转兩的概率是0.3。从兩转晴的概率是0.4，从兩转兩的概率是0.6.则A=[0.7,0.3]
 * [0.4,0.6]
 * 5.输出/混淆矩阵Q天气晴时，散步的概率是0.4，看书的概率是0.3，做清洁的概率是0.3。天气兩时，散步的概率是0.1，看书的概率是0
 * .4，做清洁的概率是0.5。则 B=[0.4,0.3,0.3] [0.4,0.1,0.5]
 * 
 * @author yangy 注意:初始状态概率矩阵P,状态转移矩阵A还有输出矩阵Q为模型的参数 一共有三個問題
 *         1.已知模型参数，计算某一给定可观察状态序列的概率.例子 比如给定了观察序列O(1,2,3,4,5)
 *         那么计算这个序列产生的概率,求解该问题采用 向前向后算法实现
 *         2.根据可观察状态的序列找到一个最可能的隐藏状态序列.比如给定了观察序列O(1
 *         ,2,3,4,5),求最可能的隐藏状态S序列,采用维特比算法求解 3.根据观察到的序列集来找到一个最有可能的
 *         HMM,就是根据观察序列不断更新HMM模型的参数矩阵,采用鲍姆韦尔奇算法+EM求解 这里先解决前两个问题
 */
public class HiddenMarkov {

	private String[] states;

	private String[] observes;

	private Map<String, Double> states_p;

	private Map<String, Map<String, Double>> trans_p;

	private Map<String, Map<String, Double>> emi_p;

	class SequenceResult {

		private List<String> sequence;

		private double prob;

		public List<String> getSequence() {
			return sequence;
		}

		public void setSequence(List<String> sequence) {
			this.sequence = sequence;
		}

		public double getProb() {
			return prob;
		}

		public void setProb(double prob) {
			this.prob = prob;
		}

	}

	public HiddenMarkov(String[] states, String[] observes,
			Map<String, Double> states_p,
			Map<String, Map<String, Double>> trans_p,
			Map<String, Map<String, Double>> emi_p) {
		this.states = states;
		this.observes = observes;
		this.states_p = states_p;
		this.trans_p = trans_p;
		this.emi_p = emi_p;
	}

	/**
	 * 向前算法,求解HMM第一个问题
	 * 
	 * @param observe
	 * @return
	 */
	public double forward(String[] observe) {
		List<Map<String, Double>> o_order_p = new ArrayList<Map<String, Double>>();
		/**
		 * 计算t=0时刻的概率概率矩阵,t=0时刻,说明观察序列的值为observe[0] 那么概率=状态的初始概率*发射矩阵中 状态到观察的概率
		 */
		double p = 0;
		Map<String, Double> temp_0_p = new HashMap<String, Double>();
		for (int i = 0; i < states.length; i++) {
			p = states_p.get(states[i]) * emi_p.get(states[i]).get(observe[0]);
			temp_0_p.put(states[i], p);
		}
		o_order_p.add(temp_0_p);

		/**
		 * 计算1<t<observe.length
		 */
		for (int t = 1; t < observe.length; t++) {
			Map<String, Double> temp_t_p = new HashMap<String, Double>();
			/**
			 * 计算状态之间相互转移的概率之和并且*
			 */

			for (int i = 0; i < states.length; i++) {
				double t_sum = 0;
				for (int j = 0; j < states.length; j++) {
					t_sum += trans_p.get(states[i]).get(states[j])
							* o_order_p.get(t - 1).get(states[j]);
				}
				double temp_p = t_sum * emi_p.get(states[i]).get(observe[t]);
				temp_t_p.put(states[i], temp_p);
			}
			o_order_p.add(temp_t_p);
		}
		Map<String, Double> path = o_order_p.get(observe.length - 1);
		double result = 0;
		for (Map.Entry<String, Double> entry : path.entrySet()) {
			result += entry.getValue();
		}
		return result;
	}

	public SequenceResult vertebi(String[] observe) {
		SequenceResult result = new SequenceResult();
		int observe_length = observe.length;
		List<Map<String, Double>> result_p = new ArrayList<Map<String, Double>>();
		Map<String, List<String>> paths = new HashMap<String, List<String>>();
		// 计算t=0时刻
		Map<String, Double> t_1_state_p = new HashMap<String, Double>();
		double p_t_1 = 0;
		List<String> state_path = null;
		for (String s : states) {
			p_t_1 = states_p.get(s) * emi_p.get(s).get(observe[0]);
			t_1_state_p.put(s, p_t_1);
			state_path = new ArrayList<String>();
			state_path.add(s);
			paths.put(s, state_path);
		}
		result_p.add(t_1_state_p);
		// 计算t>=1时刻

		for (int i = 1; i < observe_length; i++) {
			Map<String, Double> p_up_1_map = new HashMap<String, Double>();
			for (int j = 0; j < states.length; j++) {
				double max = 0;
				String path_index = states[0];
				for (int k = 0; k < states.length; k++) {
					double p_up_1 = result_p.get(i - 1).get(states[j])
							* trans_p.get(states[j]).get(states[k]);
					if (p_up_1 > max) {
						max = p_up_1;
						path_index = states[k];
					}
				}
				System.out.println("max="+max+",path="+path_index);
				p_up_1_map.put(states[j],
						max * emi_p.get(states[j]).get(observe[i]));
				paths.get(states[j]).add(path_index);
			}
			result_p.add(p_up_1_map);
		}
		double max = 0;
		String last_state = null;
		for(int i=0;i<states.length;i++){
			if(result_p.get(result_p.size()-1).get(states[i])>max){
				max = result_p.get(result_p.size()-1).get(states[i]);
				last_state = states[i];
			}
		}
		System.out.println(result_p);
		System.out.println(paths);
		System.out.println("出现的最大概率为"+max+",最大的状态为"+last_state);
		List<String> result_path = new ArrayList<String>();
		String path = null;
		for (int i = observe_length - 1; i > 0; i--) {
			//path = paths.get(i).get(index);
			//sta.add(state.get(index));
		}
		// 把状态序列再顺过来
		//Collections.reverse(sta);
		return result;
	}
}

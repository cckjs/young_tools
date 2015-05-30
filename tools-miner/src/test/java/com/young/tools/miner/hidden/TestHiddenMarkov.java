package com.young.tools.miner.hidden;

import java.util.HashMap;
import java.util.Map;

import com.young.tools.miner.hmm.HiddenMarkov;

public class TestHiddenMarkov {

	static double[][] transititon_probability = new double[][] { { 0.7, 0.3 },
			{ 0.4, 0.6 }, };
	static double[][] emission_probability = new double[][] {
			{ 0.4, 0.3, 0.3 }, {  0.1, 0.4, 0.5 }, };

	public static void main(String[] args) {
		String states[] = new String[] { "Sunny", "Rainy" };
		String[] observations = new String[] { "walk", "read", "clear" };
		Map<String, Double> start_probability = new HashMap<String, Double>();
		start_probability.put("Sunny", 0.6);
		start_probability.put("Rainy", 0.4);

		Map<String, Map<String, Double>> transititon_probability = new HashMap<String, Map<String, Double>>();
		for (int i = 0; i < states.length; i++) {
			Map<String, Double> temp = new HashMap<String, Double>();
			for (int j = 0; j < states.length; j++) {
				temp.put(states[j],
						TestHiddenMarkov.transititon_probability[i][j]);
			}
			transititon_probability.put(states[i], temp);
		}

		Map<String, Map<String, Double>> emission_probability = new HashMap<String, Map<String, Double>>();
		for (int i = 0; i < states.length; i++) {
			Map<String, Double> temp = new HashMap<String, Double>();
			for (int j = 0; j < observations.length; j++) {
				temp.put(observations[j],
						TestHiddenMarkov.emission_probability[i][j]);
			}
			emission_probability.put(states[i], temp);
		}

		HiddenMarkov hmm = new HiddenMarkov(states, observations,
				start_probability, transititon_probability,
				emission_probability);
		String[] observe = new String[5];
		observe[0] = "read";
		observe[1] = "clear";
		observe[2] = "read";
		observe[3] = "clear";
		observe[4] = "walk";

		//System.out.println(hmm.forward(observe));
		hmm.vertebi(observe);
	}
}

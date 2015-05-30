package com.young.tools.common.util.bloom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableBloom {

	public static void write(BloomFilter bloom, String filename) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filename));

			out.writeObject(bloom);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static BloomFilter read(String filename) {
		ObjectInputStream in = null;
		File file = null;
		try {
			file = new File(filename);
			if (!file.exists())
				return null;
			in = new ObjectInputStream(new FileInputStream(file));
			BloomFilter filter = (BloomFilter) in.readObject();
			if (filter != null)
				return filter;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
}

package com.github.fishbotjava.securetoken.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Hardware4Nix {
	
	private static String sn = null;

	public static final String getSerialNumber() {
		if (sn == null) {
			readHwinfo();
		}
		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}
		return sn;
	}

	private static BufferedReader read(String command) {
		OutputStream os = null;
		InputStream is = null;
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(command.split(" "));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		os = process.getOutputStream();
		is = process.getInputStream();
		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new BufferedReader(new InputStreamReader(is));
	}

	private static void readHwinfo() {
		String line = null;
		String marker = "Unique ID:";
		BufferedReader br = null;
		try {
			br = read("hwinfo --cpu");
			sn = br.readLine();
			while ((line = br.readLine()) != null) {
				if (line.indexOf(marker) != -1) {
					sn = line.split(marker)[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}

package com.github.fishbotjava.securetoken;

import junit.framework.Assert;

import org.junit.Test;

import com.github.fishbotjava.securetoken.Hardware;


public class HardwareTest {

	@Test
	public void test_getSerialNumber() {
		String sn = Hardware.getSerialNumber();
		Assert.assertNotNull(sn);

	}

}

package io.github.fengya90.skynet.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Created by fengya on 14-10-14.
 */
public class Config {
	private static XMLConfiguration config;
	private static String cfgfile;

	/**
	 * 指定配置文件
	 * @param cfgfile 配置文件路径
	 */
	public static void setProfile(String cfgfile) {
		Config.cfgfile = cfgfile;
	}

	/**
	 * 获取全局配置对象
	 * @return
	 * @throws ConfigurationException
	 */
	public static XMLConfiguration getConfig() throws ConfigurationException {
		if (config == null) {
			if (cfgfile == null) {
				cfgfile = "dev.xml";
			}
			config = new XMLConfiguration(cfgfile);
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		}
		return config;
	}
}

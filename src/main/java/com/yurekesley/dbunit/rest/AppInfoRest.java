package com.yurekesley.dbunit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yurekesley.dbunit.json.Json;
import com.yurekesley.dbunit.json.JsonResult;
import com.yurekesley.dbunit.util.RestUtil;

@RestController
public class AppInfoRest {

	private static final String DEFAULT_VERSION = "1.0.0_00";

	@Autowired
	private Environment environment;

	@GetMapping(value = "/app/info", produces = RestUtil.JSON_PRODUCE)
	public JsonResult info() {

		String appVersion = environment.getProperty("app.version", DEFAULT_VERSION);
		if (appVersion.trim().isEmpty())
			appVersion = DEFAULT_VERSION;

		AppInfo appInfo = new AppInfo(appVersion);

		return Json.success().withData(appInfo).build();
	}

	static class AppInfo {

		private String version;

		public AppInfo(String version) {
			this.version = version;
		}

		public String getVersion() {
			return version;
		}
	}
}
package com.desjardins;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutLocalServiceWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class MyLayoutService extends LayoutLocalServiceWrapper {
	
	/** Logger class */
	private static final Log LOGGER = LogFactoryUtil.getLog(MyLayoutService.class);
	
	public MyLayoutService(LayoutLocalService layoutLocalService) {
		super(layoutLocalService);
		LOGGER.info("error import layout TODO");
		// TODO Auto-generated constructor stub
	}


	@Override
	public void importLayouts(long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
			throws PortalException, SystemException {
		InputStream inputStreamImporter = null;
		File file = FileUtil.createTempFile("lar");
		LOGGER.info("error import layout TODO");
		try {
			FileUtil.write(file, is);
			inputStreamImporter = new FileInputStream(file);
		} catch (IOException e) {
			inputStreamImporter = is;
			LOGGER.error("error import layout TODO");
		} 
		
		// orignal implementation
		//super.importLayouts(userId, groupId, privateLayout, parameterMap, inputStreamImporter);
		LOGGER.info("error import layout TODO");
		// ajoute les categories aux layouts
		if(file.canRead()) {
		//	AssetLayoutImporterUtil.addCategoriesToLayouts(userId, groupId, privateLayout, parameterMap, file);
		} else {
			LOGGER.error("error import layout TODO");
		}
	}
}

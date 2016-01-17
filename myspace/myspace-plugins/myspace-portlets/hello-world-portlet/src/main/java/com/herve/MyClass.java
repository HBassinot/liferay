package com.herve;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class MyClass extends MVCPortlet {


	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		test();
		
		super.doView(renderRequest, renderResponse);
	}
	
	
	@Override
	public void init() throws PortletException {
		super.init();
	}


	private void test() {
		String title = "test";
		long companyId = 20155;
		long groupId = 20182;
		
		try {
			List<JournalFolder> folders = JournalFolderLocalServiceUtil.getFolders(groupId);
			
			List<JournalArticle> search = JournalArticleLocalServiceUtil.search(companyId, groupId, java.util.Collections.EMPTY_LIST, 0, null, null, 
					title, null, null, null, "", "", null, null, 0, null, true, 0, 1, null);
			
			System.out.println("== " +search.size());
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(RenderRequest arg0, RenderResponse arg1)
			throws PortletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("AAAAAAAAAAAa");
		
		super.render(arg0, arg1);
	}
}

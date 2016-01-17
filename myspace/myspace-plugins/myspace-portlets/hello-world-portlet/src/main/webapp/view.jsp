<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
 
<theme:defineObjects/>
<portlet:defineObjects />

<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="com.liferay.portlet.PortletURLFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategoryConstants" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategoryProperty" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryPropertyServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>

<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>

<aui:form name="fm">
	<aui:row cssClass="categories-admin-content">

		<aui:col cssClass="vocabulary-categories-container" width="<%= 40 %>">
			<span class="select-vocabularies-container">
				<aui:input cssClass="select-categories" inline="<%= true %>" label="" name="checkAllCategories" title='checkAllCategories' type="checkbox" />
			</span>

			<h3 class="categories-header">categories</h3>

			<div class="vocabulary-categories"></div>
		</aui:col>

	</aui:row>
</aui:form>

<aui:script use="liferay-category-admin">
	new Liferay.Portlet.AssetCategoryAdmin(
		{
			baseActionURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.ACTION_PHASE) %>',
			baseRenderURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
			itemsPerPage: <%= SearchContainer.DEFAULT_DELTA %>,
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>

This is the <b>hello-world-portlet</b>.

COUCOU les gars !!
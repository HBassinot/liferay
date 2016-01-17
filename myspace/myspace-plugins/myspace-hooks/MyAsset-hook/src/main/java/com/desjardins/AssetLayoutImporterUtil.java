package com.desjardins;

import com.liferay.portal.LARFileException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.File;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;

import java.util.List;
import java.util.Map;

public class AssetLayoutImporterUtil {

	/**
	 * 
	 * @param userId
	 * @param groupId
	 * @param privateLayout
	 * @param parameterMap
	 * @param file
	 */
	public static void addCategoriesToLayouts(long userId, long groupId,
			boolean privateLayout, Map<String, String[]> parameterMap, File file) {
		try {
			ManifestSummary manifestSummary = ExportImportHelperUtil
					.getManifestSummary(userId, groupId, parameterMap, file);

			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

			ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

			PortletDataContext portletDataContext = PortletDataContextFactoryUtil
					.createImportPortletDataContext(layoutSet.getCompanyId(),
							groupId, parameterMap, null, zipReader);

			portletDataContext.setManifestSummary(manifestSummary);

			portletDataContext.setPrivateLayout(privateLayout);

			Element _headerElement = readHeaderImporter(portletDataContext);

			long sourceCompanyId = GetterUtil.getLong(_headerElement
					.attributeValue("company-id"));
			portletDataContext.setSourceCompanyId(sourceCompanyId);

			long sourceCompanyGroupId = GetterUtil.getLong(_headerElement
					.attributeValue("company-group-id"));
			portletDataContext.setSourceCompanyGroupId(sourceCompanyGroupId);

			long sourceGroupId = GetterUtil.getLong(_headerElement
					.attributeValue("group-id"));
			portletDataContext.setSourceGroupId(sourceGroupId);

			readAssetCategories(portletDataContext);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static void readAssetCategories(
			PortletDataContext portletDataContext) throws Exception {

		String xml = portletDataContext
				.getZipEntryAsString(ExportImportPathUtil
						.getSourceRootPath(portletDataContext)
						+ "/categories-hierarchy.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);
		Element rootElement = document.getRootElement();
		Map<String, String> assetCategoryUuids = (Map<String, String>) portletDataContext
				.getNewPrimaryKeysMap(AssetCategory.class + ".uuid");
		Element assetsElement = rootElement.element("assets");
		List<Element> assetElements = assetsElement.elements("asset");

		for (Element assetElement : assetElements) {
			String className = GetterUtil.getString(assetElement
					.attributeValue("class-name"));

			// uniquement pour les layouts
			if ("com.liferay.portal.model.Layout".equals(className)) {

				long classPK = GetterUtil.getLong(assetElement
						.attributeValue("class-pk"));

				Layout layout = LayoutLocalServiceUtil.getLayout(classPK);
				System.out.println("layout = " + layout.getName());

				String[] assetCategoryUuidArray = StringUtil.split(GetterUtil
						.getString(assetElement
								.attributeValue("category-uuids")));

				long[] assetCategoryIds = new long[0];

				for (String assetCategoryUuid : assetCategoryUuidArray) {
					assetCategoryUuid = MapUtil.getString(assetCategoryUuids,
							assetCategoryUuid, assetCategoryUuid);

					AssetCategory assetCategory = AssetCategoryUtil
							.fetchByUUID_G(assetCategoryUuid,
									portletDataContext.getScopeGroupId());

					if (assetCategory == null) {
						Group companyGroup = GroupLocalServiceUtil
								.getCompanyGroup(portletDataContext
										.getCompanyId());

						assetCategory = AssetCategoryUtil.fetchByUUID_G(
								assetCategoryUuid, companyGroup.getGroupId());
					}

					if (assetCategory != null) {
						assetCategoryIds = ArrayUtil.append(assetCategoryIds,
								assetCategory.getCategoryId());
					}
				}

				portletDataContext.addAssetCategories(className, classPK,
						assetCategoryIds);
			}
		}
	}

	/**
	 * Cette methode retourne le header du fichier manifest.xml du lar
	 * 
	 * @param portletDataContext
	 * @return
	 * @throws Exception
	 */
	private static Element readHeaderImporter(
			PortletDataContext portletDataContext) throws Exception {
		Element rootElement = null;
		String xml = portletDataContext.getZipEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException("manifest.xml not found in the LAR");
		}

		try {
			Document document = SAXReaderUtil.read(xml);
			rootElement = document.getRootElement();
			portletDataContext.setImportDataRootElement(rootElement);
		} catch (Exception e) {
			throw new LARFileException(e);
		}

		return rootElement.element("header");
	}
}

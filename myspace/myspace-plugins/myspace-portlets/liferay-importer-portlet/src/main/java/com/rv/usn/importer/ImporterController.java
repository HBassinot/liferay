package com.rv.usn.importer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("view")
public class ImporterController {

	private static final Log LOGGER = LogFactoryUtil.getLog(ImporterController.class);

	@RenderMapping
	public ModelAndView display(RenderRequest request, RenderResponse response) {
		Map<String, Object> model = new HashMap<String, Object>() ;
		LOGGER.debug("render");
		return new ModelAndView("view", model);
	}
}

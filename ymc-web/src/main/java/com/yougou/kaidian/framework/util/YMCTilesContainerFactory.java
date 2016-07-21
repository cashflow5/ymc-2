package com.yougou.kaidian.framework.util;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.evaluator.AttributeEvaluatorFactory;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.freemarker.TilesSharedVariableFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.freemarker.render.FreemarkerRenderer;
import org.apache.tiles.request.freemarker.render.FreemarkerRendererBuilder;
import org.apache.tiles.request.freemarker.servlet.SharedVariableLoaderFreemarkerServlet;
import org.apache.tiles.request.render.BasicRendererFactory;


public class YMCTilesContainerFactory extends BasicTilesContainerFactory {

	@Override
	protected void registerAttributeRenderers(
			BasicRendererFactory rendererFactory,
			ApplicationContext applicationContext, TilesContainer container,
			AttributeEvaluatorFactory attributeEvaluatorFactory) {  
		
		   super.registerAttributeRenderers(rendererFactory, applicationContext,
		            container, attributeEvaluatorFactory);
	        FreemarkerRenderer freemarkerRenderer = FreemarkerRendererBuilder
	                .createInstance()
	                .setApplicationContext(applicationContext)
	                .setParameter("TemplatePath", "/")
	                .setParameter("NoCache", "false")
	                .setParameter("ContentType", "text/html;charset=UTF-8")
	                .setParameter("template_update_delay", "0")
	                .setParameter("default_encoding", "UTF-8")
	                .setParameter("number_format", "0.##########")
	                .setParameter("locale", "zh_CN")
	                .setParameter(SharedVariableLoaderFreemarkerServlet.CUSTOM_SHARED_VARIABLE_FACTORIES_INIT_PARAM,
	                        "tiles," + TilesSharedVariableFactory.class.getName()).build();
	        rendererFactory.registerRenderer("freemarker", freemarkerRenderer);
	        rendererFactory.setDefaultRenderer(rendererFactory.getRenderer("freemarker"));
	}

}

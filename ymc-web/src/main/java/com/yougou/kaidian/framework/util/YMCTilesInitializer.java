package com.yougou.kaidian.framework.util;

import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.startup.AbstractTilesInitializer;

public class YMCTilesInitializer extends AbstractTilesInitializer {

	@Override
	protected AbstractTilesContainerFactory createContainerFactory(
			ApplicationContext context) {
		// TODO Auto-generated method stub
		return new YMCTilesContainerFactory();
	}
}

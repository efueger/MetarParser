package com.mivek.parser;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.mivek.enums.CloudQuantity;
import com.mivek.enums.CloudType;
import com.mivek.enums.Descriptive;
import com.mivek.enums.Intensity;
import com.mivek.enums.Phenomenon;
import com.mivek.model.Cloud;
import com.mivek.model.WeatherCode;
import com.mivek.model.WeatherCondition;
import com.mivek.model.Wind;

/**
 * 
 * @author mivek
 *
 */
@Ignore
public abstract class AbstractParserTest<T extends WeatherCode> {
	/*
	 * ===========================
	 * 
	 * TEST ParseCloud
	 * 
	 * ==========================
	 */

	@Test
	public void testParseCloudNullCloudQuantity() {
		String[] cloudTab = new String[] { "AZE", "015" };

		Cloud res = getSut().parseCloud(cloudTab);

		assertNull(res);
	}

	@Test
	public void testParseCloudSkyClear() {
		String[] cloudTab = new String[] { "SKC", "SKC", null, null };

		Cloud res = getSut().parseCloud(cloudTab);

		assertNotNull(res);
		assertEquals(CloudQuantity.SKC, res.getQuantity());
		assertEquals(0, res.getAltitude());
		assertNull(res.getType());
	}

	@Test
	public void testParseCloudWithAltitude() {
		String[] cloudTab = new String[] { "SCT016", "SCT", "016", null };
		Cloud res = getSut().parseCloud(cloudTab);

		assertNotNull(res);
		assertEquals(CloudQuantity.SCT, res.getQuantity());
		assertEquals(480, res.getAltitude());
		assertNull(res.getType());
	}

	@Test
	public void testParseCloudWithType() {
		String[] cloudTab = new String[] { "SCT026CB", "SCT", "026", "CB" };

		Cloud res = getSut().parseCloud(cloudTab);

		assertNotNull(res);
		assertEquals(CloudQuantity.SCT, res.getQuantity());
		assertEquals(30 * 26, res.getAltitude());
		assertNotNull(res.getType());
		assertEquals(CloudType.CB, res.getType());
	}

	/**
	 * ===================== TEST ParseWind ==================== *
	 */
	@Test
	public void testParseWindSimple() {
		String[] windPart = new String[] { "34008KT", "340", "08", null, "KT" };

		Wind res = getSut().parseWind(windPart);

		assertNotNull(res);
		assertThat(res.getDirection(), is(i18n.Messages.CONVERTER_N));
		assertEquals(8, res.getSpeed());
		assertEquals(0, res.getGust());
		assertEquals("KT", res.getUnit());

	}

	@Test
	public void testParseWindWithGusts() {
		String[] windPart = new String[] { "12017G20KT", "120", "17", "20", "KT" };

		Wind res = getSut().parseWind(windPart);

		assertNotNull(res);
		assertThat(res.getDirection(), is(i18n.Messages.CONVERTER_SE));
		assertEquals(17, res.getSpeed());
		assertEquals(20, res.getGust());
		assertEquals("KT", res.getUnit());
	}

	/*
	 * =================== WEATHER CONDITION ===================
	 */
	@Test
	public void testParseWCSimple() {
		String wcPart = "-DZ";

		WeatherCondition wc = getSut().parseWeatherCondition(wcPart);

		assertEquals(Intensity.LIGHT, wc.getIntensity());
		assertNull(wc.getDescriptive());
		assertThat(wc.getPhenomenons(), hasSize(1));
		assertThat(wc.getPhenomenons(), hasItem(Phenomenon.DRIZZLE));
	}

	@Test
	public void testParseWCMultiplePHE() {
		String wcPart = "SHRAGR";

		WeatherCondition wc = getSut().parseWeatherCondition(wcPart);

		assertNull(wc.getIntensity());
		assertNotNull(wc.getDescriptive());
		assertEquals(Descriptive.SHOWERS, wc.getDescriptive());
		assertThat(wc.getPhenomenons(), hasSize(2));
		assertThat(wc.getPhenomenons(), hasItems(Phenomenon.RAIN, Phenomenon.HAIL));
	}

	@Test
	public void testParseWCNull() {
		String wcPart = "-SH";

		WeatherCondition wc = getSut().parseWeatherCondition(wcPart);

		assertNull(wc);
	}

	abstract AbstractParser<T> getSut();
}

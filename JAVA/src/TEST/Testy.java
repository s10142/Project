package TEST;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import MAIN.DB;

public class Testy
{
	@Test
	public void checkConnection(){
	assertNotNull(DB.initDB());
	}
}

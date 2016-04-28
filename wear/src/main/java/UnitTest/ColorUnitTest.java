package UnitTest;
import com.pilotcraftsystems.games.ColorChanger;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by csastudent2015 on 4/26/16.
 * csastudent2015 is super cool.
 */
public class ColorUnitTest {
    @Test
    public void testRGBEquals() throws Exception {
        assertEquals(255,new ColorChanger(100,60,80).testingUpdateRGB(80).getRed());
        assertEquals(255,new ColorChanger(100,60,80).testingUpdateRGB(80).getBlue());
    }
    @Test
    public void  testRGBLess() throws Exception{
        assertEquals(89,new ColorChanger(100,60,80).testingUpdateRGB(67).getRed());
        assertEquals(255,new ColorChanger(100,60,80).testingUpdateRGB(67).getBlue());
    }
    @Test
    public void  testRGBMore() throws Exception{
        assertEquals(255,new ColorChanger(100,60,80).testingUpdateRGB(95).getRed());
        assertEquals(64,new ColorChanger(100,60,80).testingUpdateRGB(95).getBlue());
    }
}

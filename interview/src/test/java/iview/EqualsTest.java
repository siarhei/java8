package iview;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EqualsTest {

    @Test
    public void checkSymmetry() {
        Equals.Key<Object> keyObj = new Equals.Key<>(1L, Object.class);
        Equals.Key<String> keyString = new Equals.Key<>(1L, String.class);

        Assert.assertEquals(keyString, keyObj);
    }

}
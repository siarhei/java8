package by.sample.spring.beans;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/lookup-beans.xml")
public class BeanLookupTest {

    @Autowired
    private BeanLookup beanLookup;

    @Test
    public void checkLookup() {
        Assert.assertNotSame(beanLookup.getBeanProto(), beanLookup.getBeanProto());
    }
}

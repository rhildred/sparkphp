package com.salesucation.sparkphp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testRender()
    {
    	PHPRenderer php = new PHPRenderer();
    	php.setViewDir("testviews/");
    	String rc = php.render("test1.phtml");
        assertTrue( rc.equals("Rich was here!") );
    }

    public void testRenderLayout()
    {
    	PHPRenderer php = new PHPRenderer();
    	php.setViewDir("testviews/");
    	String rc = php.render("testlayout.phtml");
        assertTrue( rc.equals("Rich was here! ... So there!") );
    }

    public void testRenderModel()
    {
    	PHPRenderer php = new PHPRenderer();
    	php.setViewDir("testviews/");
    	String rc = php.render("testmodel.phtml", "{\"name\":\"Rich\"}");
        assertTrue( rc.equals("Rich was here!") );
    }
}

# sparkphp
maven artifact for rendering php views

Dependency:

```

<dependency>
    <groupId>com.salesucation</groupId>
    <artifactId>sparkphp</artifactId>
    <version>1.0</version>
</dependency>


```

Example test for usage:

```
import com.salesucation.sparkphp.*;

....

	PHPRenderer php = new PHPRenderer();
	php.setViewDir("testviews/");
	String rc = php.render("testmodel.phtml", "{\"name\":\"Rich\"}");
	assertTrue( rc.equals("Rich was here!") );

```

The `setViewDir` folder is relative to the folder containing the pom.xml file.
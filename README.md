# proxifier

### Summary
Proxifier creates proxies of beans in order to check whether all setters resp. getters have been invoked,
especially in case of bean mappings.

### Details
In many situations we have to map properties from one object into another which are structurally very similar:

    dog.setRace( hund.getRasse() );
    dog.setWeight( hund.getGewicht() );
    dog.setNice( hund.isLieb() );
    return dog;
    
Things can quickly get dangerous when a new property is added for instance to the class 
<code>hund</code>. Not seldom, you forget to update the mapping code at all locations 
where this new property had has actually to be mapped,too (what leads to common errors 
like missing data, NullPointerExceptions, etc.).

This utility protects you in so far as it can throw an exception when 
a new property was added **without** mapping it. This can be achieved by a simple 
modification:

    Dog dogPx = Proxifier.proxyOf(dog);
    Hund hundPx = Proxifier.proxyOf(hund);

    // Replace source objects with proxies:
    dogPx.setRace( hundPx.getRasse() );
    dogPx.setWeight( hundPx.getGewicht() );
    dogPx.setNice( hundPx.isLieb() );
    
    // Safety check:
    Proxifier.assertAllGettersInvoked(hundPx);
    Proxifier.assertAllSettersInvoked(dogPx);
    
    // Execution continued with the originals:
    return dog;
    
The proxy objects propagate the bean mapping to the original objects 
(here <code>dog</code> and <code>hund</code>). I.e. the properties of the 
originals are automatically mapped as well. So, the proxified objects 
should only be used in the set/get code regions, the further execution
can be done using the originals again.

#### Excepted properties
In certain cases not every getter (resp. setter) needs to be invoked during the mapping.
Getters (resp. setters) which shall not be checked can be specified as a list of getters 
(resp. setters) or properties:
     
    // Exclude some attributes from the safty check: 
    Proxifier.assertAllGettersInvoked(hundPx, "getRasse", "gewicht");

#### Enable / disable
Of course, this proxification costs additional execution time. But, for example in 
production environments it can be disabled (enabled by default) globally by setting

    Proxifier.enabled = false;
    
## Maven

    <repository>
        <id>javacook-maven-repository</id>
        <url>http://www.clean-coder.de:8080/artifactory/javacook/</url>
    </repository>

    <dependency> 
        <groupId>com.javacook</groupId>
        <artifactId>proxifier</artifactId>
        <version>1.0</version>
    </dependency>    
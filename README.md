# proxifier

### Summary
Proxifier creates proxies of beans in order to check whether all setters resp. getters have been invoked,
especially in case of bean mappings.

### Details
In many situations we have to map properties from one object into another which are structurally very similar:

    dog.setRace( hund.getRasse() );
    dog.setWeight( hund.getGewicht() );
    dog.setNice( hund.isLieb() );
    
Things can quickly get dangerous when a new property is added for instance to the class 
<code>Hund</code>. Not seldom, we forget to update the mapping code at all locations 
where this new property actually had have to be mapped (what leads to common errors 
like missing data, NullPointerExceptions, etc.).

This utility protects you in so far as it can for example throw an exception when 
a new property was added **without** being mapped. You can either add
this safety check to the existing mapping code by a simple modification:

#### Tight coupled with the mapping code

    Dog dogPx = Proxifier.proxyOf(dog);
    Hund hundPx = Proxifier.proxyOf(hund);

    // Replace source objects with proxies:
    dogPx.setRace( hundPx.getRasse() );
    dogPx.setWeight( hundPx.getGewicht() );
    dogPx.setNice( hundPx.isLieb() );
    
    // Safety check:
    Proxifier.assertAllGettersInvoked(hundPx);
    Proxifier.assertAllSettersInvoked(dogPx);
    
    // Continue with the originals:
    return dog;
    
#### Decoupled from the mapping code
    
Another possibility is to move the safety check completely into a separate test class.
For this purpose it is necessary that the mapping code has the following form:

    // Here the target object is also passed as a method parameter
    // and not created inside the mapper:
    public static void mapHundToDog(final Hund hund, final Dog dog) {
        dog.setRace( hund.getRasse() );
        dog.setWeight( hund.getGewicht() );
        dog.setNice( hund.isLieb() );
    }   
    
Usually, this can be be achieved through a small refactoring. If this condition is 
hold the test could look like:

    @Test
    public void testMapper() {

        // Create the test data:
        final Dog dog = new Dog();
        final Hund hund = new Hund("Pudel", 23, true);

        // Replace the test objects with proxies:
        final Dog dogPx = Proxifier.proxyOf(dog);
        final Hund hundPx = Proxifier.proxyOf(hund);

        // Call the mapper:
        Mapper.mapHundToDog(hundPx, dogPx);

        // Safety check:
        Proxifier.assertAllGettersInvoked(hundPx);
        Proxifier.assertAllSettersInvoked(dogPx);
    }

The proxy objects propagate the bean mapping to the original objects 
(here <code>dog</code> and <code>hund</code>). I.e., the properties of the 
originals are automatically mapped as well. So, the proxified objects 
should only be used in the set/get code regions, the further execution
can be continued using the originals again.

#### Excepted properties
In certain cases not every getter (resp. setter) needs to be invoked during the mapping.
Getters (resp. setters) which shall not be checked can be specified as a list of getters 
(resp. setters) or properties:
     
    // Exclude some properties from the safty check: 
    Proxifier.assertAllGettersInvoked(hundPx, "getRasse", "gewicht");

#### Enable / disable
Of course, this proxification costs additional execution time. If it is
used in the production code like in variant 1 it can also be disabled (enabled by default) 
globally by setting

    Proxifier.enabled = false;
    
Then the orginal objects are returned instead if the proxified so that there are no 
drawbacks on performance.    
    
### Examples
 
Some examples can be found in the <code>/src/test/java/com/javacook/proxifier/usage</code>.
     
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
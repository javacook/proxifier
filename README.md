# proxifier
### Summary
Proxifier creates a proxy of an object in order to check whether all setters resp. getters have been invoked,
e.g. in case of bean mappings.

### Details
In many situations we have to map properties from one object into another which are structurally very similar:

    dog.setRace( hund.getRasse() );
    dog.setWeight( hund.getGewicht() );
    dog.setNice( hund.isLieb() );
    return dog;
    
Things can quickly get dangerous when a new property is added e.g. to the class <code>hund</code>. 
Not seldom you forget to update the mapping code at all locations where this new property had 
to be mapped additionally what leads to common errors (missing data, NullPointerExceptions, etc.)  

This utility allows to protect your code in so far as an exception can be thrown when a new 
property was added **without** mapping it. This can be achieved with a simple modification:

    Dog dogPx = Proxifier.proxyOf(dog);
    Hund hundPx = Proxifier.proxyOf(hund);

    // Replace source objects with proxies:
    dogPx.setRace( hundPx.getRasse() );
    dogPx.setWeight( hundPx.getGewicht() );
    dogPx.setNice( hundPx.isLieb() );
    
    // Safety check
    Proxifier.assertAllGettersInvoked(hundPx);
    Proxifier.assertAllSettersInvoked(dogPx);
    
    // Execution continued with the originals
    return dog;
    
The proxy objects propergate the bean mapping to the original objects 
(here e.g. <code>dog</code> and <code>hund</code>) so that you can/should 
continue using these ones.

#### Excepted properties
In certain cases not every getter (resp. setter) needs to be invoked during the mapping.
Getters (resp. setters) which should not be checked can be specified as a list of getters 
(resp. setters) or properties:
     
    // Exclude some attributes from the safty check: 
    Proxifier.assertAllGettersInvoked(hundPx, "getRasse", "gewicht");

#### Enable / disable
Of course, the proxification costs additional execution time. To avoid this the proxification 
can be disabled (enabled by default) globaly by setting

    Proxifier.enabled = false;
for example in production mode.    
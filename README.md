# proxifier
### Summary
Proxifier creates a proxy of an object in order to check whether all setters resp. getters have been invoked.

### Details
In many situations we have to map properties from one object into another which are structurally very similar:

    dog.setRace( hund.getRasse() );
    dog.setWeight( hund.getGewicht() );
    dog.setNice( hund.isLieb() );
    return dog;
    
Things can quickly get dangerous when a property is added e.g. to the class <code>hund</code>. 
Often you forget to update the location(s) where this new property had to map additionally.
This utility allows you to protect your code in so far as 
an exception can be thrown when a new property was added without mapping it. This can
be achieved with a simple modification:

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
    
The proxy objects propergate the mapping to the source objects so that you can / should 
continue to work with the original objects (here e.g. <code>dog</code> and <code>hund</code>)

#### Excepted properties
In certain cases not every getter (resp setter) needs to be invoked during the mapping.
Getters (resp setters) which should not be checked can be specified as a list of getters (resp setters) or properties:
     
    // Exclude some attributes from the safty check: 
    Proxifier.assertAllGettersInvoked(hundPx, "getRasse", "gewicht");

#### Enable / disable
Obviousely, the proxification costs additional time. To avoid this the proxification 
can be disabled (it is enabled by default) globaly by setting

    Proxifier.enabled = false;     
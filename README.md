# proxifier
com.javacook.proxifier.Proxifier can create a proxy of an object in order to check whether all setters resp. getters have been invoked.

In many situations we have to map properties of two objects that are structural very similar:

    dog.setRace(hund.getRasse());
    dog.setWeight(hund.getGewicht());
    dog.setNice(hund.isLieb());
    
Things can quickly get dangerous when a property is added e.g. to the class <code>hund</code>. 
Often you forget to update the location(s) where this new property had to map additionally.
This utility allows you to protect your code in so far as 
an exception can be thrown when a new property is added without mapping it. This can
be achieved with a simple modification:

    com.javacook.proxifier.Dog dogPx = com.javacook.proxifier.Proxifier.proxyOf(dog);
    com.javacook.proxifier.Hund hundPx = com.javacook.proxifier.Proxifier.proxyOf(hund);

    dogPx.setRace(hundPx.getRasse());
    dogPx.setWeight(hundPx.getGewicht());
    dogPx.setNice(hundPx.isLieb());

    com.javacook.proxifier.Proxifier.assertAllGettersInvoked(hundPx);
    com.javacook.proxifier.Proxifier.assertAllSettersInvoked(dogPx);
    
The proxy objects propergate the mapping to the source objects so that you can / should 
continue to work with the original objects (here e.g. <code>dog</code> and <code>hund</code>)     
package org.mb.introspection;

public enum ResourceTypeDto {
    anana(Anana.class),
    pomme(Pomme.class)
    ;

    private Class<? extends Fruit> resourceClass;

    ResourceTypeDto(Class<? extends Fruit> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public Class<? extends Fruit> getResourceClass() {
        return resourceClass;
    }
}

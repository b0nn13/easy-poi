package org.devx.easypoi.format;

import java.util.Set;

public interface FormatEP
{
    String UNDEFINED = "N/A";
    
    Object format(String fieldName, Object value, String defaultFormat);
    
    default Object format(String fieldName, Object value) {
        return format(fieldName, value, null);
    }
    
    Set<Class> getAutorizedTypes();
    
    default void validate(String fieldName, Class type)
    {
        if (getAutorizedTypes() != null && !getAutorizedTypes().isEmpty())
        {
            for (Class autorizedType : getAutorizedTypes())
            {
                if (autorizedType.isAssignableFrom(type) || autorizedType.equals(type))
                    return;
            }
            throw new IllegalArgumentException(String.format("O campo %s não pode ser do tipo %s", fieldName, type));
        }
    }
    
}

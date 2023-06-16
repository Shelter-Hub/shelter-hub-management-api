/*
package com.shelterhub.service;

import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;


public class ModelDtoMapper extends AbstractMap<String, Object> {
    private final Map<String, Object> modelMap;
    private final Map<String, Object> dtoMap;

    public ModelDtoMapper() {
        modelMap = new HashMap<>();
        dtoMap = new HashMap<>();
    }

    @Override
    public Object put(String key, Object value) {
        return modelMap.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return modelMap.get(key);
    }

    // Add other required methods based on your needs

    public void mapModelToDto(Model model, Dto dto) {
         */
/*Perform the mapping from model to dto
         For example, you can iterate over the modelMap and set values in the dto*//*


        for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // Set the value in the corresponding field of the dto
            // You may need to use reflection or other mechanisms based on your specific requirements
            dtoMap.put(key, value);
        }
    }

    public void mapDtoToModel(Dto dto, Model model) {
        // Perform the mapping from dto to model
        // For example, you can iterate over the dtoMap and set values in the model

        for (Map.Entry<String, Object> entry : dtoMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // Set the value in the corresponding field of the model
            // You may need to use reflection or other mechanisms based on your specific requirements
            modelMap.put(key, value);
        }
    }
}

*/
/*
public abstract class AbstractMap<K,V> extends Object, implements Map {
}
*/


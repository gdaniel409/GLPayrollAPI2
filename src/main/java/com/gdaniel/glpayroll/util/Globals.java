package com.gdaniel.glpayroll.util;

import java.util.Comparator;
import org.springframework.data.domain.Sort;

public class Globals {

    /**
     * Builds a Comparator from a Spring Data Sort object.
     * This uses reflection to access the property getters.
     * WARNING: This requires careful handling of property names and types.
     */
    private static <T> Comparator<T> buildComparatorFromSort(Sort sort) {
        Comparator<T> comparator = (o1, o2) -> 0; // Default no-op comparator

        for (Sort.Order order : sort) {
            Comparator<T> currentOrderComparator = (o1, o2) -> {
                try {
                    // Simple reflection-based value fetching (requires proper getter naming)
                    String property = order.getProperty();
                    String getterMethodName = "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);

                    @SuppressWarnings("unchecked")
                    Comparable<Object> value1 = (Comparable<Object>) o1.getClass().getMethod(getterMethodName)
                            .invoke(o1);
                    @SuppressWarnings("unchecked")
                    Comparable<Object> value2 = (Comparable<Object>) o2.getClass().getMethod(getterMethodName)
                            .invoke(o2);

                    if (value1 == null && value2 == null)
                        return 0;
                    if (value1 == null)
                        return order.isAscending() ? -1 : 1;
                    if (value2 == null)
                        return order.isAscending() ? 1 : -1;

                    int comparison = value1.compareTo(value2);
                    return order.isAscending() ? comparison : -comparison;
                } catch (Exception e) {
                    throw new RuntimeException("Error accessing property for sorting: " + order.getProperty(), e);
                }
            };
            comparator = comparator.thenComparing(currentOrderComparator);
        }
        return comparator;
    }

}

package br.com.cmachado.cashflowcontrol.utils;

import javax.persistence.Table;

public class TableNamesUtil {
    public static String from(Class<?> aClass) {
        return aClass.getAnnotation(Table.class).schema() + "." + aClass.getAnnotation(Table.class).name();
    }
}
package com.mxm.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFormatter {

    public static String formatToLongDate(String date) {
        // Define o formato de entrada como ISO-8601 (yyyy-MM-dd)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Converte a string para LocalDate
        LocalDate localDate = LocalDate.parse(date, inputFormatter);

        // Obtém o nome do mês com a primeira letra maiúscula
        String month = localDate.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"))
                .substring(0, 1).toUpperCase() +
                localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR")).substring(1);

        // Formata a data final como "22 de novembro de 2024"
        return String.format("%d de %s de %d", localDate.getDayOfMonth(), month, localDate.getYear());
    }

    public static String formatToShortDate(String date) {
        // Define o formato de entrada como ISO-8601 (yyyy-MM-dd)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Define o formato de saída como dd/MM/yyyy
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Converte e formata a data
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        return localDate.format(outputFormatter);
    }
}

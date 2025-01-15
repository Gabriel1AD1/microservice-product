package com.keola.microservice.product.exception.util;

import com.keola.microservice.product.models.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.keola.microservice.product.exception.ErrorDuplicateDB.UNIQUE_CONSTRAINT_MESSAGES;


public class ErrorUtils {

    // Método para manejar la violación de integridad de datos
    public static ApiError handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Extraer la causa raíz de la excepción
        String rootCauseMessage = getRootCause(ex).getMessage();

        // Intentar extraer un mensaje específico desde el diccionario de errores
        String userMessage = UNIQUE_CONSTRAINT_MESSAGES.entrySet().stream()
                .filter(entry -> rootCauseMessage.contains(entry.getKey())) // Verifica si el mensaje contiene la clave
                .map(Map.Entry::getValue) // Obtiene el mensaje asociado
                .findFirst() // Toma el primero encontrado
                .orElse(null); // Si no se encuentra, se deja como null

        // Si no se encuentra un mensaje específico, usa uno genérico o detecta el tipo de error
        if (userMessage == null) {
            if (rootCauseMessage.contains("Duplicate entry")) {
                userMessage = "Duplicate entry detected: " + extractDuplicateEntryInfo(rootCauseMessage);
            } else if (rootCauseMessage.contains("violación de restricción de unicidad")) {
                userMessage = "Duplicate entry violation: " + extractConstraintInfo(rootCauseMessage);
            } else {
                userMessage = "A database integrity violation occurred.";
            }
        }

        // Construir el objeto ApiError
        return ApiError.builder()
                .errorCode(HttpStatus.CONFLICT)
                .errorMessage("Data integrity violation.")
                .debugMessage(rootCauseMessage)
                .detail(userMessage)
                .build();
    }

    // Método auxiliar para extraer la causa raíz de la excepción
    private static Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause != rootCause.getCause()) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    // Método auxiliar para extraer la información sobre la entrada duplicada
    private static String extractDuplicateEntryInfo(String rootCauseMessage) {
        String[] parts = rootCauseMessage.split("Duplicate entry '");
        if (parts.length > 1) {
            return parts[1].split("' for key")[0]; // Extrae el valor duplicado
        }
        return "Unknown duplicate entry";
    }

    // Método auxiliar para extraer la información sobre la restricción de unicidad
    private static String extractConstraintInfo(String rootCauseMessage) {
        String[] parts = rootCauseMessage.split("violación de restricción de unicidad");
        if (parts.length > 1) {
            String[] constraintParts = parts[1].split("«");
            if (constraintParts.length > 1) {
                return "Constraint violated: " + constraintParts[1].split("»")[0]; // Extrae el nombre de la restricción
            }
        }
        return "Unknown constraint violation";
    }
}

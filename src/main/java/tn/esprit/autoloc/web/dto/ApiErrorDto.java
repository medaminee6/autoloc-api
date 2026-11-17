package tn.esprit.autoloc.web.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorDto(
        int statut,
        String message,
        LocalDateTime horodatage,
        Map<String, String> erreurs
) {
}

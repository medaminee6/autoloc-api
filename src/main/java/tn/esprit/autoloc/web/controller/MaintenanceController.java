package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IMaintenanceService;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;
}

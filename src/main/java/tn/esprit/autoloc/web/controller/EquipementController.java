package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IEquipementService;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;
}

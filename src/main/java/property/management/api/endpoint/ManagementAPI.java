package property.management.api.endpoint;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import property.management.api.model.Property;
import property.management.api.service.ManagementService;
import property.management.api.utils.PropertySearchQuery;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/manage")
public class ManagementAPI {

    @Autowired
    private ManagementService  managementService;


    @PostMapping("/create")
    public ResponseEntity createProperty(@RequestBody Property property){
        return ResponseEntity.status(managementService.createProperty(property)).build();
    }

    @PostMapping("/update")
    public ResponseEntity updateProperty(@RequestBody Property property){

        return ResponseEntity.status(managementService.updateProperty(property)).build();
    }

    @PostMapping("/approve")
    public ResponseEntity approveProperty(@RequestBody Property property){
        return ResponseEntity.status(managementService.approveProperty(property)).build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<Property>> searchProperty(@RequestBody PropertySearchQuery query){
        return ResponseEntity.of(Optional.of(managementService.searchProperty(query)));

    }


}

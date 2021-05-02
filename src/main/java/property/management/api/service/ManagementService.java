package property.management.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import property.management.api.crud.PropertyRepository;
import property.management.api.model.Property;
import property.management.api.model.PropertyStatus;
import property.management.api.utils.PropertySearchQuery;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ManagementService {


    @Autowired
    private PropertyRepository propertyRepository;



    public int createProperty(Property property){
        try{
            propertyRepository.save(property);
            return HttpStatus.CREATED.value();
        }catch (DataIntegrityViolationException dataException){
            dataException.printStackTrace();
            return HttpStatus.BAD_REQUEST.value();
        }


    }

    public int updateProperty(Property property){

        if(propertyRepository.existsById(property.getId())){
            propertyRepository.save(updateMerger(property, propertyRepository.findById(property.getId()).get()));
            return HttpStatus.OK.value();
        }else{
            propertyRepository.save(property);
            return HttpStatus.CREATED.value();
        }
    }


    public int approveProperty(Property property){

        Optional<Property> existing = propertyRepository.findById(property.getId());

        if(existing.isPresent()){
            if(existing.get().getStatus() == PropertyStatus.PENDING){
                Property pending = existing.get();
                pending.setStatus(PropertyStatus.APPROVED);
                propertyRepository.save(pending);
                return HttpStatus.OK.value();
            }else{
              return HttpStatus.NOT_ACCEPTABLE.value();
            }
        }else{
            return HttpStatus.NOT_FOUND.value();
        }
    }

    public List<Property> searchProperty(PropertySearchQuery searchQuery){

        Optional<List<Property>> foundProperties;
        if(searchQuery.isCompleteQuery()){
            foundProperties = propertyRepository
                .findAllByNegotiableAndPriceBetweenAndLocationAndSizeBetween(
                    searchQuery.isNegotiable(),
                    searchQuery.getPrice(),
                    searchQuery.getMaxPrice(),
                    searchQuery.getLocation(),
                    searchQuery.getSize(),
                    searchQuery.getMaxSize()
                );
        }else if(searchQuery.isNoSizeQuery()){

            foundProperties = propertyRepository
                .findAllByNegotiableAndPriceBetweenAndLocation(
                    searchQuery.isNegotiable(),
                    searchQuery.getPrice(),
                    searchQuery.getMaxPrice(),
                    searchQuery.getLocation()
                );
        }else if(searchQuery.isPriceOnlyQuery()){
            foundProperties = propertyRepository.findAllByNegotiableAndPriceBetween(searchQuery.isNegotiable(), searchQuery.getPrice(), searchQuery.getMaxPrice());
        }else if (searchQuery.isLocationOnlyQuery()){
            foundProperties = propertyRepository.findAllByNegotiableAndLocation(searchQuery.isNegotiable(), searchQuery.getLocation());
        }else{
            foundProperties = propertyRepository.findAllByNegotiable(searchQuery.isNegotiable());
        }


        if(!foundProperties.isPresent()){
            return Collections.emptyList();
        }

        return foundProperties.get().stream().filter(
            property -> searchQuery.getGoal() == null ? true : property.getGoal().equals(searchQuery.getGoal())
        ).collect(Collectors.toList()).stream().filter(
            property -> searchQuery.getBathRooms() == 0 ? true : property.getBathrooms() == searchQuery.getBathRooms()
        ).collect(Collectors.toList()).stream().filter(
            property -> searchQuery.getBedRooms() == 0 ? true : property.getBathrooms() == searchQuery.getBedRooms()
        ).collect(Collectors.toList()).stream().filter(
            property -> searchQuery.getSize() == 0.0 ? true : property.getSize() >= searchQuery.getSize()
        ).collect(Collectors.toList()).stream().filter(
            property -> searchQuery.getMaxSize() == 0.0 ? true : property.getSize() <=searchQuery.getMaxSize()
        ).collect(Collectors.toList()).stream().filter(
            property -> searchQuery.getPrice() == BigDecimal.ZERO ? true : property.getPrice().longValue() >= searchQuery.getPrice().longValue()
        ).collect(Collectors.toList()).stream().filter(
            property -> searchQuery.getMaxPrice() == BigDecimal.ZERO ? true : property.getPrice().longValue() <= searchQuery.getMaxPrice().longValue()
        ).collect(Collectors.toList());


    }

    private Property updateMerger(Property updatedProperty, Property existingProperty){
        if(updatedProperty.getBathrooms() != 0 &&  updatedProperty.getBathrooms() != existingProperty.getBathrooms()){
            existingProperty.setBathrooms(updatedProperty.getBathrooms());
        }
        if (updatedProperty.getBedrooms() != 0 && updatedProperty.getBedrooms() != existingProperty.getBedrooms()){
            existingProperty.setBedrooms(updatedProperty.getBedrooms());
        }
        if (updatedProperty.getDeposit() != null && updatedProperty.getDeposit() != existingProperty.getDeposit()){
            existingProperty.setDeposit(updatedProperty.getDeposit());
        }
        if (updatedProperty.getDescription() != null && updatedProperty.getDescription() != existingProperty.getDescription()){
            existingProperty.setDescription(updatedProperty.getDescription());
        }
        if (updatedProperty.getFee() !=null && updatedProperty.getFee() != existingProperty.getFee()){
            existingProperty.setFee(updatedProperty.getFee());
        }
        if(updatedProperty.getFurnish() != null && updatedProperty.getFurnish() != existingProperty.getFurnish()){
            existingProperty.setFurnish(updatedProperty.getFurnish());
        }
        if(updatedProperty.getGoal() != null && updatedProperty.getGoal() != existingProperty.getGoal()){
            existingProperty.setGoal(updatedProperty.getGoal());
        }
        if(updatedProperty.getLocation() != null && updatedProperty.getLocation() != existingProperty.getLocation()){
            existingProperty.setLocation(updatedProperty.getLocation());
        }
        if(updatedProperty.getPrice() != null && updatedProperty.getPrice() != existingProperty.getPrice()){
            existingProperty.setPrice(updatedProperty.getPrice());
        }
        if(updatedProperty.getSeller() != null && updatedProperty.getSeller() != existingProperty.getSeller()){
            existingProperty.setSeller(updatedProperty.getSeller());
        }
        if(updatedProperty.getSize() != 0.0f && updatedProperty.getSize() != existingProperty.getSize()){
            existingProperty.setSize(updatedProperty.getSize());
        }
        if(updatedProperty.getStatus() != null && updatedProperty.getStatus() != existingProperty.getStatus()){
            existingProperty.setStatus(updatedProperty.getStatus());
        }
        if(updatedProperty.getType() != null && updatedProperty.getType() != existingProperty.getType()){
            existingProperty.setType(updatedProperty.getType());
        }

        return existingProperty;
    }




}

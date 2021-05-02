package property.management.api.crud;

import org.springframework.data.repository.CrudRepository;
import property.management.api.model.Property;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends CrudRepository<Property, Long> {


	Optional<List<Property>> findAllByNegotiable(boolean negotiable);

	Optional<List<Property>> findAllByNegotiableAndPriceBetween(boolean negotiable, BigDecimal bottom, BigDecimal upper);

	Optional<List<Property>> findAllByNegotiableAndLocation(boolean negotiable, String location);

	Optional<List<Property>> findAllByNegotiableAndPriceBetweenAndLocation(boolean negotiable, BigDecimal bottom, BigDecimal upper, String location);

	Optional<List<Property>> findAllByNegotiableAndPriceBetweenAndLocationAndSizeBetween(boolean negotiable, BigDecimal bottom, BigDecimal upper, String location, float size, float maxSize);




}

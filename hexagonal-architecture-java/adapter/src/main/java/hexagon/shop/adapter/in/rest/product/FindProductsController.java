package hexagon.shop.adapter.in.rest.product;

import static hexagon.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

import hexagon.shop.application.port.in.product.FindProductsUseCase;
import hexagon.shop.model.product.Product;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Slf4j
public class FindProductsController {

  private final FindProductsUseCase findProductsUseCase;

  public FindProductsController(final FindProductsUseCase findProductsUseCase) {
    this.findProductsUseCase = findProductsUseCase;
  }

  @GetMapping
  public List<ProductInListWebModel> findProducts(
      @RequestParam(value = "query", required = false) final String query) {
    log.info("Query ==> {}", query);
    if (Objects.isNull(query))
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'query'");
    List<Product> products;
    try {
      products = findProductsUseCase.findByNameOrDescription(query);
    } catch (final IllegalArgumentException exception) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'query'");
    }
    return products.stream().map(ProductInListWebModel::fromDomainModel).toList();
  }
}

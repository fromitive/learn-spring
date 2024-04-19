package example;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
class CustomerController {
    private final JdbcTemplate jdbcTemplate;

    public CustomerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/customers")
    ResponseEntity<Void> save(@RequestBody Customer customer){
        String sql = "INSERT INTO customers(first_name, last_name) VALUES (?,?)";
        jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customers")
    ResponseEntity<List<Customer>> findAll(){
        String sql = "SELECT id, first_name, last_name FROM customers";
        List<Customer> customers = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                ));
        return ResponseEntity.ok().body(customers);
    }
}

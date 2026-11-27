package vn.rikkei.cinemabox.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rikkei.cinemabox.common.BadRequestException;
import vn.rikkei.cinemabox.common.ConflictException;
import vn.rikkei.cinemabox.common.PageResponse;
import vn.rikkei.cinemabox.common.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        String normalizedEmail = request.email().trim();
        if (customerRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ConflictException("Email đã tồn tại: " + normalizedEmail);
        }

        Customer customer = new Customer();
        customer.setFullName(request.fullName().trim());
        customer.setEmail(normalizedEmail);
        customer.setPhone(request.phone());
        customer.setMemberLevel(request.memberLevel() != null ? request.memberLevel() : "STANDARD");

        Customer saved = customerRepository.save(customer);
        return CustomerResponse.from(saved);
    }

    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng id=" + id));
        return CustomerResponse.from(customer);
    }

    public PageResponse<CustomerResponse> list(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("page phải >= 0");
        }
        if (size < 1 || size > 50) {
            throw new BadRequestException("size phải trong khoảng từ 1 đến 50");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<CustomerResponse> result = customerRepository.findAll(pageable).map(CustomerResponse::from);
        return PageResponse.from(result);
    }
}

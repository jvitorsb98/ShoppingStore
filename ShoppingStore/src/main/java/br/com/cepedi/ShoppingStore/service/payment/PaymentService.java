package br.com.cepedi.ShoppingStore.service.payment;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Payment;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.model.records.payments.details.DataDetailsPayment;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.repository.PaymentRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.service.payment.validations.disabled.ValidationsDisabledPayment;
import br.com.cepedi.ShoppingStore.service.payment.validations.register.ValidationsRegisterPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private List<ValidationsRegisterPayment> validationsRegisterPaymentList;

    @Autowired
    private List<ValidationsDisabledPayment> validationsDisabledPaymentList;

    public DataDetailsPayment register(DataRegisterPayment data) {
        validationsRegisterPaymentList.forEach(v -> v.validation(data));
        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(data.shoppingCartId());
        Payment payment = new Payment(data,shoppingCart);
        paymentRepository.save(payment);
        return new DataDetailsPayment(payment);
    }

    public Page<DataDetailsPayment> listAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(DataDetailsPayment::new);
    }

    public Page<DataDetailsPayment> listAllPaymentsForUser(Long idUser , Pageable pageable) {
        return paymentRepository.findAllPaymentsByUserId(idUser,pageable).map(DataDetailsPayment::new);
    }

    public void disable(Long id) {
        validationsDisabledPaymentList.forEach(v -> v.validation(id));
        Payment payment  = paymentRepository.getReferenceById(id);
        payment.disable();
    }

}

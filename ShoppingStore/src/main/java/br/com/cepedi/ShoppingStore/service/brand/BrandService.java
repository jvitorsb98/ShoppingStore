package br.com.cepedi.ShoppingStore.service.brand;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.brand.details.DataBrandDetails;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataRegisterBrand;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import br.com.cepedi.ShoppingStore.service.brand.validations.disabled.BrandValidatorDisabled;
import br.com.cepedi.ShoppingStore.service.brand.validations.update.BrandValidationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private List<BrandValidationUpdate> brandValidationUpdateList;

    @Autowired
    private List<BrandValidatorDisabled> brandValidatorDisabledList;


    public DataBrandDetails register(DataRegisterBrand data) {
        Brand brand = new Brand(data);
        brand = brandRepository.save(brand);
        return new DataBrandDetails(brand);
    }

    public DataBrandDetails update(DataUpdateBrand data) {
        brandValidationUpdateList.forEach(v -> v.validation(data));
        Brand brand= brandRepository.getReferenceById(data.id());
        brand.updateDataBrand(data);
        return new DataBrandDetails(brand);
    }

    public DataBrandDetails getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        return new DataBrandDetails(brand);
    }

    public void disabled(Long id) {
        brandValidatorDisabledList.forEach(v -> v.validation(id));
        Brand brand = brandRepository.getReferenceById(id);
        brand.disable();
    }

    public Page<DataBrandDetails> listAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable).map(DataBrandDetails::new);
    }

    public Page<DataBrandDetails> listAllBrandsAndDisabledTrue(Pageable pageable) {
        return brandRepository.findAllByDisabledTrue(pageable).map(DataBrandDetails::new);
    }
}

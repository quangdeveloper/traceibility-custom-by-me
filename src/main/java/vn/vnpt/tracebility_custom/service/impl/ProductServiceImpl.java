package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.vnpt.tracebility_custom.entity.Product;
import vn.vnpt.tracebility_custom.entity.ProductType;
import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.mapper.ProductMapper;
import vn.vnpt.tracebility_custom.model.param.ProductParam;
import vn.vnpt.tracebility_custom.model.request.ProductNewRQ;
import vn.vnpt.tracebility_custom.model.request.ProductUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;
import vn.vnpt.tracebility_custom.model.response.ProductRP;
import vn.vnpt.tracebility_custom.repository.ProductRepository;
import vn.vnpt.tracebility_custom.repository.ProductTypeRepository;
import vn.vnpt.tracebility_custom.repository.UserRepository;
import vn.vnpt.tracebility_custom.service.ProductService;
import vn.vnpt.tracebility_custom.service.UploadService;
import vn.vnpt.tracebility_custom.util.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public PageRP findByProperties(ProductParam productParam) {

        Pageable pageable = PageRequest.of(productParam.getPageNo() - 1,
                productParam.getPageSize(),
                Sort.by("id").descending());

        ProductType productType = productTypeRepository.findByID(productParam.getTypeId());

        Page<Product> page = productRepository.findByProperties(productParam.getKeyword(),
                productParam.getStatus(),
                productParam.getCompanyId(),
                productType,
                productParam.getOriginId(),
                productParam.getIsCompanyFilter(),
                pageable
        );

        final long total = page.getTotalElements();

        final List<ProductRP> list = page.map(productMapper::toProductRpFromProduct).getContent();

        return PageRP.builder()
                .content(list)
                .total(total)
                .build();
    }

    @Override
    public PageRP findProductOrigin() {

        List<Product> products = productRepository.findProductOrigin();

        final long total = products.size();

        final List<ProductRP> list = products.stream()
                .map(productMapper::toProductRpFromProduct)
                .collect(Collectors.toList());

        return PageRP.builder()
                .content(list)
                .total(total)
                .build();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadService uploadService;

    @Override
    public ActionRP createProduct(ProductNewRQ productNewRQ) {

        User user = userRepository.findByID(SecurityUtil.getCurrentUserId());

        Product product = Product.builder()
                .name(productNewRQ.getProductName())
                .barcode(productNewRQ.getProductBarcode())
                .info(productNewRQ.getInfo())
                .productType(productTypeRepository.findByID(productNewRQ.getTypeID()))
                .user(user)
                .originId(productNewRQ.getOriginID())
                .build();

        final Long productId = productRepository.save(product).getId();

        return new ActionRP(ImmutableMap.builder()
                .put("PO_UPLOAD_KEY", uploadService.progressUpload(productId, "PRODUCT"))
                .build());
    }

    @Override
    public ActionRP updateProduct(ProductUpdateRQ productUpdateRQ) {

        User user = userRepository.findByID(SecurityUtil.getCurrentUserId());

        Product product = Product.builder()
                .name(productUpdateRQ.getProductName())
                .barcode(productUpdateRQ.getProductBarcode())
                .info(productUpdateRQ.getInfo())
                .productType(productTypeRepository.findByID(productUpdateRQ.getTypeID()))
                .user(user)
                .originId(productUpdateRQ.getOriginID())
                .build();
        product.setId(productUpdateRQ.getId());

        final Long productId = productRepository.save(product).getId();

        return new ActionRP(ImmutableMap.builder()
                .put("PO_UPLOAD_KEY", uploadService.progressUpload(productId, "PRODUCT"))
                .build());
    }
}

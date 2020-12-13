package vn.vnpt.tracebility_custom.service;

import vn.vnpt.tracebility_custom.model.param.ProductParam;
import vn.vnpt.tracebility_custom.model.request.ProductNewRQ;
import vn.vnpt.tracebility_custom.model.request.ProductUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;

public interface ProductService {

    PageRP findByProperties(ProductParam productParam);

    PageRP findProductOrigin();

    ActionRP createProduct(ProductNewRQ productNewRQ);

    ActionRP updateProduct(ProductUpdateRQ productUpdateRQ);


}

package vn.iotstar.service.vendor;

import vn.iotstar.entity.Shop;

public interface VendorIRegisterService {

	<S extends Shop> S save(S entity);

}

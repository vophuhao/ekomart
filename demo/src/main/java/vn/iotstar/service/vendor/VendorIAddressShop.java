package vn.iotstar.service.vendor;

import vn.iotstar.entity.AddressShop;

public interface VendorIAddressShop {

	<S extends AddressShop> S save(S entity);

}

package vn.iotstar.service.vendor.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.repository.IdentificationInfoRepository;
import vn.iotstar.service.vendor.VendorIdentificationService;

@Service
public class VendorIdentificationServiceImp implements VendorIdentificationService {

	@Autowired
	IdentificationInfoRepository idenRepository;
	
	
}
